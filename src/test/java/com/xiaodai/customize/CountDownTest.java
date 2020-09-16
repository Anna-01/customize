package com.xiaodai.customize;

import com.xiaodai.customize.service.json.JsonToBeanService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@SpringBootTest
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class CountDownTest {

    @Resource
    private JsonToBeanService jsonToBeanService;
    public static Date date = new Date();
    //并发数
    private static final int threadNum = 30;
    //倒计时数 发令枪 用于制造线程的并发执行
    private static CountDownLatch cdl = new CountDownLatch(threadNum);

    public static final String jstring = "{\"name\":\"lijiaxing\",\"age\":\"11\", \"creatTime\":\"" + date + "\"}";


    /**
     * 并发和并行是即相似又有区别(微观概念)：
     * 并行：指两个或多个事件在同一时刻点发生；
     * 并发：指两个或多个事件在同一时间段内发生。
     * 多线程并行
     * @param args
     */
    @Test
    //@PostConstruct
    public void testCountDown(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //jsonToBeanService.getJson(jstring);
                        System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发布口令");
                        cdOrder.await();
                        System.out.println("选手" + Thread.currentThread().getName() + "已接受裁判口令");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("选手" + Thread.currentThread().getName() + "到达终点");
                        cdAnswer.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        try {
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("裁判" + Thread.currentThread().getName() + "即将发布口令");
            cdOrder.countDown();
            System.out.println("裁判" + Thread.currentThread().getName() + "已发送口令，正在等待所有选手到达终点");
            cdAnswer.await();
            System.out.println("所有选手都到达终点");
            System.out.println("裁判" + Thread.currentThread().getName() + "汇总成绩排名");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    /**
     * 模拟并发
     */
    @Test
    @PostConstruct
    public void testConcurrent() {
        for(int i = 0;i< threadNum;i++) {
            //new多个子线程
            new Thread(new ThreadClass(jsonToBeanService)).start();
            //计数器-1
            cdl.countDown();
        }
        try {
            //主线程 等待 子线程执行完 等待
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //多线程执行类
    public class ThreadClass implements Runnable{
        private JsonToBeanService jsonToBeanService;

        public ThreadClass(JsonToBeanService showService) {
            this.jsonToBeanService = showService;
        }

        @Override
        public void run() {
            try {
                //所有子线程在这里等待，当所有线程实例化后，停止等待
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //执行业务方法
            jsonToBeanService.getJson(jstring);
        }

    }

}
