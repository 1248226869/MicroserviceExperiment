package com.cxytiandi.sharding.config.limiting.domain;

import com.cxytiandi.sharding.util.DateFormatUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description
 * 固定窗口计数算法
 * 每个周期访问量固定
 * warm：连续周期内request不均衡，会导致相邻周期的结合处，超出limiting
 * @Author zhao tailin
 * @Date 2021/8/5
 * @Version 1.0.0
 */
public class FixedWindowLimiting {
    private static ConcurrentHashMap<String, AtomicLong> limitingValve=new ConcurrentHashMap<String, AtomicLong>(100);
    private String url;
    private Long requestsPerSecond;

    public FixedWindowLimiting(String url, Long requestsPerSecond) {
        this.url=url;
        this.requestsPerSecond=requestsPerSecond;
        init();
    }

    private void init() {
        //初始化一天的容量
        try {
            Date tomorrow=DateFormatUtil.parse(DateFormatUtil.dateToStr(new Date(), "yyyy-MM-dd") + " 24:00:00");
            long tomorrowTime=tomorrow.getTime();
            Date today=DateFormatUtil.parse(DateFormatUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            long todayTime=today.getTime();
            long n=(tomorrowTime - todayTime) / 1000;
            for (int i=0; i <= n; i++) {

                limitingValve.putIfAbsent(DateFormatUtil.dateToStr(new Date(todayTime + 1000 * i), "yyyy-MM-dd HH:mm:ss"), new AtomicLong(0));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean getAccessRights() {
        String key=DateFormatUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        if (limitingValve.containsKey(key)) {
            return limitingValve.get(key).incrementAndGet() < requestsPerSecond;
        }
        limitingValve.putIfAbsent(key, new AtomicLong(1));
        return limitingValve.get(key).incrementAndGet() < requestsPerSecond;
    }


    public void changeRequestsPerSecond(Long newRequestsPerSecond) {
        this.requestsPerSecond=newRequestsPerSecond;
    }

    public static void main(String[] args) {

        FixedWindowLimiting limitingDomain=new FixedWindowLimiting("12", 21L);
        while (true) {
            new Thread() {

                public void run() {
                    if (limitingDomain.getAccessRights()) {
                        System.out.println(DateFormatUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    }
                }

            }.start();
        }
    }
}
