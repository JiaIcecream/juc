package com.liang.concurrencypractice.heima.买票练习;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class Test {
    public static void main(String[] args) throws InterruptedException {
        ticketWindow ticketWindow = new ticketWindow(1000);

        List<Integer> ticketCount = new Vector<>();

        List<Thread> threadList = new ArrayList<>();

        // 统计卖出的和剩余的相加是否为1000

        for (int i = 0; i < 2000; i++) {

            Thread thread = new Thread(() -> {
                synchronized (ticketWindow) {
                    int mount = ticketWindow.sellTicket(getRandom());
                    ticketCount.add(mount);
                }
            });

            thread.start();
            threadList.add(thread);
        }



        // 阻塞获取结果
        for (Thread thread : threadList) {
            thread.join();
        }

        log.info("剩余票数为:{}",ticketWindow.getCount());

        log.info("售出票数和剩余票数合计为: {}",ticketWindow.getCount() + ticketCount.stream().mapToInt(Integer::intValue).sum());

        /*
            运行结果：
            15:49:57.823 [main] INFO com.liang.concurrencypractice.heima.买票练习.Test -- 剩余票数为:0
            15:49:57.826 [main] INFO com.liang.concurrencypractice.heima.买票练习.Test -- 售出票数和剩余票数合计为: 1005

            分析原因：
             找出临界区代码：对共享变量有读写操作：通过观察可以发现 ticketWindow和 ticketCount 都为共享变量
             多线程对 ticketWindow.sellTicket(getRandom()); 进行访问
             sellTicket方法中使用了成员变量 count 并对成员变量进行了读写操作，这样会造成多线程不安全
             因此需要对sellTicket方法设置synchronize 确保方法内的操作为原子操作

           为什么不需要对： ticketCount.add(mount); 进行多线程安全规范？
           因为ticketCount 是new Vector<>();本身是线程安全的。

           public synchronized boolean add(E e) {
                modCount++;
                add(e, elementData, elementCount);
                return true;
           }

         */

    }

    static Random random = new Random();

    public static int getRandom() {
        return random.nextInt(5) + 1;
    }

}


class ticketWindow {

    private int count;

    public ticketWindow(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public synchronized int sellTicket(int num) {
        if(this.count >= num) {
            this.count -= num;
            return num;
        }else {
            return 0;
        }
    }

}
