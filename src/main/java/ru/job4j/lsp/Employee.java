package ru.job4j.lsp;

public abstract class Employee {
    private String name;
    private int age;
    private int salary;
    private String grade;

    public Employee(String name, int age, int salary, String grade) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.grade = grade;
    }

    public void setSalary(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getSalary() {
        return salary;
    }

    public String getGrade() {
        return grade;
    }
}
