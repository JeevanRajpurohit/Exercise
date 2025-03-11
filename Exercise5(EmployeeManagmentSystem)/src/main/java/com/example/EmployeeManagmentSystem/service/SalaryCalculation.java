package com.example.EmployeeManagmentSystem.service;

@FunctionalInterface
public interface SalaryCalculation {
    double calculate(double baseSalary, Double percentage, Double fixedAmount);
}
