package com.liang.concurrencypractice.chapter2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Evenchecker implements Runnable{

    private final IntGenerator generator;
    private final int id;

    public Evenchecker(IntGenerator generator, int id) {
        this.generator = generator;
        this.id = id;
    }

    @Override
    public void run() {
        while(!generator.isCanceled()) {
            int value = generator.setNext();
            if( value %2 != 0) {
                System.out.println(value + " not event " + " ,由 id 为： " + id + "任务发现");
                generator.cancel();
            }
        }
    }

    public static void test(IntGenerator generator, int count) {
        final ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i< count; i++) {
            executorService.execute(new Evenchecker(generator,i));
        }
        executorService.shutdown();
    }

    public static void test(IntGenerator generator) {
        test(generator,200);
    }
}
