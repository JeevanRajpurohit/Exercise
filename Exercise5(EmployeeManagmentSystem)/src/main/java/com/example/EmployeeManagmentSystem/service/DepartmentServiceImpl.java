package com.example.EmployeeManagmentSystem.service;

import com.example.EmployeeManagmentSystem.dto.DepartmentDto;
import com.example.EmployeeManagmentSystem.entity.Department;
import com.example.EmployeeManagmentSystem.exception.DepartmentNotFoundException;
import com.example.EmployeeManagmentSystem.repositories.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public Department getOrCreateDepartment(DepartmentDto departmentDto) {
        return departmentRepository.findByName(departmentDto.getName())
                .orElseGet(() -> {
                    Department newDepartment = new Department();
                    newDepartment.setName(departmentDto.getName());
                    return departmentRepository.save(newDepartment);
                });
    }


    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setName(departmentDto.getName());
        Department savedDepartment = departmentRepository.save(department);
        return mapToDTO(savedDepartment);
    }

    public DepartmentDto getDepartmentById(Long id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        return departmentOptional.map(this::mapToDTO)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
    }

    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        department.setName(departmentDto.getName());
        Department updatedDepartment = departmentRepository.save(department);
        return mapToDTO(updatedDepartment);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException(id);
        }
        departmentRepository.deleteById(id);
    }

    private DepartmentDto mapToDTO(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentId(department.getDepartmentId());
        departmentDto.setName(department.getName());
        return departmentDto;
    }
}
