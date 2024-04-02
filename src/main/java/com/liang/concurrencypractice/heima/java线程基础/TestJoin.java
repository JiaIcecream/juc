package com.liang.concurrencypractice.heima.java线程基础;

import java.util.concurrent.TimeUnit;

public class TestJoin {

    static int r = 0;

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    private static void test1() throws InterruptedException {
        System.out.println("开始");

        Thread t1 = new Thread(() -> {
            System.out.println("t1 开始");
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("t1 结束");
                r = 10;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        // 阻塞住，等待t1执行完成再往下运行
        t1.join();
        System.out.println("结果为:" + r);
        System.out.println("结束");
    }

}
