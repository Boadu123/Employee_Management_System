package com.example.employee_management_system.exceptions;

public class InvalidDepartmentException extends RuntimeException {
    public InvalidDepartmentException(String message) {
        super(message);
    }
}
