package com.liang.concurrencypractice.heima.共享模型之管程;

public class TestShareError {

    static int count = 0;
    private final static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (obj) {
                    count++;
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                // 利用synchronized 保证了代码块中的一致性
                synchronized (obj) {
                    count--;
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(count);



    }

}
