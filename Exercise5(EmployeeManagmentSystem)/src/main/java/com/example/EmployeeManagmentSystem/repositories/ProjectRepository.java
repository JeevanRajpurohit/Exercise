package com.example.EmployeeManagmentSystem.repositories;

import com.example.EmployeeManagmentSystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
