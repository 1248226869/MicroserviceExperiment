package com.cxytiandi.sharding.test;

import rx.Observable;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Func0;

import java.util.concurrent.ExecutionException;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/2
 * @Version 1.0.0
 */
public class RxJavaDemo {

    // ReactiveX Java  响应式编程框架(android）
    // Java stream() java8
    // Action和Func都是定义的一个动作，Action是无返回值，Func是有返回值
    //观察者模式
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final String[] datas=new String[]{"登录"};


        //老师（被观察者）
        Observable<String> observable=Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable
                        .from(datas)
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
                                System.out.println("被观察者 do On Terminate");
                            }
                        })
                        .doOnUnsubscribe(new Action0() {
                            @Override
                            public void call() {
                                System.out.println("被观察者 do On Unsubscribe");
                            }
                        })
                        .doOnCompleted(new Action0() {
                            @Override
                            public void call() {
                                System.out.println("被观察者 do On Complated");
                            }
                        });
            }
        });
        //学生(观察者)
        Observer observer=new Observer() {
            @Override
            public void onCompleted() {
                System.out.println("观察者: on Complated");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("观察者: onError");
            }

            @Override
            public void onNext(Object o) {
                System.out.println("观察者:on Next:" + o);
            }
        };
//        observable.subscribe(observer); //建立订阅关系

        String s=observable.toBlocking().toFuture().get();//建立订阅关系
        System.out.println(s);
    }
}
