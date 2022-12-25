package ru.job4j.lsp;

public abstract class Hero {

    abstract void voice();

    public void atack() {
        System.out.println("Наношу урон");
    }

    public void serchQuest() {
        System.out.println("Ищу новые задания");
    }
}
