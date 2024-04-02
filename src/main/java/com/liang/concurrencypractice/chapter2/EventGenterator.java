package com.liang.concurrencypractice.chapter2;

public class EventGenterator extends IntGenerator{

    private int currentEventValue = 0;
    @Override
    public int setNext() {
        ++ currentEventValue;
        ++ currentEventValue;
        return currentEventValue;
    }

    public static void main(String[] args) {
        final EventGenterator eventGenterator = new EventGenterator();
        Evenchecker.test(eventGenterator);
    }
}
