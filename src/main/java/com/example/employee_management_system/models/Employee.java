package com.example.employee_management_system.models;

import java.util.List;

public class Employee<T> implements Comparable<Employee<T>> {
    private T employeeID;
    private String name;
    private String department;
    private double salary;
    private double performanceRating;
    private int yearsOfExperience;
    private boolean isActive;

    public Employee(T employeeID, String name, String department, double salary, double performanceRating, int yearsOfExperience, boolean isActive){
        this.employeeID = employeeID;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceRating = performanceRating;
        this.yearsOfExperience = yearsOfExperience;
        this.isActive = isActive;
    }

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
        this.employeeID = employeeID;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSalary(double salary){
        this.salary = salary;
    }

    public void setYearsOfExperience(int yearsOfExperience){
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setPerformanceRating(double performanceRating){
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

    public void displayEmployeesWith(List<Employee<T>> employees) {
        System.out.printf("%-10s %-15s %-15s %-10s %-20s %-20s %-10s%n",
                "ID", "Name", "Department", "Salary", "Performance Rating", "Experience (Years)", "Active");

        for (Employee<T> emp : employees) {
            System.out.printf("%-10s %-15s %-15s %-10.2f %-20.1f %-20d %-10b%n",
                    emp.getEmployeeID(), emp.getName(), emp.getDepartment(), emp.getSalary(),
                    emp.getPerformanceRating(), emp.getYearsOfExperience(), emp.getIsActive());
        }
    }

    public void displayEmployeesWithStreams(List<Employee<T>> employees) {
        System.out.printf("%-10s %-15s %-15s %-10s %-20s %-20s %-10s%n",
                "ID", "Name", "Department", "Salary", "Performance Rating", "Experience (Years)", "Active");

        employees
                .forEach(emp -> System.out.printf("%-10s %-15s %-15s %-10.2f %-20.1f %-20d %-10b%n",
                        emp.getEmployeeID(), emp.getName(), emp.getDepartment(), emp.getSalary(),
                        emp.getPerformanceRating(), emp.getYearsOfExperience(), emp.getIsActive()));
    }
}
