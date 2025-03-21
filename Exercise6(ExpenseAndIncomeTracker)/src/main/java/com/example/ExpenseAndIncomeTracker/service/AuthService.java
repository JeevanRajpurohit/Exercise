package com.example.ExpenseAndIncomeTracker.service;

import com.example.ExpenseAndIncomeTracker.dto.LoginDto;
import com.example.ExpenseAndIncomeTracker.dto.UserRegistrationDto;
import com.example.ExpenseAndIncomeTracker.model.User;

public interface AuthService {

    User registerUser(UserRegistrationDto registrationDTO);

    User authenticateUser(LoginDto loginDTO);

}
