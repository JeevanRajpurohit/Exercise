package com.example.EmployeeManagmentSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0, message = "Base salary cannot be negative")
    @NotNull(message = "Base salary is required")
    private Double baseSalary;

    @Min(value = 0, message = "Base salary cannot be negative")
    @NotNull(message = "Bonus is required")
    private Double bonus;

    @Min(value = 0, message = "Allowance cannot be negative")
    private Double allowance;

    @PastOrPresent(message = "Last increment date cannot be in the future")
    private LocalDate lastIncrement;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


}

