package ru.job4j;

import org.junit.jupiter.api.Test;


class SimpleBlockingQueueTest {

    @Test
    void testSimpleBlockingQueue() throws InterruptedException {

        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(3);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                simpleBlockingQueue.offer(i);
                System.out.println("Produced " + i);

            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                Integer value = null;
                try {
                    value = simpleBlockingQueue.poll();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Consumed " + value);
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }

}