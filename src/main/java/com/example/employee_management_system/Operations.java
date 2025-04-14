package com.example.employee_management_system;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Operations<T> {
    private Map<T, Employee<T>> employeeDB = new HashMap<>();

//    Create a new Employee
    public void addEmployee(Employee<T> employee){
        employeeDB.put(employee.getEmployeeID(), employee);
    }

//    Delete a new Employee
    public void removeEmployee(Employee<T> employee){
        employeeDB.remove(employee.getEmployeeID(), employee);
    }

//    Get all employees in the systems
    public Collection<Employee<T>> getAllEmployees(){
        return employeeDB.values();
    }

//    Update Employee details in the System
public boolean updateEmployeeField(T employeeId, String field, Object newValue) {
    Employee<T> employee = employeeDB.get(employeeId);
    if (employee == null) {
        return false;
    }

    switch (field.toLowerCase()) {
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
            return false;
    }

    return true;
}


}
