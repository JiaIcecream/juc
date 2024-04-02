package com.liang.concurrencypractice.heima.ReentrantLock.线程按顺序执行;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    static Object obj = new Object();

    static boolean t2runed = false;

    public static void main(String[] args) {
        /*
            使用 wait/notify 或 await/signal
            必须先打印1再打印2
         */

        new Thread(() -> {
            synchronized (obj) {
                while(!t2runed) {
                    // 只有第二轮循环进入的时候才会再obj.wait处停止，因此写代码的时候while中判断部分最好只留 obj.wait
                    try {
                        obj.wait();
                    }catch (Exception e) {
                        log.error("被打断");
                    }
                }
                log.info("t1 被执行！");
            }
        },"t1").start();

        new Thread(() -> {
            synchronized (obj) {
                log.info("t2 被执行！");
                // 唤醒所有正在等待的线程
//                t2runed = true;
                obj.notifyAll();
            }
        },"t2").start();
    }
}
