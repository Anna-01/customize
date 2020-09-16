package com.xiaodai.customize.service.multithread.produce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

/**
 * 生产者
 * @author My
 */
public class ProduceService implements  Runnable{
    Logger logger = LoggerFactory.getLogger(ProduceService.class);

    private Queue<Integer> queue;
    private int maxSize;
    int i = 0;

    public ProduceService(Queue<Integer> queue, int maxSize){
        this.queue = queue;
        this.maxSize = maxSize;
    }


    /**
     * 生产者生产商品
     */
    @Override
    public void run() {
        while(true) {
            synchronized (queue) {
                while (queue.size() >= maxSize) {
                    //生产者生产已满 等待消费者消费
                    try {
                        logger.info("生产者生产已满等待消费者 进行消费并唤醒");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                i = i + 1;
                //生产者生产商品
                logger.info("生产者生产第"+i+"个商品" + "maxSize是"+ maxSize);
                queue.offer(i);
                //通知消费者有商品可以消费了
                queue.notify();
            }

        }
    }
}
