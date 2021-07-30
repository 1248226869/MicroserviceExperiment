package com.cxytiandi.sharding.config.moreDadtaSource;

/**
 * @Description 在进行增删改查的时候，对于每一个线程应该有对应的数据源，所以使用ThreadLocal来存储当前线程对应的数据源，
 * ThreadLocal可以定义在后面介绍的DynamicDataSource类里，
 * 也可以定义在其他类中，比如这里定义在DataSourceContextHolder类中
 * @Author zhao tailin
 * @Date 2021/7/22
 * @Version 1.0.0
 */
public class DataSourceContextHolder {
    //为每个线程存储对应的数据源
    private static ThreadLocal<DataSourceType> context=new ThreadLocal<>();

    public static void setDataSource(DataSourceType datasource) {
        context.set(datasource);
    }

    public static DataSourceType getDataSource() {
        return context.get();
    }

    //移除存储的数据源信息
    public static void clearDataSource() {
        context.remove();
    }


    //StrongReference
    //SoftReference
    //WeakReference
    //PhantomReference
    //FinalReference
    public static void main(String[] args) {
        ThreadLocal<Integer> context=new ThreadLocal<>();

            new Thread() {
                @Override
                public void run() {
                    context.set(1);
                    context.set(12);
                    System.gc();
                    try {
                        Thread.sleep(10*1000);
                        System.gc();
                        Integer integer=context.get();
                        Thread.sleep(2*1000);
                        System.out.println(Thread.currentThread().getName()+" get v is "+integer);
                        System.gc();
                        System.out.println(Thread.currentThread().getName()+" get v is "+integer);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

    }
}
