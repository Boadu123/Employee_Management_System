package com.example.employee_management_system.models;

import com.example.employee_management_system.exceptions.InvalidDepartmentException;
import com.example.employee_management_system.exceptions.InvalidSalaryException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Employee<T> implements Comparable<Employee<T>> {
    private T employeeID;
    private String name;
    private String department;
    private double salary;
    private double performanceRating;
    private int yearsOfExperience;
    private boolean isActive;

    public Employee(T employeeID, String name, String department, double salary, double performanceRating, int yearsOfExperience, boolean isActive){
        setEmployeeID(employeeID);
        setName(name);
        setDepartment(department);
        setSalary(salary);
        setPerformanceRating(performanceRating);
        setYearsOfExperience(yearsOfExperience);
        setActive(isActive);
    }

    private static Logger logger = Logger.getLogger(Employee.class.getName());

    //    Getters
    public T getEmployeeID() {
        return employeeID;
    }

    public String getName(){
        return name;
    }

    public String getDepartment(){
        return department;
    }

    public double getSalary(){
        return salary;
    }

    public double getPerformanceRating() {
        return performanceRating;
    }

    public int getYearsOfExperience(){
        return yearsOfExperience;
    }

    public boolean getIsActive(){
        return isActive;
    }

    //    Setters
    public void setEmployeeID(T employeeID){
        if(employeeID == null){
            logger.log(Level.WARNING, "Provided Employee ID is empty");
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        this.employeeID = employeeID;
    }

    public void setDepartment(String department) throws InvalidDepartmentException {
        List<String> availableDepartment = List.of("Backend", "Frontend", "HR", "Finance", "Marketing");
        if(department == null || !availableDepartment.contains(department)){
            logger.log(Level.WARNING, "Invalid department entered: ", department);
            throw new InvalidDepartmentException("Not part of the available department");
        }
        this.department = department;
    }

    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            logger.log(Level.WARNING, "Invalid name entered: ", name);
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setSalary(double salary) throws InvalidSalaryException {
        if(salary < 0 ){
            logger.log(Level.WARNING, "Attempted to set salary on a negative value ", salary);
            throw new InvalidSalaryException("Salary cannot be negative");
        }
        this.salary = salary;
    }

    public void setYearsOfExperience(int yearsOfExperience){
        if(yearsOfExperience < 0){
            logger.log(Level.WARNING, "Attempted to put Years of Experience to a negative number: ", yearsOfExperience);
            throw new IllegalArgumentException("Years of experience should be greater than 1");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setPerformanceRating(double performanceRating){
        if(performanceRating < 0.0 || performanceRating > 5.0){
            logger.log(Level.WARNING, "Performance Rating is not within Range");
            throw new IllegalArgumentException("Performance Rating should be between 0.0 and 5.0.");
        }
        this.performanceRating = performanceRating;
    }

    public void setActive(boolean isActive){
        this.isActive = isActive;
    }

    //    arrange Employees in descending order
    @Override
    public int compareTo(Employee<T> other){
        return Integer.compare(other.yearsOfExperience, this.yearsOfExperience);
    }

}


