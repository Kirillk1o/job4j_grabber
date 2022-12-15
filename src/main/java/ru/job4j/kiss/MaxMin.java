package ru.job4j.kiss;

import java.util.*;
import java.util.function.Predicate;

public class MaxMin {

    public <T> T met(List<T> value, Comparator<T> comparator, Predicate<Integer> predicate) {
        if (value.isEmpty()) {
            throw new IllegalArgumentException("List is Empty");
        }
        T tmp = value.get(0);
        for (T val : value) {
            if (predicate.test(comparator.compare(tmp, val))) {
                tmp = val;
            }
        }
        return tmp;
    }

    public <T> T max(List<T> value, Comparator<T> comparator) {
        return met(value, comparator, s -> s < 0);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return met(value, comparator, s -> s > 0);
    }
}
