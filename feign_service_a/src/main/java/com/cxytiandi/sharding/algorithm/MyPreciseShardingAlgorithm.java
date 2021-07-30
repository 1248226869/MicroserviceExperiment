package com.cxytiandi.sharding.algorithm;

import com.cxytiandi.sharding.FeignServiceAApplication;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * 自定义分片算法：数量大则分表，访问量大则分库。
 * <p>
 * 分表、分库思想
 * 大包含小，以小为sharding-key
 * 比如订单表。
 * 门店id,用户id,订单id,分别为storeId,userId,orderId
 * userId=storeId{xyz}
 * orderId=userId{xyz}
 * 然后以storeId进行分表，则可以兼顾以上所有key
 * 主键为orderId。对查询友好
 * 缺点：表倾斜
 * 解决方案，扩容
 * <p>
 */
public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {
    private final Logger log=LoggerFactory.getLogger(MyPreciseShardingAlgorithm.class);

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        for (String tableName : availableTargetNames) {
            if (tableName.endsWith(shardingValue.getValue().longValue() % 3 + "")) {
                log.info("tableName is {}  ; shardingValue is {}", tableName, shardingValue.getValue());
                return tableName;
            }
        }
        throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        Long a=12375678998L;
        String s=toUnsignedString0(a, 8);
        char[] chars=s.toCharArray();
        System.out.println(s);
        int count1=0;
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0; i < chars.length; i=i + 2) {
            char d1=chars[i];
            char d2=chars[i + 1];
            int f=0;
            if (d1 == '0' && d2 == '1') {
                f=1;
            } else if (d1 == '1' && d2 == '0') {
                f=2;
            } else if (d1 == '1' && d2 == '1') {
                f=3;
            }
            System.out.print(d1);
            System.out.print(d2);

            stringBuilder.append(f);

        }
        System.out.println();
        System.out.println(stringBuilder.toString());

        char[] chars1=stringBuilder.toString().toCharArray();

        StringBuilder news=new StringBuilder();
        for (int i=0; i < chars1.length; i++) {
            char f=chars1[i];
            String n="00";
            if (f == '1') {
                n="01";
            } else if (f == '2') {
                n="10";
            } else if (f == '3') {
                n="11";
            }

            news.append(n);

        }

        System.out.println(news);

    }

    static String toUnsignedString0(long val, int shift) {
        // assert shift > 0 && shift <=5 : "Illegal shift value";
        int mag=Long.SIZE - Long.numberOfLeadingZeros(val);
        int chars=Math.max(((mag + (shift - 1)) / shift), 1);
        char[] buf=new char[chars];

        formatUnsignedLong(val, shift, buf, 0, chars);
        return new String(buf);
    }

    /**
     * Format a long (treated as unsigned) into a character buffer.
     *
     * @param val    the unsigned long to format
     * @param shift  the log2 of the base to format in (4 for hex, 3 for octal, 1 for binary)
     * @param buf    the character buffer to write to
     * @param offset the offset in the destination buffer to start at
     * @param len    the number of characters to write
     * @return the lowest character location used
     */
    static int formatUnsignedLong(long val, int shift, char[] buf, int offset, int len) {
        int charPos=len;
        int radix=1 << shift;
        int mask=radix - 1;
        do {
            buf[offset + --charPos]=digits[((int) val) & mask];
            val >>>= shift;
        } while (val != 0 && charPos > 0);

        return charPos;
    }

    static char[] digits={
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };
}
