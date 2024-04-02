package com.liang.concurrencypractice.heima.ReentrantLock.线程交替执行;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test2ReentrantLock {

    public static void main(String[] args) throws InterruptedException {
        AwaitSignal awaitSignal = new AwaitSignal(3);
        Condition a = awaitSignal.newCondition();
        Condition b = awaitSignal.newCondition();
        Condition c = awaitSignal.newCondition();
        new Thread(() -> {
            awaitSignal.print(a,b,"a");
        },"t1").start();
        new Thread(() -> {
            awaitSignal.print(b,c,"b");
        },"t2").start();
        new Thread(() -> {
            awaitSignal.print(c,a,"c");
        },"t3").start();

        Thread.sleep(1000);
        awaitSignal.lock();
        try {
            System.out.println("开始...");
            a.signal();
        } finally {
            awaitSignal.unlock();
        }
    }
}

@Slf4j
// 领悟这个思想很重要
class AwaitSignal extends ReentrantLock {

    private Integer loopNum;

    AwaitSignal(Integer loopNum) {
        this.loopNum = loopNum;
    }

    public void print(Condition current,Condition next,String msg) {
        for (Integer i = 0; i < loopNum; i++) {
            // 再wait等操作之前都需要获得锁
            lock();
            try {
                // 将当前线程放入等待房间
                current.await();
                // 输出msg内容
                log.info(msg);
                // 将下一个等待房间内的线程唤醒
                next.signal();
            }catch (Exception e) {
                log.error("被打断！");
            }finally {
                unlock();
            }
        }

    }

}
