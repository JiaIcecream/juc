package com.liang.concurrencypractice.chapter2;


import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class Foo{
    private static final ConcurrentHashMap<String,Object> hashMap = new ConcurrentHashMap<>();

    // 目的是向map中添加数据，但是多线程导致在已经存在key的情况下第二次向其中添加key是成功，并将上一次的值覆盖
    // 结论：两个线程安全的类组成的类并不一定是线程安全的
    public static void putIfAbsent(String key, Function<String,Object> callback){
        if(!hashMap.contains(key)) {
            hashMap.put(key,callback.apply(key));
        }
    }

    public static ConcurrentHashMap<String,Object> getHashMap() {
        return hashMap;
    }
}


public class ConcurrentHashMapTest {
    public static void main(String[] args) throws InterruptedException {
        new Thread(()-> {
            Foo.putIfAbsent("key",str-> {
                String s = str + "11111";
                System.out.println(s);
                return s;
            });
        }).start();

        new Thread(()-> {
            Foo.putIfAbsent("key",str-> {
                String s = str + "22222";
                System.out.println(s);
                return s;
            });
        }).start();

        Thread.sleep(1000);

        System.out.println(Foo.getHashMap());
    }
}
