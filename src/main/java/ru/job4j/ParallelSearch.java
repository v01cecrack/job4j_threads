package ru.job4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    public final T[] array;
    private final T target;
    private final int start;
    private final int end;

    public ParallelSearch(T[] array, T target, int start, int end) {
        this.array = array;
        this.target = target;
        this.start = start;
        this.end = end;
    }

    public ParallelSearch(T[] array, T target) {
        this.array = array;
        this.target = target;
        this.start = 0;
        this.end = this.array.length - 1;
    }

    private int linearSearch() {
        for (int i = start; i < end; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        int length = end - start;
        if (length <= 10) {
            return linearSearch();
        }
        int middle = (start + end) / 2;
        ParallelSearch<T> left = new ParallelSearch<>(array, target, start, middle);
        ParallelSearch<T> right = new ParallelSearch<>(array, target, middle + 1, end);
        left.fork();
        right.fork();
        var leftResult = left.join();
        var rightResult = right.join();
        return leftResult == -1 ? rightResult : leftResult;
    }

    public static <T> int search(T[] array, T target) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelSearch<>(array, target));
    }

}
