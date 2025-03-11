package com.example.EmployeeManagmentSystem.exception;

public class SalaryNotFoundException extends RuntimeException{
    public SalaryNotFoundException(Long id){
        super(String.valueOf(id));
    }
}
