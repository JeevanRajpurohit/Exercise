package com.example.ExpenseAndIncomeTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordUpdateDto {
    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
