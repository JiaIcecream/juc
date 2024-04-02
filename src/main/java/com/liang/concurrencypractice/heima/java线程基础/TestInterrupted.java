package com.liang.concurrencypractice.heima.java线程基础;

public class TestInterrupted {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while(true) {
                boolean isinterrupte = Thread.currentThread().isInterrupted();
                if(isinterrupte) {
                    // 判断被打断之后可以在该代码块中执行善后工作
                    System.out.println("被打断"+ isinterrupte);
                    break;
                }
            }
        });

        System.out.println("开始执行");
        t1.start();

        t1.interrupt();

        System.out.println("执行结束");
    }
}
