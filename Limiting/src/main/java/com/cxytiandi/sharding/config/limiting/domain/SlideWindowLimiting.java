package com.cxytiandi.sharding.config.limiting.domain;

import com.cxytiandi.sharding.util.DateFormatUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description 滑动窗口算法
 * @Author zhao tailin
 * @Date 2021/8/6
 * @Version 1.0.0
 */
public class SlideWindowLimiting {
    private static ConcurrentHashMap<String, AtomicLong> limitingValve=new ConcurrentHashMap<String, AtomicLong>(100);
    private volatile ConcurrentLinkedDeque<AtomicLong> windowsRequestsPer=new ConcurrentLinkedDeque<AtomicLong>();
    private Long requestsPerSecond;
    private Long childWindowsNumber;
    private Long childRequestsPerSecond;

    public SlideWindowLimiting(Long requestsPerSecond, Long childWindowsNumber) {
        this.requestsPerSecond=requestsPerSecond;
        this.childWindowsNumber=childWindowsNumber;
        childRequestsPerSecond=requestsPerSecond / childWindowsNumber;
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

    public boolean getAccessRights(String flag) {
        computeWindowsRequestsPer();
        String key=DateFormatUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        if (limitingValve.containsKey(key)) {
            return limitingValve.get(key).incrementAndGet() < requestsPerSecond;
        }
        limitingValve.putIfAbsent(key, new AtomicLong(1));
        return limitingValve.get(key).incrementAndGet() < requestsPerSecond;

    }

    public void computeWindowsRequestsPer(){
        windowsRequestsPer.poll();
        windowsRequestsPer.addLast(new AtomicLong( windowsRequestsPer.getLast().addAndGet(1)));
    }
}
