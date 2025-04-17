package com.example.employee_management_system.services;

import com.example.employee_management_system.models.Employee;
import com.example.employee_management_system.models.comparators.EmployeeSalaryComparator;

import java.util.*;
import java.util.stream.Collectors;

public class SalaryManagement<T> {

    private List<Employee<T>> employees;

    public SalaryManagement(List<Employee<T>> employees) {
        this.employees = employees;
    }

    // Give a salary raise to employees with high performance (rating ≥ 4.5)
    public void giveRaiseToHighPerformers(double raisePercentage) {
        employees.stream()
                .filter(emp -> emp.getPerformanceRating() >= 4.5)
                .forEach(emp -> {
                    double newSalary = emp.getSalary() * (1 + raisePercentage / 100);
                    emp.setSalary(newSalary);
                });
    }

    // Get top 5 highest-paid employees
    public List<Employee<T>> getTop5HighestPaid() {
        EmployeeSalaryComparator<T> comparator = new EmployeeSalaryComparator<>();
        return employees.stream()
                .sorted(comparator)
                .limit(5)
                .collect(Collectors.toList());
    }

    // Calculate average salary of employees in a specific department
    public double getAverageSalaryByDepartment(String department) {
        return employees.stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .mapToDouble((Employee<?> emp) -> emp.getSalary())
                .average()
                .orElse(0.0); // default if no employees found
    }
}
