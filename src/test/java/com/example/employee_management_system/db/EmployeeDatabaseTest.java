package com.example.employee_management_system.db;

import com.example.employee_management_system.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class EmployeeDatabaseTest {

    private EmployeeDatabase<String> database;
    private Employee<String> employee;

    @BeforeEach
    void setup(){
        database = new EmployeeDatabase<>();
        employee = new Employee<>("E001", "Benedict", "Backend", 4000.0, 4.5, 3, true);
    }

    @Test
    void addEmployee() {
        database.addEmployee(employee);
        List<Employee<String>> employees = database.getAllEmployees();
        assertEquals(1, employees.size());
        assertEquals("Benedict", employees.getFirst().getName());
        assertNotNull(employees.getFirst());
    }

    @Test
    void removeEmployee() {
        database.addEmployee(employee);
        database.removeEmployee("E001");
        List<Employee<String>> employees = database.getAllEmployees();
        assertTrue(employees.isEmpty());
    }

    @Test
    void searchByDepartment() {
        Employee<String> emp2 = new Employee<>("E001", "Jane Smith", "HR", 65000.0, 4.2, 3, true);
        database.addEmployee(employee);
        database.addEmployee(emp2);

        List<Employee<String>> itEmployees = database.getAllEmployees().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase("HR"))
                .toList();

        assertEquals(1, itEmployees.size());
        assertEquals("Jane Smith", itEmployees.getFirst().getName());
    }
}