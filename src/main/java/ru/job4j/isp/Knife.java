package ru.job4j.isp;
/*
Нарушение принципа ISP по причине того, что классы не все могу реализовать методы интерфейса - Iweapon.
Чтобы принцип ISP соблюдался, нам нужно вынести методы Iweapon в отдельные интерфейсы
и уже нужные классы имплементить от соответсвующих по логики им интерфейсов.
 */
public class Knife implements Iweapon {
    @Override
    public void damage() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void alternativeDamage() {

    }
}
