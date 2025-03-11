package com.example.EmployeeManagmentSystem.controller;

import com.example.EmployeeManagmentSystem.dto.ProjectDto;
import com.example.EmployeeManagmentSystem.response.ResponseHandler;
import com.example.EmployeeManagmentSystem.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final MessageSource messageSource;

    public ProjectController(ProjectService projectService, MessageSource messageSource) {
        this.projectService = projectService;
        this.messageSource = messageSource;
    }

    @PostMapping("/addSingle")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectDto projectDto) {
        ProjectDto createdProject = projectService.createProject(projectDto);
        ResponseHandler response = new ResponseHandler(
                createdProject,
                messageSource.getMessage("project.create.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.CREATED.value(),
                true,
                "project");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/addMultiple")
    public ResponseEntity<?> createProjects(@Valid @RequestBody List<ProjectDto> projectDtos) {
        List<ProjectDto> createdProjects = projectService.createProjects(projectDtos);
        ResponseHandler response = new ResponseHandler(
                createdProjects,
                messageSource.getMessage("projects.create.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.CREATED.value(),
                true,
                "projects");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        ProjectDto project = projectService.getProjectById(id);
        ResponseHandler response = new ResponseHandler(
                project,
                messageSource.getMessage("project.get.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "project");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProjects() {
        List<ProjectDto> projects = projectService.getAllProjects();
        ResponseHandler response = new ResponseHandler(
                projects,
                messageSource.getMessage("project.list.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "projects");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectDto projectDto) {
        ProjectDto updatedProject = projectService.updateProject(id, projectDto);
        ResponseHandler response = new ResponseHandler(
                updatedProject,
                messageSource.getMessage("project.update.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "project");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        ResponseHandler response = new ResponseHandler(
                null,
                messageSource.getMessage("project.delete.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                null);
        return ResponseEntity.ok(response);
    }
}