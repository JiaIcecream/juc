package com.liang.concurrencypractice.heima.生产者消费者;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Test1 {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);

        // 创建三个生产者
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                queue.take(new Message(id,"产品" + id));
            },"生产者" + i).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    Message message = queue.get();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}

@Slf4j
class MessageQueue {
    private LinkedList<Message> list = new LinkedList<>();

    private final Integer capacity;

    public MessageQueue(Integer capacity) {
        this.capacity = capacity;
    }

    public Message get() {
        // 这里共享访问的肯定是存 message 的list，因此 synchronized 应该锁定在list上
        synchronized (list) {
            while(list.isEmpty()) {
                // 如果列表为null，表示没办法消费，需要阻塞等待生产者生产
                try {
                    log.info("队列已空...");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // list不为null 之后将队列的尾部元素返回
            Message message = list.removeFirst();
            // 删掉并返回队尾的元素，并唤醒所有list线程使得所有因容量已满被阻塞的线程继续执行
            log.info("消费产品id:{},内容为:{}",message.getId(),message.getMsg());
            list.notifyAll();
            return message;
        }
    }

    public void take(Message message) {
        // 生产者将产品推入到list中
        synchronized (list) {
            while (list.size() == capacity) {
                // 队列已满，阻塞等待消费
                try {
                    log.info("队列已满...");
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // 将数据添加到队列里,并唤醒所有线程，使得因为队列为null不能继续执行的消费者继续执行
            list.add(message);
            log.info("已生产id：{}，内容：{}",message.getId(),message.getMsg());
            list.notifyAll();
        }
    }
}

// final 使得该类不能被继承，类只有构造函数会被赋值，使得无法通过别的方式改变实例的值，这在一定程度上保证了线程安全
final class Message {
    private int id;
    private String msg;

    public Message(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }
}
