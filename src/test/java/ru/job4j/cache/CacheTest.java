package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {
    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        assertThat(cache.add(model)).isTrue();
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        cache.delete(model);
        assertThat(cache.get(0)).isNull();
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        assertThat(cache.update(model)).isTrue();
        assertThat(cache.get(1)).isEqualTo(new Base(1, 2));
    }

    @Test
    public void whenThrowException() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        assertThatThrownBy(() -> cache.update(new Base(1, 3)))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Versions are not equal");
    }
}