package ru.job4j.ood.srp.report;

import ru.job4j.ood.srp.model.Employee;

public class ReportHrDescBySalary implements Sort<Employee> {
    @Override
    public double compareDesc(Employee em1, Employee em2) {
        return em2.getSalary() - em1.getSalary();
    }
}
