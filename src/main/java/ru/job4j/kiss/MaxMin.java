package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class MaxMin {

    public <T> T met(List<T> value, Comparator<T> comparator, Predicate<Integer> predicate) {
        if (value.isEmpty()) {
            throw new IllegalArgumentException("List is Empty");
        }
        T tmp = value.get(0);
        for (int i = 1; i < value.size(); i++) {
            if (predicate.test(comparator.compare(tmp, value.get(i)))) {
                tmp = value.get(i);
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
