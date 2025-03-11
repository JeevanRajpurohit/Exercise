package com.example.EmployeeManagmentSystem.controller;

import com.example.EmployeeManagmentSystem.dto.SalaryDto;
import com.example.EmployeeManagmentSystem.response.ResponseHandler;
import com.example.EmployeeManagmentSystem.service.SalaryService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    private final SalaryService salaryService;
    private final MessageSource messageSource;

    public SalaryController(SalaryService salaryService, MessageSource messageSource) {
        this.salaryService = salaryService;
        this.messageSource = messageSource;
    }

    @PostMapping("/addSingle")
    public ResponseEntity<?> createSalary(@Valid @RequestBody SalaryDto salaryDto) {
        SalaryDto createdSalary = salaryService.createSalary(salaryDto);
        ResponseHandler response = new ResponseHandler(
                createdSalary,
                messageSource.getMessage("salary.create.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.CREATED.value(),
                true,
                "salary");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalaryById(@PathVariable Long id) {
        SalaryDto salary = salaryService.getSalaryById(id);
        ResponseHandler response = new ResponseHandler(
                salary,
                messageSource.getMessage("salary.get.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "salary");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllSalaries() {
        List<SalaryDto> salaries = salaryService.getAllSalaries();
        ResponseHandler response = new ResponseHandler(
                salaries,
                messageSource.getMessage("salary.list.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "salaries");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSalary(@PathVariable Long id, @Valid @RequestBody SalaryDto salaryDto) {
        SalaryDto updatedSalary = salaryService.updateSalary(id, salaryDto);
        ResponseHandler response = new ResponseHandler(
                updatedSalary,
                messageSource.getMessage("salary.update.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "salary");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalary(@PathVariable Long id) {
        salaryService.deleteSalary(id);
        ResponseHandler response = new ResponseHandler(
                null,
                messageSource.getMessage("salary.delete.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                null);
        return ResponseEntity.ok(response);
    }
}