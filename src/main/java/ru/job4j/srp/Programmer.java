package ru.job4j.srp;

/*
Принцип SRP нарушается так как класс представляет сам объект programmer, но и еще берет на себя роль/задачу
по реализации различных методов по работе с этим объектом.
Решение- нужно чтобы класc отвечал только за само представление programmer
а методы делегировать на реализацию другим классам.
К примеру класс WeekDaysProgrammer(который описывает то чем занимается программист каждый будний день)
И WeekendDays (который описывает то что делает программист на выходных)
в методе Main тогда будет
такая реализация
WeeDaysProgrammer week = new WeekDaysProgrammer();
week.go("Прогает на Java", programer);
Так же и с WeekendDays.
 */

public class Programmer {
    String name;
    String female;
    int age;

    public Programmer(String name, String female, int age) {
        this.name = name;
        this.female = female;
        this.age = age;
    }

    public void go() {
        System.out.println("отправить объект программировать на Java");
    }

    public void sleep() {
        System.out.println("Отправить объект спать");
    }

    public static void main(String[] args) {
        Programmer programmer = new Programmer("Kirill", "Kirillov", 27);
        programmer.go();
        programmer.sleep();
    }
}
