package com.liang.concurrencypractice.heima.死锁;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Test1 {
    public static void main(String[] args) {

        /*
            死锁：
            两个线程，两个锁对象。两个线程分别获得锁对象，但是这时候又需要获取另一个锁对象才能继续执行，
            在不释放占有锁对象的情况下也无法获取另一个锁对象就无法再执行下去，这样就造成了死锁。
         */

        Object A = new Object();
        Object B = new Object();

        new Thread(() -> {
            synchronized (A) {
                log.info("lock A...");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (B) {
                    log.info("lock B...");
                    log.info("操作...");
                }
            }
        },"t1").start();

        new Thread(() -> {
            synchronized (B) {
                log.info("lock B...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (A) {
                    log.info("lock A...");
                    log.info("操作...");
                }
            }
        },"t2").start();
    }
}
