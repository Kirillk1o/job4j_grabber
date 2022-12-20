package ru.job4j.srp;

/*
В данном случае нарушение SRP происходит
потому что не ясно... Что конкретно описывает этот класс
Если Человек - Петя
то здесь мы не можем сказать однозначно чем он занимается (лечит людей- Врач)
или же (Арестовывает преступников - полицейский)
Нам нужно было делегировать реализации этих интерфейсов 2м отдельным классам Doctor
Police
 */
public class Human implements PoliceAction, DoctorAction {

    @Override
    public void heal() {
        System.out.println("Лечить");
    }

    @Override
    public void arrest() {
        System.out.println("Арестовать");
    }
}
