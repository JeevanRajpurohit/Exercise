package com.example.EmployeeManagmentSystem.exception;


public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(Long id) {
        super("Department not found with id: " + id);
    }
}