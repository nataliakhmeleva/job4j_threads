package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ParallelSearchTest {
    @Test
    void whenTypeInteger() {
        Integer[] array = {1, 2, 3, 4, 5, 6};
        assertThat(ParallelSearchIndex.sort(array, 4)).isEqualTo(3);
    }

    @Test
    void whenTypeString() {
        String[] array = {"Ivan", "Stepan", "Igor", "Kirill"};
        assertThat(ParallelSearchIndex.sort(array, "Stepan")).isEqualTo(1);
    }

    @Test
    void whenRecursiveSearch() {
        Integer[] array = new Integer[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        assertThat(ParallelSearchIndex.sort(array, 592)).isEqualTo(592);
    }

    @Test
    void whenNotFoundValue() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        assertThat(ParallelSearchIndex.sort(array, 14)).isEqualTo(-1);
    }
}