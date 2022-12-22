package ru.job4j.ood.srp.report;

import org.junit.jupiter.api.Test;
import ru.job4j.ood.srp.model.Employee;
import ru.job4j.ood.srp.store.MemStore;

import static org.assertj.core.api.Assertions.assertThat;

class ReportHrTest {

    @Test
    public void whenGeneratedForHrDescBySalary() {
        MemStore store = new MemStore();
        Employee worker1 = new Employee("Alena", 1000);
        Employee worker2 = new Employee("Valentina", 3000);
        store.add(worker1);
        store.add(worker2);
        ReportHrDescBySalary sortDesc = new ReportHrDescBySalary();
        sortDesc.compareDesc(worker1, worker2);
        Report engine = new ReportHr(store, sortDesc);
        StringBuilder expect = new StringBuilder()
                .append("Name; Salary;")
                .append(System.lineSeparator())
                .append(worker1.getName()).append(" ")
                .append(worker1.getSalary())
                .append(System.lineSeparator())
                .append(worker2.getName()).append(" ")
                .append(worker2.getSalary())
                .append(System.lineSeparator());
        assertThat(engine.generate(em -> true)).isEqualTo(expect.toString());
    }
}