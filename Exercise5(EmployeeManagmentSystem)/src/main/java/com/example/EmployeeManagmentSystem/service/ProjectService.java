package com.example.EmployeeManagmentSystem.service;

import com.example.EmployeeManagmentSystem.dto.ProjectDto;
import com.example.EmployeeManagmentSystem.entity.Employee;
import com.example.EmployeeManagmentSystem.entity.Project;

import java.util.List;

public interface ProjectService {
    Project createOrUpdateProject(ProjectDto projectDto, Employee employee);

    ProjectDto createProject(ProjectDto projectDto);

    List<ProjectDto> createProjects(List<ProjectDto> projectDtos);

    ProjectDto getProjectById(Long id);

    List<ProjectDto> getAllProjects();

    ProjectDto updateProject(Long id, ProjectDto projectDto);

    void deleteProject(Long id);
}
