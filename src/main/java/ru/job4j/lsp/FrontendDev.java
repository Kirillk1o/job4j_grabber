package ru.job4j.lsp;
/*
нарушение принципа LSP по причине того, что базовое поведение дочеренего класса стало отличаться от поведения род класса.

 */
public class FrontendDev extends Employee {
    double codeQuality;
    int workingHours;

    public FrontendDev(String name, int age, int salary, String gradle, double codeQuality, int workingHours) {
        super(name, age, salary, gradle);
        this.codeQuality = codeQuality;
        this.workingHours = workingHours;
    }

    public double getCodeQuality() {
        return codeQuality;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setSalary(String name) {
        this.getName();
        this.workingHours = 4;
    }
}
