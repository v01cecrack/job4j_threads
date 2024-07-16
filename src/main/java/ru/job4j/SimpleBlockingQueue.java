package ru.job4j;

import net.jcip.annotations.GuardedBy;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= limit) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        notifyAll();
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        final Thread consumer = new Thread(
                () -> {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            System.out.println(queue.poll());
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        consumer.start();
        final Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }

        );
        producer.start();
        producer.join();

        consumer.interrupt();
    }
}
