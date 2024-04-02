package com.liang.concurrencypractice.heima.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Test1 {
    /*
        验证ReentrantLock 的可重入性,即同一个线程在获取锁的前提下可以重复获取该锁对象
     */

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        method1();
    }

    public static void method1() {
        lock.lock();
        try {
            log.info("method1 执行");
            method2();
        }finally {
            lock.unlock();
        }
    }

    public static void method2() {
        lock.lock();
        try {
            log.info("method2 执行");
            method3();
        }finally {
            lock.unlock();
        }
    }

    public static void method3() {
        lock.lock();
        try {
            log.info("method3 执行");

        }finally {
            lock.unlock();
        }
    }

}
