package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.pools.RolColSum.*;


class RolColSumTest {
    @Test
    public void whenRowSums() {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums = sum(array);
        assertThat(sums[0].getRowSum()).isEqualTo(6);
    }

    @Test
    public void whenColumnSums() {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums = sum(array);
        assertThat(sums[2].getColSum()).isEqualTo(18);
    }

    @Test
    public void whenSums() {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums = sum(array);
        Sums[] expected = new Sums[]{
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        assertThat(sums).isEqualTo(expected);
    }

    @Test
    public void whenAsyncSums() {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums[] sums = asyncSum(array);
        Sums[] expected = new Sums[]{
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        assertThat(sums).isEqualTo(expected);
    }
}