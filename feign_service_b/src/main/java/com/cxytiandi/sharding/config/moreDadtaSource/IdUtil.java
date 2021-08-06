package com.cxytiandi.sharding.config.moreDadtaSource;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * 雪花算法
 * snowflake生产的ID二进制结构表示如下(每部分用-分开):
 *
 * 0 - 00000000 00000000 00000000 00000000 00000000 0 - 00000 - 00000 - 00000000 0000
 *
 * 第一位未使用，
 * 接下来的41位为毫秒级时间(41位的长度可以使用69年，从1970-01-01 08:00:00)，
 *
 * 然后是5位datacenterId（最大支持2^5＝32个，二进制表示从00000-11111，也即是十进制0-31），
 *
 * 和5位workerId（最大支持2^5＝32个，原理同datacenterId），所以datacenterId*workerId最多支持部署1024个节点，
 *
 * 最后12位是毫秒内的计数（12位的计数顺序号支持每个节点每毫秒产生2^12＝4096个ID序号）.
 *
 * 所有位数加起来共64位，恰好是一个Long型（转换为字符串长度为18）.
 *
 * 单台机器实例，通过时间戳保证前41位是唯一的，分布式系统多台机器实例下，通过对每个机器实例分配不同的datacenterId和workerId避免中间的10位碰撞。
 * 最后12位每毫秒从0递增生产ID，
 *
 * 再提一次：每毫秒最多生成4096个ID，每秒可达4096000个。理论上，只要CPU计算能力足够，单机每秒可生产400多万个，实测300w+，效率之高由此可见。下面是一个demo，通过测试可以直接使用。
 * @Author zhao tailin
 * @Date 2021/8/6
 * @Version 1.0.0
 */
public class IdUtil {

    // 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
    private final static long twepoch = 1288834974657L;
    // 机器标识位数
    private final static long workerIdBits = 5L;
    // 数据中心标识位数
    private final static long dataCenterIdBits = 5L;
    // 机器ID最大值  这个是二进制运算，就是 5 bit最多只能有31个数字，也就是说机器id最多只能是32以内
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 数据中心ID最大值    机房id最多只能是32以内
    private final static long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    // 毫秒内自增位
    private final static long sequenceBits = 12L;
    // 机器ID偏左移12位
    private final static long workerIdShift = sequenceBits;
    // 数据中心ID左移17位
    private final static long dataCenterIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    // 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
    // 上次生成ID的时间截
    private static long lastTimestamp = -1L;
    // 0，并发控制   毫秒内序列(0~4095)
    private long sequence = 0L;
    // 工作机器ID(0~31)
    private final long workerId;
    // 机房中心ID(0~31)
    private final long dataCenterId;

    public IdUtil() {
        this.dataCenterId = getDataCenterId(maxDataCenterId);
        this.workerId = getMaxWorkerId(dataCenterId, maxWorkerId);
    }

    /**
     * @param workerId     工作机器ID
     * @param dataCenterId 机房中心ID
     */
    public IdUtil(long workerId, long dataCenterId) {
        // 要求传递进来的机房id和机器id不能超过32，不能小于0
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId不能大于%d或小于0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenterId不能大于%d或0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 获取下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        //获取当前时间戳，单位毫秒
        long timestamp = timeGen();
        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，应抛异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("系统时钟回退。%d毫秒内拒绝生成ID", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            //  一个毫秒内最多只能有4096个数字，无论你传递多少进来，这个位运算保证始终在4096这个范围内
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        // 记录最近一次生成id的时间戳，单位毫秒
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        /**
         * 返回结果：
         * (timestamp - twepoch) << timestampLeftShift) 表示将时间戳减去初始时间戳，再左移相应位数
         * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
         * (workerId << workerIdShift) 表示将工作id左移相应位数
         * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
         * 因为各部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
         */
        long nextId = ((timestamp - twepoch) << timestampLeftShift)
                | (dataCenterId << dataCenterIdShift)
                | (workerId << workerIdShift) | sequence;

        return nextId;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * <p>
     * 获取 maxWorkerId
     * </p>
     */
    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * <p>
     * 数据标识id部分
     * </p>
     */
    protected static long getDataCenterId(long maxDataCenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDataCenterId + 1);
            }
        } catch (Exception e) {
            System.out.println(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }

    public static void main(String[] args) {
        IdUtil idUtil = new IdUtil(1,1);
        Set<String> set = new HashSet<>();
        Runnable work = () -> {
            String id = String.valueOf(idUtil.nextId());
            if (!set.contains(id)){
                set.add(id);
                System.out.println("不重复："+id);
            }else {
                System.out.println("重复："+id);
            }
        };

        for (int i =0;i<1000000;i++){
            new Thread(work).start();
        }
    }

}
