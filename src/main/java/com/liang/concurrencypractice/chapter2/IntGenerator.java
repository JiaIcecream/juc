package com.liang.concurrencypractice.chapter2;

public abstract class IntGenerator {
    private volatile boolean canceled = true;

    public abstract int setNext();

    public boolean isCanceled() {
        return canceled;
    }

    public void cancel() {
        canceled = true;
    }
}
