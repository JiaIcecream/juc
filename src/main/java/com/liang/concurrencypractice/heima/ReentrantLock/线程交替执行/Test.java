package com.liang.concurrencypractice.heima.ReentrantLock.线程交替执行;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
    public static void main(String[] args) {
        SyncLoopPrint loopPrint = new SyncLoopPrint(1, 3);
        new Thread(() -> {
            loopPrint.prin(1,2,"a");
        },"t1").start();
        new Thread(() -> {
            loopPrint.prin(2,3,"b");
        },"t2").start();
        new Thread(() -> {
            loopPrint.prin(3,1,"c");
        },"t3").start();
    }
}

@Slf4j
class SyncLoopPrint {

    private Integer flag;
    private Integer loopNum;

    // 构造函数
    SyncLoopPrint(Integer flag, Integer loopNum) {
        this.flag = flag;
        this.loopNum = loopNum;
    }

    public void prin(Integer waitFlag,Integer nextFlag,String msg) {
        for (Integer i = 0; i < loopNum; i++) {
            synchronized (this) {
                while(this.flag != waitFlag) {
                    // 当前线程等待
                    try {
                        this.wait();
                    }catch (Exception e) {
                        log.error("被打断");
                    }
                }
                // 输出打印信息
                log.info(msg);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}
