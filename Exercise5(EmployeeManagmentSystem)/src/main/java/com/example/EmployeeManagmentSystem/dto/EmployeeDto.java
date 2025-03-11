package com.example.EmployeeManagmentSystem.dto;

import jakarta.validation.constraints.*;

import java.util.Set;

public class EmployeeDto {

    private Long employeeId;

    @NotBlank(message = "Employee name is required")
    @Size(min = 2, max = 35, message = "Name must be between 2 and 35 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Designation is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Designation must contain only letters and spaces")
    private String designation;

    @NotNull(message = "Department is required")
    private DepartmentDto department;

    @NotNull(message = "Salary details are required")
    private SalaryDto salary;

    @NotEmpty(message = "At least one project is required")
    private Set<ProjectDto> projects;


    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public DepartmentDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDto department) {
        this.department = department;
    }

    public SalaryDto getSalary() {
        return salary;
    }

    public void setSalary(SalaryDto salary) {
        this.salary = salary;
    }

    public Set<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectDto> projects) {
        this.projects = projects;
    }
}
