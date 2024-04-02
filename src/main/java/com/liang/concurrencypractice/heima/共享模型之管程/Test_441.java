package com.liang.concurrencypractice.heima.共享模型之管程;

import java.util.ArrayList;

public class Test_441 {
    public static void main(String[] args) {
        ThreadUnsafe unsafe = new ThreadUnsafe();
        // 多条线程同时对list进行读写操作
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                unsafe.method1(5000);
            }).start();
        }
    }
}

/*
    每个线程都有自己独立的虚拟栈内存，每个线程在调用方法的时候会在每个线程的栈帧内存中创建备份，因此不存在共享,不会造成线程不安全。
 */

/*
    而成员变量是会被线程共享的，当进行读写操作的时候就会造成线程不安全
 */

class ThreadUnsafe {

    // 类中的成员变量
    ArrayList<String> list = new ArrayList<>();

    public void method1(int loopNum) {
        for (int i = 0; i < loopNum; i++) {
            // 局部变量对成员变量的引用
            method2(list);
            method3(list);
        }
    }
    // 锁住同一个实例，那么在操作的时候就是原子的不会被打断

    private void method2(ArrayList list) {
        list.add("1");
    }

    private void method3(ArrayList list) {
        list.remove(0);
    }
}

//class Threadsafe {
//    public void method1(int loopNum) {
//        ArrayList<String> list = new ArrayList<>();
//        for (int i = 0; i < loopNum; i++) {
//            // 局部变量对成员变量的引用
//            method2(list);
//            method3(list);
//        }
//    }
//    private void method2(ArrayList list) {
//        list.add("1");
//    }
//
//    private void method3(ArrayList list) {
//        list.remove(0);
//    }
//}
