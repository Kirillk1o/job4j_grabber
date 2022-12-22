package ru.job4j.ocp;

public class Customer {
/*
Этот класс не соответствует принципу OCP, т.к. использует в своем методе объект, который не является абстрактным.
Правильно было бы, согласно принципу, создать абстрактный объет AbstractCarShop а от него уже унаследовать CarShop
 */
    void buy() {
        CarShop shop = new CarShop();
    }
}
