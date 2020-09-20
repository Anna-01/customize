package com.xiaodai.customize.service.multithread.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Queue;

/**
 * 消费者
 * @author My
 */
public class ConsumerService implements Runnable{

    Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private Queue<Integer> queue;
    private HashMap<Integer, String> map;
    private int maxSize;

    public ConsumerService(Queue<Integer> queue, int maxSize){
        this.queue = queue;
        this.maxSize = maxSize;
    }

    /**
     * 当队列为空即 没有可消费商品 则等待生产者生产
     */
    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {

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
                logger.info("消费者消费第={}个商品， " +
                        "" +
                        "还剩{}个", reThing, queue.size());
                //通知生产者可以生产了（已经消费了）
                queue.notifyAll();

            }
        }
    }
}
