package com.example.employee_management_system.db;

import com.example.employee_management_system.models.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeDatabase<T> {
    private Map<T, Employee<T>> employeeDB = new HashMap<>();

//    Create a new Employee
    public void addEmployee(Employee<T> employee){
        employeeDB.put(employee.getEmployeeID(), employee);
    }

//    Delete a new Employee
    public void removeEmployee(T EmployeeID){
        employeeDB.remove(EmployeeID);
    }

//    Get all employees in the systems
    public List<Employee<T>> getAllEmployees(){
        return employeeDB.values().stream().toList();
    }

//    Update Employee details in the System
public boolean updateEmployeeFields(T employeeId, Map<String, Object> updates) {
    Employee<T> employee = employeeDB.get(employeeId);
    if (employee == null) {
        return false;
    }

    for (Map.Entry<String, Object> entry : updates.entrySet()) {
        String field = entry.getKey().toLowerCase();
        Object newValue = entry.getValue();

        switch (field) {
            case "name":
                if (newValue instanceof String) {
                    employee.setName((String) newValue);
                }
                break;
            case "department":
                if (newValue instanceof String) {
                    employee.setDepartment((String) newValue);
                }
                break;
            case "salary":
                if (newValue instanceof Number) {
                    employee.setSalary(((Number) newValue).doubleValue());
                }
                break;
            case "performancerating":
                if (newValue instanceof Number) {
                    employee.setPerformanceRating(((Number) newValue).doubleValue());
                }
                break;
            case "yearsofexperience":
                if (newValue instanceof Number) {
                    employee.setYearsOfExperience(((Number) newValue).intValue());
                }
                break;
            case "isactive":
                if (newValue instanceof Boolean) {
                    employee.setActive((Boolean) newValue);
                }
                break;
            default:
                System.out.println("Invalid field: " + field);
                // Continue updating other fields, but optionally you can return false here
                break;
        }
    }

    return true;
}

    // Search by Department
    public List<Employee<T>> searchByDepartment(String department) {
        return employeeDB.values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    // Search by Name
    public List<Employee<T>> searchByName(String searchTerm) {
        return employeeDB.values().stream()
                .filter(emp -> emp.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }

    //  Get employees with performance above minimum rating
    public List<Employee<T>> filterByPerformance(double minRating) {
        return employeeDB.values().stream()
                .filter(emp -> emp.getPerformanceRating() >= minRating)
                .collect(Collectors.toList());
    }

    //   Employees within a specific Salary Range
    public List<Employee<T>> filterBySalaryRange(double min, double max) {
        return employeeDB.values().stream()
                .filter(emp -> emp.getSalary() >= min && emp.getSalary() <= max)
                .collect(Collectors.toList());
    }

    public void displayEmployeesWith(List<Employee<T>> employees) {
        if (employees.isEmpty()) {
            System.out.println("\nNo employees found.\n");
            return;
        }

        // Table header with borders
        String headerFormat = "║ %-10s ║ %-20s ║ %-20s ║ %-10s ║ %-15s ║ %-15s ║ %-8s ║%n";
        String border = "╠════════════╬══════════════════════╬══════════════════════╬════════════╬═════════════════╬═════════════════╬══════════╣";
        String topBorder = "╔════════════╦══════════════════════╦══════════════════════╦════════════╦═════════════════╦═════════════════╦══════════╗";
        String bottomBorder = "╚════════════╩══════════════════════╩══════════════════════╩════════════╩═════════════════╩═════════════════╩══════════╝";

        System.out.println(topBorder);
        System.out.printf(headerFormat,
                "ID", "Name", "Department", "Salary", "Performance", "Experience", "Active");
        System.out.println(border);

        // Table rows
        String rowFormat = "║ %-10s ║ %-20s ║ %-20s ║ $%,9.2f ║ %-15.1f ║ %-15d ║ %-8b ║%n";
        for (Employee<T> emp : employees) {
            System.out.printf(rowFormat,
                    emp.getEmployeeID(),
                    emp.getName(),
                    emp.getDepartment(),
                    emp.getSalary(),
                    emp.getPerformanceRating(),
                    emp.getYearsOfExperience(),
                    emp.getIsActive());
        }
        System.out.println(bottomBorder);
        System.out.printf("%nFound %d employee(s)%n%n", employees.size());
    }

    public void displayEmployeesWithStreams(List<Employee<T>> employees) {
        if (employees.isEmpty()) {
            System.out.println("\nNo employees found.\n");
            return;
        }

        // Table header with borders
        String headerFormat = "║ %-10s ║ %-20s ║ %-20s ║ %-10s ║ %-15s ║ %-15s ║ %-8s ║%n";
        String border = "╠════════════╬══════════════════════╬══════════════════════╬════════════╬═════════════════╬═════════════════╬══════════╣";
        String topBorder = "╔════════════╦══════════════════════╦══════════════════════╦════════════╦═════════════════╦═════════════════╦══════════╗";
        String bottomBorder = "╚════════════╩══════════════════════╩══════════════════════╩════════════╩═════════════════╩═════════════════╩══════════╝";

        System.out.println(topBorder);
        System.out.printf(headerFormat,
                "ID", "Name", "Department", "Salary", "Performance", "Experience", "Active");
        System.out.println(border);

        // Table rows using stream
        String rowFormat = "║ %-10s ║ %-20s ║ %-20s ║ $%,9.2f ║ %-15.1f ║ %-15d ║ %-8b ║%n";
        employees.stream()
                .forEach(emp -> System.out.printf(rowFormat,
                        emp.getEmployeeID(),
                        emp.getName(),
                        emp.getDepartment(),
                        emp.getSalary(),
                        emp.getPerformanceRating(),
                        emp.getYearsOfExperience(),
                        emp.getIsActive()));

        System.out.println(bottomBorder);
        System.out.printf("%nFound %d employee(s)%n%n", employees.size());
    }
}
