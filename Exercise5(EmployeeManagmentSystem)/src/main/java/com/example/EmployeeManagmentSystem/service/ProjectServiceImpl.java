package com.example.EmployeeManagmentSystem.service;

import com.example.EmployeeManagmentSystem.dto.ProjectDto;
import com.example.EmployeeManagmentSystem.entity.Employee;
import com.example.EmployeeManagmentSystem.entity.Project;
import com.example.EmployeeManagmentSystem.exception.ProjectNotFoundException;
import com.example.EmployeeManagmentSystem.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Project createOrUpdateProject(ProjectDto projectDto, Employee employee) {
        Project project;
        if (projectDto.getId() != null) {
            project = projectRepository.findById(projectDto.getId())
                    .orElseThrow(() -> new ProjectNotFoundException(projectDto.getId()));
        } else {
            project = new Project();
        }

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());

        if (project.getEmployees() == null) {
            project.setEmployees(new HashSet<>());
        }
        project.getEmployees().add(employee);

        return projectRepository.save(project);
    }

    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        Project savedProject = projectRepository.save(project);
        return mapToDTO(savedProject);
    }
    public List<ProjectDto> createProjects(List<ProjectDto> projectDtos) {
        List<Project> projects = projectDtos.stream()
                .map(dto -> {
                    Project project = new Project();
                    project.setName(dto.getName());
                    project.setDescription(dto.getDescription());
                    return project;
                })
                .collect(Collectors.toList());
        List<Project> savedProjects = projectRepository.saveAll(projects);
        return savedProjects.stream()
                .map(project -> {
                    ProjectDto dto = new ProjectDto();
                    dto.setId(project.getId());
                    dto.setName(project.getName());
                    dto.setDescription(project.getDescription());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ProjectDto getProjectById(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        return projectOptional.map(this::mapToDTO)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProjectDto updateProject(Long id, ProjectDto projectDto) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        existingProject.setName(projectDto.getName());
        existingProject.setDescription(projectDto.getDescription());
        Project updatedProject = projectRepository.save(existingProject);
        return mapToDTO(updatedProject);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException(id);
        }
        projectRepository.deleteById(id);
    }

    private ProjectDto mapToDTO(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());

        projectDto.setCreatedAt(project.getCreatedAt());
        projectDto.setUpdatedAt(project.getUpdatedAt());
        return projectDto;
    }
}
