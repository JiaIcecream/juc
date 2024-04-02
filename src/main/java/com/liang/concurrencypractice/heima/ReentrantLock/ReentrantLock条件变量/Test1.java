package com.liang.concurrencypractice.heima.ReentrantLock.ReentrantLock条件变量;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Test1 {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock room = new ReentrantLock();

        Condition condition1 = room.newCondition();

        Condition condition2 = room.newCondition();

        AtomicBoolean hashCigarette = new AtomicBoolean(false);

        AtomicBoolean hashFood = new AtomicBoolean(false);

        // 两个小黑屋，为不同的需求所打造
        // 不同的小黑屋进入的前提都是需要先获得锁。

        Thread t1 = new Thread(() -> {
            room.lock();
            log.info("没烟抽，不干活");
            try {
                while(!hashCigarette.get()) {
                    try {
                        // 到吸烟室等待
                        condition1.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.info("烟到了，开始吸烟");
            }finally {
                room.unlock();
            }
        });



        Thread t2 = new Thread(() -> {
            room.lock();
            log.info("没饭吃，不干活");
            try {
                while(!hashFood.get()) {
                    try {
                        // 到餐厅等待
                        condition2.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.info("饭到了，开始吃饭，开始干活");
            }finally {
                room.unlock();
            }
        });

        t1.start();
        t2.start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(() -> {
            room.lock();
            try {
                log.info("鸡汤来喽...");
                hashFood.set(true);
                // 通过 signal 将指定房间的等待线程唤醒
                condition2.signal();
            }finally {
                room.unlock();
            }
        }).start();
    }
}
