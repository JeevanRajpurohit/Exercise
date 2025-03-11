package com.example.EmployeeManagmentSystem.controller;

import com.example.EmployeeManagmentSystem.dto.DepartmentDto;
import com.example.EmployeeManagmentSystem.response.ResponseHandler;
import com.example.EmployeeManagmentSystem.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final MessageSource messageSource;

    public DepartmentController(DepartmentService departmentService, MessageSource messageSource) {
        this.departmentService = departmentService;
        this.messageSource = messageSource;
    }

    @PostMapping("/addSingle")
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        DepartmentDto createdDepartment = departmentService.createDepartment(departmentDto);
        ResponseHandler response = new ResponseHandler(
                createdDepartment,
                messageSource.getMessage("department.create.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.CREATED.value(),
                true,
                "department");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        DepartmentDto department = departmentService.getDepartmentById(id);
        ResponseHandler response = new ResponseHandler(
                department,
                messageSource.getMessage("department.get.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "department");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllDepartments() {
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        ResponseHandler response = new ResponseHandler(
                departments,
                messageSource.getMessage("department.list.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "departments");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentDto departmentDto) {
        DepartmentDto updatedDepartment = departmentService.updateDepartment(id, departmentDto);
        ResponseHandler response = new ResponseHandler(
                updatedDepartment,
                messageSource.getMessage("department.update.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "department");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        ResponseHandler response = new ResponseHandler(
                null,
                messageSource.getMessage("department.delete.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                null);
        return ResponseEntity.ok(response);
    }
}