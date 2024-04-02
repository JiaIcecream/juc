package com.liang.concurrencypractice.chapter2;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ExpensiveObject {
    ExpensiveObject() {
        System.out.println(this);
    }
}


/*
  竟态条件例子展示：
  竟态条件发生的场景通常是： 先检查后执行
  在下面这个例子中会先检查实例是否为null，如果为null就新建一个返回，不为空就直接返回，
  但在并发条件下，线程a和线程b同时检查到了instance 为null，因此同时都会创建一个新的实例返回，这样就会造成程序的错误
 */

public class LazyInitRace {

    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if(instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }

    public static void main(String[] args) {
        LazyInitRace initRace = new LazyInitRace();
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 只会展示构建时的输出，输出几个都代表并发时创建了多少个实例
        for (int i = 0; i < 10; i++) {
            executorService.execute(initRace::getInstance);
        }
    }
}


