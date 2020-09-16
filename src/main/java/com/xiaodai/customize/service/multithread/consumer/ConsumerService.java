package com.xiaodai.customize.service.multithread.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

/**
 * 消费者
 * @author My
 */
public class ConsumerService implements Runnable{

    Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private Queue<Integer> queue;
    private int maxSize;

    public ConsumerService(Queue<Integer> queue, int maxSize){
        this.queue = queue;
        this.maxSize = maxSize;
    }

    /**
     * 当队列为空即生产者没有消费 则等待生产者生产
     */
    @Override
    public void run() {
        while (true) {

            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        logger.info("等待生产者生产商品");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //队列里有商品 则进行消费
                int reThing = queue.remove();
                logger.info("消费者消费商品={}", reThing);
                //通知生产者可以生产了（已经消费了）
                queue.notify();
            }
        }
    }
}
