package com.liang.concurrencypractice.chapter2;

import java.util.concurrent.TimeUnit;

public class SyncExce {

    /*
        每个java对象都可以用作一个实现同步的锁，这些锁被称为内置锁，或监视锁。线程在进入同步代码块之前会自动获得锁，并且在退出同步代码块之前会自动释放锁，无论是通过正常的控制路径退出的还是通过代码块抛出异常退出的
        使用 synchronized 去锁类中的方法，当方法执行的过程中抛出异常 锁就会被自动释放
     */


    public synchronized void foo(int i) {
        try{
            System.out.println("执行行foo" + i + "开始");

            if(i == 3) {
                throw new RuntimeException();
            }
            TimeUnit.SECONDS.sleep(4);
            System.out.println("执行行foo" + i + "结束");

        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SyncExce syncExce = new SyncExce();

        new Thread(() -> {
            syncExce.foo(3);
        }).start();

        new Thread(() -> {
            syncExce.foo(5);
        }).start();

    }
}
