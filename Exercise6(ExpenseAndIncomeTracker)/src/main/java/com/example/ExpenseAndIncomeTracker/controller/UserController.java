package com.example.ExpenseAndIncomeTracker.controller;

import com.example.ExpenseAndIncomeTracker.dto.PasswordUpdateDto;
import com.example.ExpenseAndIncomeTracker.exception.EntityNotFoundException;
import com.example.ExpenseAndIncomeTracker.model.User;
import com.example.ExpenseAndIncomeTracker.service.UserService;
import com.example.ExpenseAndIncomeTracker.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        ResponseHandler response = new ResponseHandler(
                user,
                messageSource.getMessage("user.get.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "user"
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
        ResponseHandler response = new ResponseHandler(
                null,
                messageSource.getMessage("user.delete.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "user"
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.findAll();
        ResponseHandler response = new ResponseHandler(
                users,
                messageSource.getMessage("user.list.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "users"
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{username}/password")
    public ResponseEntity<?> updatePassword(@PathVariable String username, @RequestBody PasswordUpdateDto passwordUpdateDto) {
        userService.updatePassword(username, passwordUpdateDto);
        ResponseHandler response = new ResponseHandler(
                null,
                messageSource.getMessage("user.password.update.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "user"
        );
        return ResponseEntity.ok(response);
    }
}
