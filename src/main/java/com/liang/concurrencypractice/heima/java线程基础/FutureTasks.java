package com.liang.concurrencypractice.heima.java线程基础;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FutureTasks {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("running...");
                TimeUnit.SECONDS.sleep(10);
                return 100;
            }
        });

        Thread t1 = new Thread(task,"t1");

        t1.start();

        System.out.println(task.get());

    }
}
