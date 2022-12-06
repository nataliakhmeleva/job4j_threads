package ru.job4j.concurrent;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    @Test
    public void when5Increment() {
        CASCount count = new CASCount();
        IntStream.range(0, 5).forEach(i -> count.increment());
        assertThat(count.get()).isEqualTo(5);
    }

    @Test
    public void when100Increment() {
        CASCount count = new CASCount();
        IntStream.range(0, 100).forEach(i -> count.increment());
        assertThat(count.get()).isEqualTo(100);
    }

    @Test
    public void when15Increment2Threads() {
        CASCount count = new CASCount();
        Thread first = new Thread(
                () -> IntStream.range(0, 5).forEach(i -> count.increment()));
        Thread second = new Thread(
                () -> IntStream.range(0, 10).forEach(i -> count.increment()));
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(count.get()).isEqualTo(15);
    }
}