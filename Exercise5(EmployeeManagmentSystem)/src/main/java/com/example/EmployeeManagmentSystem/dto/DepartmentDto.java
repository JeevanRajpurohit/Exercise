package com.example.EmployeeManagmentSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


public class DepartmentDto {

    private Long departmentId;

    @NotBlank(message = "Department name is required")
    @Size(min = 2, max = 50, message = "Department name must be between 2 and 50 characters")
    private String name;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
