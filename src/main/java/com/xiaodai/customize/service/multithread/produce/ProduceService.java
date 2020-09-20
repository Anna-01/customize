package com.xiaodai.customize.service.multithread.produce;

import com.xiaodai.customize.service.multithread.consumer.ConsumerService;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 生产者
 *
 * 假如此时我们的临界区域已经满了，此时唤醒的是一个生产者线程，
 * 就会导致死锁，所以我们在这里采用的是 notifyAll() 这个方法，意思就是唤醒阻塞队列里面的全部线程，
 * 这样某一个消费者就可以去取出临界区里面的产品，从而避免死锁的发生
 * 链接：https://www.jianshu.com/p/d0c11dddafa4
 * @author My
 */
public class ProduceService implements Runnable {
    Logger logger = LoggerFactory.getLogger(ProduceService.class);

    private Queue<Integer> queue;
    private int maxSize;
    int count = 0;

    public ProduceService(Queue<Integer> queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }


    /**
     * 生产者生产商品
     */
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
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
                count = count + 1;
                //生产者生产商品
                queue.add(i);
                logger.info("生产者生产第" + count + "个商品" + "商品还有" + queue.size());
                //通知消费者有商品可以消费了
                queue.notifyAll();
            }
        }
    }


    /**
     * 相当于5个消费者 每个人可以消费10个   5x10=50
     * @param args
     */
    public static void main(String[] args) {

        int MAXNUM = 2;
        Queue<Integer> queue = new LinkedList<>();
        int maxSize = 2;

        ConsumerService consumerService = new ConsumerService(queue, maxSize);
        ProduceService produceService = new ProduceService(queue, maxSize);

        for (int i = 0; i < MAXNUM; i ++) {
            new Thread(consumerService).start();
            new Thread(produceService).start();
        }
    }

}
