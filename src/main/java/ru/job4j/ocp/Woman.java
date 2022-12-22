package ru.job4j.ocp;

/*
в данном примере принцип OCP нарушен путем наследования от класса Cat
между сущностями Woman и Cat нет связи "is A" Женщина не может быть кошкой
ибо кошка относится к классу животных.
 */
public class Woman extends Cat {

    public Woman(String name) {
        super(name);
    }

    @Override
    public void voice() {
        System.out.println("Я женщина- кошка");
    }
}
