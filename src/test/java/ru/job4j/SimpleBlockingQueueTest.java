package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;


class SimpleBlockingQueueTest {

    @Test
    void testSimpleBlockingQueue() throws InterruptedException {

        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(3);
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                simpleBlockingQueue.offer(i);
            }
        });

        Thread consumer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    buffer.add(simpleBlockingQueue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);

    }

}