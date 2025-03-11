package com.example.EmployeeManagmentSystem.service;

import com.example.EmployeeManagmentSystem.dto.EmployeeDto;
import com.example.EmployeeManagmentSystem.dto.ProjectDto;
import com.example.EmployeeManagmentSystem.entity.Department;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> createEmployees(List<EmployeeDto> employeeDtos);

    EmployeeDto getEmployeeById(Long id);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);

    void deleteEmployee(Long id);

    void deleteEmployees(List<Long> employeeIds);

    void updateSalaries(List<Long> employeeIds, Double percentage, Double fixedAmount);

    EmployeeDto assignProjectsToEmployee(Long employeeId, Set<ProjectDto> projectDtos);

    Page<EmployeeDto> getEmployees(int page, int size);

    Page<EmployeeDto> getEmployeesSorted(int page, int size, String[] sort);

    List<EmployeeDto> getEmployeesWithMoreThanTwoProjects();

    List<EmployeeDto> getEmployeesWithSalaryGreaterThan50000();

    List<EmployeeDto> getEmployeesWithSalaryGreaterThan50000AndITDesignationAndMoreThanOneProject();
}
