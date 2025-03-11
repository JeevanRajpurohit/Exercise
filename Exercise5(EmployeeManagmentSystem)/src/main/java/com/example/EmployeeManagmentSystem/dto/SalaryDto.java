package com.example.EmployeeManagmentSystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto {

    private Long id;

    @NotNull(message = "Base salary is required")
    @Min(value = 0, message = "Base salary cannot be negative")
    private Double baseSalary;

    @NotNull(message = "Bonus is required")
    @Min(value = 0, message = "Bonus cannot be negative")
    private Double bonus;

    @NotNull(message = "Allowance is required")
    @Min(value = 0, message = "Allowance cannot be negative")
    private Double allowance;

    private LocalDate lastIncrement;

    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Double getAllowance() {
        return allowance;
    }

    public void setAllowance(Double allowance) {
        this.allowance = allowance;
    }

    public LocalDate getLastIncrement() {
        return lastIncrement;
    }

    public void setLastIncrement(LocalDate lastIncrement) {
        this.lastIncrement = lastIncrement;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
