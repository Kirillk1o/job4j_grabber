package ru.job4j.kiss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class MaxMinTest {

    @Test
    void whenFindMax() {
        MaxMin max = new MaxMin();
        List<Integer> list = Arrays.asList(12, 11, 25, 2, 9, 1);
        Comparator<Integer> comparator = Integer::compareTo;
        assertThat(max.max(list, comparator)).isEqualTo(25);
    }

    @Test
    void whenFindMin() {
        MaxMin min = new MaxMin();
        List<Integer> list = Arrays.asList(12, 11, 25, 2, 9, 1);
        Comparator<Integer> comparator = Integer::compareTo;
        assertThat(min.min(list, comparator)).isEqualTo(1);
    }

    @Test
    void whenFindMaxString() {
        MaxMin max = new MaxMin();
        List<String> list = Arrays.asList("privet", "poka", "Gabe Nuel", "Jakarta");
        Comparator<String> comparator = String::compareTo;
        assertThat(max.max(list, comparator)).isEqualTo("privet");
    }

    @Test
    void whenFindMinString() {
        MaxMin min = new MaxMin();
        List<String> list = Arrays.asList("privet", "poka", "Gabe Nuel", "Jakarta");
        Comparator<String> comparator = String::compareTo;
        assertThat(min.min(list, comparator)).isEqualTo("Gabe Nuel");
    }
}