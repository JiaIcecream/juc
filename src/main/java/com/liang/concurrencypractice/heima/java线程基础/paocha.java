package com.liang.concurrencypractice.heima.java线程基础;

import java.util.concurrent.TimeUnit;

public class paocha {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
           try {
               System.out.println("洗水壶");
               TimeUnit.SECONDS.sleep(1);
               System.out.println("烧开水");
               TimeUnit.SECONDS.sleep(5);
           }catch (InterruptedException e) {
               throw new RuntimeException(e);
           }

        });

        Thread t2 = new Thread(() -> {
            // 重点不是谁快，而是谁等谁
            try {
                System.out.println("洗茶壶");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("洗茶杯");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("拿茶叶");
                TimeUnit.SECONDS.sleep(1);
                t1.join();
                System.out.println("泡茶");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
        t2.start();
    }
}
