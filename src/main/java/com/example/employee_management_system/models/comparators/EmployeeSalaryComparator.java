package com.example.employee_management_system.models.comparators;

import com.example.employee_management_system.models.Employee;
import java.util.Comparator;

public class EmployeeSalaryComparator<T> implements Comparator<Employee<T>> {
    @Override
    public int compare(Employee<T> e1, Employee<T> e2) {
        return Double.compare(e2.getSalary(), e1.getSalary()); // Descending
    }
}
