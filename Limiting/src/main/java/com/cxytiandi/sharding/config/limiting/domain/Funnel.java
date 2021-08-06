package com.cxytiandi.sharding.config.limiting.domain;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * 漏斗算法[限量不限速]
 * @Author zhao tailin
 * @Date 2021/8/5
 * @Version 1.0.0
 */
public class Funnel {

    private volatile ReentrantLock lock=new ReentrantLock(true);
    private volatile AtomicLong requestsPerSecond;
    private volatile ConcurrentHashMap requestsPool=new ConcurrentHashMap<String, Date>();
    private volatile ConcurrentLinkedDeque<String> fifoDeque=new ConcurrentLinkedDeque<String>();
    private  Timer timer=new Timer("ScheduleDemo", false);

    public Funnel(Long requestsPerSecond) {
        this.requestsPerSecond=new AtomicLong(requestsPerSecond);

        startTimer();
    }

    private void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    while (true){

                        String first=fifoDeque.getFirst();
                        if (!requestsPool.containsKey(first)){
                            fifoDeque.pollFirst();
                            continue;
                        }
                        //过期
                         comeBackAccessRights(fifoDeque.pollFirst());
                        //未过期
                        break;
                    }

                } catch (Throwable e) {

                }
            }
        }, 1000 * 3, 3 * 1000);
    }


    public void updateRequestsPerSecond(Long requestsPerSecond) {
        this.requestsPerSecond=new AtomicLong(requestsPerSecond);
    }


    public boolean getAccessRights(String flag) {
        lock.lock();
        try {
            if (requestsPerSecond.longValue() < 1) {
                return false;
            }
            requestsPerSecond.decrementAndGet();
            requestsPool.put(flag, new Date());
            fifoDeque.addLast(flag);
            return true;
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return false;
    }

    public void comeBackAccessRights(String flag) {
        requestsPerSecond.incrementAndGet();
        requestsPool.remove(flag);
    }


    public static void main(String[] args) {
         ConcurrentLinkedDeque<Integer> fifoDeque=new ConcurrentLinkedDeque();
        fifoDeque.addLast(1);
        fifoDeque.addLast(2);
        fifoDeque.addLast(3);
        fifoDeque.addLast(4);

        System.out.println(fifoDeque.pollFirst());

        System.out.println(fifoDeque.pollFirst());

        System.out.println(fifoDeque.pollFirst());

        System.out.println(fifoDeque.pollFirst());
    }


}
