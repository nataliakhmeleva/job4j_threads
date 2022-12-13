package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getSum(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < matrix.length; i++) {
                sums[i] = getSum(matrix, i);
            }
            return sums;
        });
        return sums;
    }

    private static Sums getSum(int[][] matrix, int index) {
        int row = 0;
        int column = 0;
        for (int j = 0; j < matrix.length; j++) {
            row += matrix[index][j];
            column += matrix[j][index];
        }
        return new Sums(row, column);
    }
}
