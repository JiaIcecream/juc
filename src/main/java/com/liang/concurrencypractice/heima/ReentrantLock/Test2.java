package com.liang.concurrencypractice.heima.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

@Slf4j
public class Test2 {

    /*
        验证打断: 可打断: lock.lockInterruptibly()
                不可打断: lock.lock()
     */

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        Thread t1 =  new Thread(() -> {
            log.info("t1 线程执行...");
            // 可被打断
//            try {
//                lock.lockInterruptibly();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

            // lock.lock 不可被打断
            lock.lock();
            try {
                log.info("t1获得锁...");
            }finally {
                // 释放锁
                lock.unlock();
            }
        },"t1");

        // 主线程先上锁
        lock.lock();
        t1.start();

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("打断 t1");
        t1.interrupt();

        log.info("主线程释放锁");
        lock.unlock();

    }
}
