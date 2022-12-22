package ru.job4j.ood.srp.report;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.Store;

import java.util.function.Predicate;

public class ReportHr implements Report {
    private final Store store;
    private final ReportHrDescBySalary sortDesc;

    public ReportHr(Store store, ReportHrDescBySalary sortDesc) {
        this.store = store;
        this.sortDesc = sortDesc;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Salary;")
                .append(System.lineSeparator());
        for (Employee employee : store.findBy(filter)) {
            sortDesc.compareDesc(employee, employee);
            text.append(employee.getName()).append(" ")
                    .append(employee.getSalary())
                    .append(System.lineSeparator());
        }
        return text.toString();

    }
}
