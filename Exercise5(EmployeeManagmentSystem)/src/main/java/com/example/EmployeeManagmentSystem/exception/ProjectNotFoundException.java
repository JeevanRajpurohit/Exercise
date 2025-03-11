package com.example.EmployeeManagmentSystem.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(Long id){
        super(String.valueOf(id));
    }
}
