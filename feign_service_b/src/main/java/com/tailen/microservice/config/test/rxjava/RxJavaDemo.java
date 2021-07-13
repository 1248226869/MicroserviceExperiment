package com.tailen.microservice.config.test.rxjava;

import org.apache.commons.logging.Log;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/12
 * @Version 1.0.0
 */
public class RxJavaDemo {

    // ReactiveX Java  响应式编程框架(android）
    // Java stream() java8
    //观察者模式
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        //创建被观察者
//        Observable.create(new Observable.OnSubscribe<Integer>() {
//
//            @Override
//            public void call(Subscriber<? super Integer> subscriber) {
//                try {
//
//                    //获取的观察者，告知观察者所发生的事件
//                    for (int i = 0; i < 5; i++) {
//                        subscriber.onNext(i);
//                    }
//                }catch (Exception e){
//                    subscriber.onError(e);
//                }
//                // 观察 完成
//                subscriber.onCompleted();
//            }
//
//        }).
//                //订阅观察者
//                subscribe(new Observer<Integer>() {
//
//            @Override
//            public void onCompleted() {
//                System.out.println("onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("onError");
//            }
//
//            @Override
//            public void onNext(Integer item) {
//                System.out.println("Item is " + item);
//            }
//        });

//        创建被观察者Observable;just()和from()同样可以创建Observable。
//       Observable.just("One", "Two", "Three").doOnSubscribe(new Action0() {
//           @Override
//           public void call() {
//               System.out.println("what ???");
//           }
//       }).subscribe(createObserver());

//        Observable.just(1, 2, 3, 4, 5)
//                //过滤观察事件
//                .filter(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer data) {
//                        return data > 3;
//                    }
//                })
//                //Func1构造函数中的两个参数分别是Observable发射值当前的类型和map转换后的类型，上面这个例子中发射值当前的类型是Integer,转换后的类型是String。
//                .map(new Func1<Integer, String>() {
//                    @Override
//                    public String call(Integer i) {
//                        return "This is " + i;
//                    }
//                }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println(s);
//            }
//        });

//        String[] letters=new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
//        Observable<Integer> letterSequence=Observable.just(1, 2, 3, 4, 5) ;
//
//        Observable<Long> numberSequence=Observable.just(111L, 2222L, 322L, 4873L, 51213L) ;
//
//        Observable.merge(letterSequence, numberSequence)
//                .subscribe(new Observer<Object>() {
//                    @Override
//                    public void onCompleted() {
//                        System.exit(0);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("Error:" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Object serializable) {
//                        System.out.print(serializable.toString() + " ");
//                    }
//                });

//        Observable.from(new String[]{"1","34"}) .doOnSubscribe(new Action0() {
//            @Override
//            public void call() {
//                System.out.println("what ???");
//            }[2,3,1,2,4,3]
//        }).subscribe(createObserver());

        Observable.just(createData())
//                .observeOn(Schedulers.newThread())
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String o) {
                        System.out.println("thread is "+Thread.currentThread().getName());
                        return "map:::: "+o;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe(createObserver());

    }

    private static String createData() {

        return "ss";
    }


    //创建观察者
    public static Observer<Object> createObserver() {
        return new Observer<Object>() {

            @Override
            public void onCompleted() {
                System.out.println("观察者完成");
            }

            @Override
            public void onError(Throwable e) {

                System.out.println("～～～观察异常" + e.toString());
            }

            @Override
            public void onNext(Object s) {
                System.out.println("观察者收到：" + s.toString()+" in thread is "+Thread.currentThread().getName());

            }
        };
    }

    //创建被观察者Observable;just()和from()同样可以创建Observable。
    // Observable observable = Observable.just("One", "Two", "Three");
    ////上面这行代码会依次调用
    ////onNext("One");
    ////onNext("Two");
    ////onNext("Three");
    ////onCompleted();

    //from(T[])/from(Iterable<? extends T>)将传入的数组或者Iterable拆分成Java对象依次发送
    // String[] parameters = {"One", "Two", "Three"};
    //Observable observable = Observable.from(parameters);
    ////上面这行代码会依次调用
    ////onNext("One");
    ////onNext("Two");
    ////onNext("Three");
    ////onCompleted();
    public Observable<Object> createObservable() {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                System.out.println("on Complated");
            }
        });
    }

    private void logThread(Object obj, Thread thread) {
//        Log.v(TAG, "onNext:"+obj+" -"+Thread.currentThread().getName());
    }
}
