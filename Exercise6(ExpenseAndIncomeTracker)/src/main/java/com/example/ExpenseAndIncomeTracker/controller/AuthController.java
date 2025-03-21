package com.example.ExpenseAndIncomeTracker.controller;

import com.example.ExpenseAndIncomeTracker.config.JwtTokenProvider;
import com.example.ExpenseAndIncomeTracker.dto.LoginDto;
import com.example.ExpenseAndIncomeTracker.dto.UserRegistrationDto;
import com.example.ExpenseAndIncomeTracker.model.User;
import com.example.ExpenseAndIncomeTracker.service.AuthService;
import com.example.ExpenseAndIncomeTracker.util.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDto registrationDTO) {
        User user = authService.registerUser(registrationDTO);

        ResponseHandler response = new ResponseHandler(
                user,
                messageSource.getMessage("user.register.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.CREATED.value(),
                true,
                "user"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDTO) {
        User user = authService.authenticateUser(loginDTO);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                Collections.emptyList()
        );
        String token = jwtTokenProvider.generateToken(authentication);


        ResponseHandler response = new ResponseHandler(
                user,
                messageSource.getMessage("user.login.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "user"
        );
        response.setToken(token);
        return ResponseEntity.ok(response);
    }
}