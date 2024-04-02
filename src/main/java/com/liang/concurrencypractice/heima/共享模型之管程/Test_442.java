package com.liang.concurrencypractice.heima.共享模型之管程;

import java.util.ArrayList;

public class Test_442 {
    public static void main(String[] args) {
        Threadsafe unsafe = new Threadsafe();
        // 多条线程同时对list进行读写操作
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                unsafe.method1(5000);
            }).start();
        }
    }
}

class Threadsafe {
    public void method1(int loopNum) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < loopNum; i++) {
            // 局部变量对成员变量的引用
            method2(list);
            method3(list);
        }
    }
    private void method2(ArrayList<String> list) {
        list.add("1");
    }

    public void method3(ArrayList<String> list) {
        list.remove(0);
    }
}

class ThreadSafeSubClass extends Threadsafe {

    @Override
    public void method3(ArrayList<String> list) {
        // 子类使用了父类的局部变量list， 开启一个新线程进行操作造成了线程不安全
        // 可以理解为 两个线程的list引用指向了同一个，并且list不是线程安全的，因此会造成线程不安全
        // 可以使用final、private 关键字代替public，是类方法不能被重写
        new Thread(() -> {
            list.remove(0);
        });
    }
}