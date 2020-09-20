package com.xiaodai.customize.service.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 生产者消费者lock
 * @author My
 */
public class ProAndConByLock {
    static class Consumer implements Runnable {

        private List<Integer> queue;

        public Consumer(List<Integer> queue) {
            this.queue = queue;
        }

        //创建锁对象
        private Lock lock = new ReentrantLock();
        //创建锁的条件，情况
        private Condition condition = lock.newCondition();


        @Override
        public void run() {
            try {
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            System.out.println("Queue is Empty");
                            queue.wait();
                        }
                        int i = queue.remove(0);
                        queue.notifyAll();
                        System.out.println(Thread.currentThread().getName() + " 消费了:" + i + "还剩:" + queue.size());
                        Thread.sleep(100);
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class Producer implements Runnable {
        private List<Integer> queue;
        private int length;

        public Producer(List<Integer> queue, int length) {
            this.queue = queue;
            this.length = length;
        }

        @Override
        public void run() {
            try {
                    synchronized (queue) {
                        while (queue.size() >= length) {
                            queue.wait();
                        }
                        queue.add(1);
                        System.out.println(Thread.currentThread().getName() + "生产了" + 1 + "现在有" + queue.size());
                        Thread.sleep(100);
                        queue.notifyAll();
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> queue = new ArrayList<>();
        int length = 10;
        Thread c1 = new Thread(new Consumer(queue));
        Thread p1 = new Thread(new Producer(queue, length));
        c1.setName("消费者1");
        p1.setName("生产者1");
        c1.start();
        p1.start();
    }
}