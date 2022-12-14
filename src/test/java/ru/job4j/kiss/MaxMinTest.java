package ru.job4j.kiss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaxMinTest {

    private Comparator<Integer> comparator;

    @BeforeEach
    void test() {
        comparator = Comparator.naturalOrder();
    }

    @Test
    void whenFindMax() {
        MaxMin max = new MaxMin();
        List<Integer> list = Arrays.asList(12, 11, 25, 2, 9, 1);
        assertThat(max.max(list, comparator)).isEqualTo(25);
    }

    @Test
    void whenFindMin() {
        MaxMin min = new MaxMin();
        List<Integer> list = Arrays.asList(12, 11, 25, 2, 9, 1);
        assertThat(min.min(list, comparator)).isEqualTo(1);
    }
}