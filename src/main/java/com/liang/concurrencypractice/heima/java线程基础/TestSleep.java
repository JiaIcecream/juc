package com.liang.concurrencypractice.heima.java线程基础;

import java.util.concurrent.TimeUnit;

public class TestSleep {
    public static void main(String[] args) throws InterruptedException {

        /*
            打断测试：
                处于sleep状态的线程被interrupt之后会抛出InterruptedException 异常
         */
        Thread t1 = new Thread(() -> {
            System.out.println("runing...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("wake up ...");
                throw new RuntimeException(e);
            }
        });

        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t1.interrupt();

    }
}
