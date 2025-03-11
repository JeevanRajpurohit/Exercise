package com.example.EmployeeManagmentSystem.service;

import com.example.EmployeeManagmentSystem.dto.SalaryDto;
import com.example.EmployeeManagmentSystem.entity.Employee;
import com.example.EmployeeManagmentSystem.entity.Salary;

import java.util.List;

public interface SalaryService {
    Salary createOrUpdateSalary(SalaryDto salaryDto, Employee employee);

    SalaryDto createSalary(SalaryDto salaryDto);

    SalaryDto getSalaryById(Long id);

    List<SalaryDto> getAllSalaries();

    SalaryDto updateSalary(Long id, SalaryDto salaryDto);

    void deleteSalary(Long id);
}
