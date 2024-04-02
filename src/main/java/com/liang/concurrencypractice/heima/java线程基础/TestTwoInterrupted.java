package com.liang.concurrencypractice.heima.java线程基础;

import java.util.concurrent.TimeUnit;

public class TestTwoInterrupted {
    public static void main(String[] args) throws InterruptedException {
        TestInterrupt testInterrupt = new TestInterrupt();
        testInterrupt.start();
        TimeUnit.SECONDS.sleep(3);
        testInterrupt.stop();
    }

}

class TestInterrupt {
    private static Thread minor;

    public void start() {
        minor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();

                if (current.isInterrupted()) {
                    System.out.println("执行后处理操作");
                    break;
                }

                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("执行监视操作");
                } catch (InterruptedException e) {

                    // 异常操作会使打断状态被清空
                    e.printStackTrace();
                    System.out.println("sleep状态被打断");

                    // 更新打断状态，使打断标志为 true
                    current.interrupt();
                }
            }

        });

        minor.start();
    }

    public void stop() {
        minor.interrupt();
    }
}
