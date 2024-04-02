package com.liang.concurrencypractice.heima.waitAndNotify使用;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Test1 {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            // 获取response
            log.info("正在获取...");
            Integer res = (Integer) guardedObject.getResponse(3000);
            log.info("获取结果为{}",res);
        },"t1").start();

        new Thread(() -> {
            log.info("正在下载...");
            try {
                TimeUnit.SECONDS.sleep(2);
                guardedObject.setResponse(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t2").start();

        log.info("主线程不受影响");
    }
}

class GuardedObject {

    private Object response;

    // 增加超时等待时间，可以记一下这个程序模板
    public Object getResponse(long timeout) {
        // this 锁住的就是当前实例
        synchronized (this) {
            long begintime = System.currentTimeMillis();

            long passtime = 0;
            while(response == null) {
                try {
                    // 这一轮循环中再等待多长时间
                    long waitTime = timeout - passtime;
                    if(waitTime <= 0) {
                        break;
                    }
                    // 设置这一轮循环等待时长
                    this.wait(waitTime);
                    // 已经等待了多长时间
                    passtime = System.currentTimeMillis() - begintime;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return response;
        }
    }

    public void setResponse(Object response) {
        synchronized (this) {
            this.response = response;
            // 唤醒所有wait的线程
            this.notifyAll();
        }
    }

}
