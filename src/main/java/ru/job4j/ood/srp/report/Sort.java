package ru.job4j.ood.srp.report;

public interface Sort<T> {

    double compareDesc(T o1, T o2);
}
