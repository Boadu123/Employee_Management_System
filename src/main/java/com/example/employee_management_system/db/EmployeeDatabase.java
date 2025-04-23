package com.example.employee_management_system.db;

import com.example.employee_management_system.exceptions.InvalidDepartmentException;
import com.example.employee_management_system.models.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EmployeeDatabase<T> {

    private static final Logger logger = Logger.getLogger(EmployeeDatabase.class.getName());


    private Map<T, Employee<T>> employeeDB = new HashMap<>();

//    Create a new Employee
    public void addEmployee(Employee<T> employee){
        try {
            employeeDB.put(employee.getEmployeeID(), employee);
            logger.info("Employee added successfully: " + employee.getEmployeeID());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding employee: " + e.getMessage(), e);
        } finally {
            logger.info("addEmployee operation completed.");
        }
    }

//    Delete a new Employee
    public void removeEmployee(T employeeID){
        try {
            employeeDB.remove(employeeID);
            logger.info("Employee removed: " + employeeID);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error removing employee: " + e.getMessage(), e);
        } finally {
            logger.info("removeEmployee operation completed.");
        }
    }

//    Get all employees in the systems
    public List<Employee<T>> getAllEmployees(){
        try {
            return employeeDB.values().stream().toList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving employees: " + e.getMessage(), e);
            return List.of();
        } finally {
            logger.info("getAllEmployees operation completed.");
        }
    }

//    Update Employee details in the System
public boolean updateEmployeeFields(T employeeId, Map<String, Object> updates) {
    try {
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
                    break;
            }
        }
        return true;
    } catch (Exception e) {
        logger.log(Level.SEVERE, "Error updating employee: " + e.getMessage(), e);
        return false;
    } finally {
        logger.info("updateEmployeeFields operation completed.");
    }
}

    // Search by Department
    public List<Employee<T>> searchByDepartment(String department) throws InvalidDepartmentException {
        List<String> availableDepartments = List.of("Backend", "Frontend", "HR", "Finance", "Marketing");

        if (department == null || !availableDepartments.contains(department)) {
            logger.log(Level.WARNING, "Invalid department entered for search: " + department);
            throw new InvalidDepartmentException("The department '" + department + "' is not recognized.");
        }

        try {
            return employeeDB.values().stream()
                    .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error searching by department: " + e.getMessage(), e);
            return List.of();
        } finally {
            logger.info("searchByDepartment operation completed.");
        }
    }


    // Search by Name
    public List<Employee<T>> searchByName(String searchTerm) {
        try {
            List<Employee<T>> results = employeeDB.values().stream()
                    .filter(emp -> emp.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());

            if (results.isEmpty()) {
                logger.log(Level.WARNING, "No employees found with name containing: " + searchTerm);
                throw new IllegalArgumentException("No employees found matching the name: " + searchTerm);
            }

            return results;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error searching by name: " + e.getMessage(), e);
            throw e; // Rethrow so caller knows something went wrong
        } finally {
            logger.info("searchByName operation completed.");
        }
    }


    //  Get employees with performance above minimum rating
    public List<Employee<T>> filterByPerformance(double minRating) {
        try {
            if (minRating < 0.0 || minRating > 5.0) {
                logger.log(Level.WARNING, "Invalid performance rating filter value: " + minRating);
                throw new IllegalArgumentException("Performance rating filter must be between 0.0 and 5.0");
            }

            List<Employee<T>> results = employeeDB.values().stream()
                    .filter(emp -> emp.getPerformanceRating() >= minRating)
                    .collect(Collectors.toList());

            if (results.isEmpty()) {
                logger.log(Level.WARNING, "No employees found with performance rating >= " + minRating);
                throw new IllegalArgumentException("No employees found matching the performance criteria.");
            }

            return results;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error filtering by performance: " + e.getMessage(), e);
            throw e;
        } finally {
            logger.info("filterByPerformance operation completed.");
        }
    }

    //   Employees within a specific Salary Range
    public List<Employee<T>> filterBySalaryRange(double min, double max) {
        try {
            if (min < 0 || max < 0) {
                logger.log(Level.WARNING, "Invalid salary range: min or max is negative. Min: " + min + ", Max: " + max);
                throw new IllegalArgumentException("Salary values cannot be negative.");
            }

            if (min > max) {
                logger.log(Level.WARNING, "Invalid salary range: min is greater than max. Min: " + min + ", Max: " + max);
                throw new IllegalArgumentException("Minimum salary cannot be greater than maximum salary.");
            }

            List<Employee<T>> results = employeeDB.values().stream()
                    .filter(emp -> emp.getSalary() >= min && emp.getSalary() <= max)
                    .collect(Collectors.toList());

            if (results.isEmpty()) {
                logger.log(Level.WARNING, "No employees found in salary range: Min: " + min + ", Max: " + max);
                throw new IllegalArgumentException("No employees found matching the salary criteria.");
            }

            return results;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error filtering by salary range: " + e.getMessage(), e);
            throw e;
        } finally {
            logger.info("filterBySalaryRange operation completed.");
        }
    }


    public void displayEmployeesWith(List<Employee<T>> employees) {
        try {
            if (employees.isEmpty()) {
                System.out.println("\nNo employees found.\n");
                return;
            }

            String headerFormat = "║ %-10s ║ %-20s ║ %-20s ║ %-10s ║ %-15s ║ %-15s ║ %-8s ║%n";
            String border = "╠════════════╬══════════════════════╬══════════════════════╬════════════╬═════════════════╬═════════════════╬══════════╣";
            String topBorder = "╔════════════╦══════════════════════╦══════════════════════╦════════════╦═════════════════╦═════════════════╦══════════╗";
            String bottomBorder = "╚════════════╩══════════════════════╩══════════════════════╩════════════╩═════════════════╩═════════════════╩══════════╝";

            System.out.println(topBorder);
            System.out.printf(headerFormat, "ID", "Name", "Department", "Salary", "Performance", "Experience", "Active");
            System.out.println(border);

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
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error displaying employees: " + e.getMessage(), e);
        } finally {
            logger.info("displayEmployeesWith operation completed.");
        }
    }

    public void displayEmployeesWithStreams(List<Employee<T>> employees) {
        try {
            if (employees.isEmpty()) {
                System.out.println("\nNo employees found.\n");
                return;
            }

            String headerFormat = "║ %-10s ║ %-20s ║ %-20s ║ %-10s ║ %-15s ║ %-15s ║ %-8s ║%n";
            String border = "╠════════════╬══════════════════════╬══════════════════════╬════════════╬═════════════════╬═════════════════╬══════════╣";
            String topBorder = "╔════════════╦══════════════════════╦══════════════════════╦════════════╦═════════════════╦═════════════════╦══════════╗";
            String bottomBorder = "╚════════════╩══════════════════════╩══════════════════════╩════════════╩═════════════════╩═════════════════╩══════════╝";

            System.out.println(topBorder);
            System.out.printf(headerFormat, "ID", "Name", "Department", "Salary", "Performance", "Experience", "Active");
            System.out.println(border);

            String rowFormat = "║ %-10s ║ %-20s ║ %-20s ║ $%,9.2f ║ %-15.1f ║ %-15d ║ %-8b ║%n";
            employees.stream().forEach(emp -> System.out.printf(rowFormat,
                    emp.getEmployeeID(),
                    emp.getName(),
                    emp.getDepartment(),
                    emp.getSalary(),
                    emp.getPerformanceRating(),
                    emp.getYearsOfExperience(),
                    emp.getIsActive()));

            System.out.println(bottomBorder);
            System.out.printf("%nFound %d employee(s)%n%n", employees.size());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error displaying employees with streams: " + e.getMessage(), e);
        } finally {
            logger.info("displayEmployeesWithStreams operation completed.");
        }
    }
}
