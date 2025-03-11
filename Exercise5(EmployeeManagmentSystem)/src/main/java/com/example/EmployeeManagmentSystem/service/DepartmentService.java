package com.example.EmployeeManagmentSystem.service;

import com.example.EmployeeManagmentSystem.dto.DepartmentDto;
import com.example.EmployeeManagmentSystem.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department getOrCreateDepartment(DepartmentDto departmentDto);

    DepartmentDto createDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentById(Long id);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto);

    void deleteDepartment(Long id);
}
