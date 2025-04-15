package com.example.employee_management_system;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Search {
    private Map<String, Employee<String>> employeeDB;

    // Constructor to initialize employeeDB
    public Search(Map<String, Employee<String>> employeeDB) {
        this.employeeDB = employeeDB;
    }

    // Search by Department
    public List<Employee<String>> searchByDepartment(String department) {
        return employeeDB.values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    // Search by Name
    public List<Employee<String>> searchByName(String searchTerm) {
        return employeeDB.values().stream()
                .filter(emp -> emp.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    //  Get employees with performance above minimum rating
    public List<Employee<String>> filterByPerformance(double minRating) {
        return employeeDB.values().stream()
                .filter(emp -> emp.getPerformanceRating() >= minRating)
                .collect(Collectors.toList());
    }
    //   Employees within a specific Salary Range
    public List<Employee<String>> filterBySalaryRange(double min, double max) {
        return employeeDB.values().stream()
                .filter(emp -> emp.getSalary() >= min && emp.getSalary() <= max)
                .collect(Collectors.toList());
    }

    public Iterator<Employee<String>> getEmployeeIterator() {
        return employeeDB.values().iterator();
    }

}
