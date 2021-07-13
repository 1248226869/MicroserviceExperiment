package com.tailen.microservice.config.test.rxjava;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func0;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/12
 * @Version 1.0.0
 */
public class TestRxjava {


    private static String dataProducer() {
        String str="Hello,RxJava!";
        System.out.println("Produce Data :" + str);
        return str;
    }

    private static void just() {
        Observable.just(dataProducer()).doOnSubscribe(() -> {
            System.out.println("Subscribe!");
        }).subscribe(s -> {
            System.out.println("Consume Data :" + s);
        });
    }

    private static void defer() throws ExecutionException, InterruptedException {
        Future<String> stringFuture=Observable.defer(() -> {
            return Observable.just(dataProducer());
        }).toBlocking().toFuture();
        String s=stringFuture.get();
        System.out.println(s);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        just();
        defer();
//        List<String> list = Arrays.asList(new String[]
//                {"one","two","three"});
//        Observable<String> stringObservable=Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                for (String str : list) {
//                    System.out.println(str);
//                    subscriber.onNext("send:"+str);
//                }
//                subscriber.onCompleted();
//            }
//        });
//        stringObservable.subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("Observable completed");
//            }
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("Oh,no! Something wrong happened！");
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println("Consume " + s);
//            }
//
//        });
//        stringObservable.subscribe(new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("22222222222Observable completed");
//            }
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("2222222222Oh,no! Something wrong happened！");
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println("22222222222Consume " + s);
//            }
//
//        });
//        ConcurrentHashMap<String, Integer> s=new ConcurrentHashMap<>();
//        s.put("1",1);
//        s.put("1",1111);
//        System.out.println(s.get("1"));
    }


}
