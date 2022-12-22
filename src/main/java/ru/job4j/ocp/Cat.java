package ru.job4j.ocp;

public class Cat extends Animals {

    public Cat(String name) {
        super(name);
    }

    @Override
    public void voice() {
        System.out.println("Мяу");
    }
}
