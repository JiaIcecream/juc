package com.liang.concurrencypractice.heima.共享模型之管程;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "TestShareError2")
public class TestShareError2 {
    public static void main(String[] args) throws InterruptedException {

        Room room = new Room();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.incream();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                room.decream();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();


        log.info("count ..{}",room.getCount());
    }


}

class Room {

    private static Integer count = 0;


    /*
         synchronized 放在非静态方法上与
         synchronized(this) 效果相同 都是锁住类的实例
     */

    /*
        静态方法：
        class Test{
            public synchronized static void test() {

            }
        }
        等价于
        class Test{
            public static void test() {
                synchronized(Test.class) {

                }
            }
        }
     */

    /*
        线程八锁，考察线程锁住的是哪个对象
     */

    public synchronized void incream() {
//        synchronized (this) {
//
//        }
        count++;
    }

    public void decream() {
        synchronized (this) {
            count--;
        }
    }

    public Integer getCount() {
        synchronized (this) {
            return count;
        }
    }

}