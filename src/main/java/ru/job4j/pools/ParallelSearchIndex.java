package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelSearchIndex(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (from == to && array[from].equals(value)) {
            return from;
        }
        if (to - from <= 10) {
            return searchIndex();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndex<T> leftSort = new ParallelSearchIndex<>(array, value, from, mid);
        ParallelSearchIndex<T> rightSort = new ParallelSearchIndex<>(array, value, mid + 1, to);
        leftSort.fork();
        rightSort.fork();
        int left = leftSort.join();
        int right = rightSort.join();
        return sortIndex(left, right);
    }

    public static <T> int sort(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearchIndex<>(array, value, 0, array.length - 1));
    }

    private int searchIndex() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(value)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    private int sortIndex(int left, int right) {
        if (left != -1) {
            return left;
        }
        if (right != -1) {
            return right;
        }
        return -1;
    }
}
