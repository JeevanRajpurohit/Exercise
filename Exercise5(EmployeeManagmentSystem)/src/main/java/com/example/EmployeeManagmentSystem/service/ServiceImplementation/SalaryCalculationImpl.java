package com.example.EmployeeManagmentSystem.service.ServiceImplementation;

import com.example.EmployeeManagmentSystem.service.SalaryCalculation;

public class SalaryCalculationImpl implements SalaryCalculation {
    @Override
    public double calculate(double baseSalary, Double percentage, Double fixedAmount) {
        double updatedSalary = baseSalary;

        if (percentage != null) {
            updatedSalary *= (1 + percentage / 100);
        }
        if (fixedAmount != null) {
            updatedSalary += fixedAmount;
        }

        return updatedSalary;
    }
}

