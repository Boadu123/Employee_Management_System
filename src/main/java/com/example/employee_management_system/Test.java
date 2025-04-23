package com.example.employee_management_system;

import com.example.employee_management_system.models.Employee;
import com.example.employee_management_system.db.EmployeeDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args){
        Employee<String> newEmployee = new Employee<>("E1009", "Benedict Boadu-Boateng", "HR", 8500.00, 3.0, 6, true);
        Employee<String> newEmployee1 = new Employee<>("E1003", "Thomson Asiedu", "Backend", 900.00, 4.0, 4, true);
        Employee<String> newEmployee2 = new Employee<>("E1005", "John Badu", "Backend", 230.78, 5.1, 9, true);

        EmployeeDatabase<String> database = new EmployeeDatabase<>();
        database.addEmployee(newEmployee);
        database.addEmployee(newEmployee1);
        database.addEmployee(newEmployee2);

        database.removeEmployee(newEmployee.getEmployeeID());

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Jane Doe");
        updates.put("salary", 75000);
        updates.put("isActive", true);

        database.updateEmployeeFields("E1005", updates);

        List<Employee<String>> allEmployees = database.getAllEmployees();

        List<Employee<String>> searchedData = database.searchByDepartment("Backend");
        System.out.println("DEPARTMENT SEARCH: "+ searchedData);

        for(Employee<String> emp: allEmployees){
            System.out.println(emp.getName() + " " +  emp.getEmployeeID() + " " + emp.getDepartment());
            System.out.println("====================");
        }

        for(Employee<String> emp: searchedData){
            System.out.print(emp.getName() + " " +  emp.getEmployeeID() + " " + emp.getDepartment());
            System.out.println();
        }

        database.displayEmployeesWith(allEmployees);
        database.displayEmployeesWithStreams(allEmployees);

    }

}

