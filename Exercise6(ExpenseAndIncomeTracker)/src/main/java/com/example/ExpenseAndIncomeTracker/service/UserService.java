package com.example.ExpenseAndIncomeTracker.service;


import com.example.ExpenseAndIncomeTracker.dto.PasswordUpdateDto;
import com.example.ExpenseAndIncomeTracker.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    void updatePassword(String username, PasswordUpdateDto passwordUpdateDto);

    Optional<User> findByUsername(String username);
}
