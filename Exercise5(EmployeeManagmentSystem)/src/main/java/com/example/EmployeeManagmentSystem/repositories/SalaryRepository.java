package com.example.EmployeeManagmentSystem.repositories;

import com.example.EmployeeManagmentSystem.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary,Long> {
    Optional<Salary> findFirstByEmployee_EmployeeIdOrderByIdDesc(Long employeeId);}
