package com.liang.concurrencypractice.heima.ReentrantLock.哲学家就餐问题;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class TestEat{

    /*
        ReentrantLock 解决哲学家就餐问题
     */


    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");

        new Philosopher("苏格拉底",c1,c2).start();
        new Philosopher("张良",c2,c3).start();
        new Philosopher("关羽",c3,c4).start();
        new Philosopher("赵云",c4,c5).start();
        new Philosopher("刘邦",c5,c1).start();
    }
}


@Slf4j
class Chopstick extends ReentrantLock {
    String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}

@Slf4j
class Philosopher extends Thread {
    Chopstick left;
    Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    private void eat() {
        log.info("eating...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run() {
        while (true) {
            // 获得左手筷子 left.tryLock() 函数返回的是 boolean ，值为true表示获取到了锁，false则没获取到
            if(left.tryLock()) {
                try {
                    // 如果获取到了右筷子的锁那么就执行吃饭任务，如果没获取到那么就释放掉左筷子锁
                    if (right.tryLock()) {
                        try {
                            eat();
                        }finally {
                            right.unlock();
                        }
                    }
                }finally {
                    // 获取到锁执行完try中的内容一定要释放锁
                    left.unlock();
                }
            }
        }
    }
}
