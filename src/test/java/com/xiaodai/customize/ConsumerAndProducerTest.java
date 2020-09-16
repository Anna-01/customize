package com.xiaodai.customize;

import com.xiaodai.customize.service.multithread.consumer.ConsumerService;
import com.xiaodai.customize.service.multithread.produce.ProduceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

@SpringBootTest
public class ConsumerAndProducerTest {
    @Test
    void testConsumerAndProducerTest() {
        Queue<Integer> queue = new LinkedList<>();
        int maxSize = 2;

        ConsumerService consumerService = new ConsumerService(queue, maxSize);
        ProduceService produceService = new ProduceService(queue,maxSize);

        Thread consumer = new Thread(consumerService);
        Thread producer = new Thread(produceService);

        consumer.start();
        producer.start();

    }
}
