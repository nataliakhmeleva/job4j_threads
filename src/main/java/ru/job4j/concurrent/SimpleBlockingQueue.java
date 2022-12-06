package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int amount;

    public SimpleBlockingQueue(int amount) {
        this.amount = amount;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == amount) {
            wait();
        }
        queue.add(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T result = queue.poll();
        notify();
        return result;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
