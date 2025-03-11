
package com.example.EmployeeManagmentSystem.controller;

import com.example.EmployeeManagmentSystem.dto.EmployeeDto;
import com.example.EmployeeManagmentSystem.dto.ProjectDto;
import com.example.EmployeeManagmentSystem.response.ResponseHandler;
import com.example.EmployeeManagmentSystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final MessageSource messageSource;
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService, MessageSource messageSource) {
        this.employeeService = employeeService;
        this.messageSource = messageSource;
    }

    @PostMapping("/addSingle")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDto employeeDTO) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDTO);
        System.out.println(createdEmployee);
        ResponseHandler response = new ResponseHandler(
                createdEmployee,
                messageSource.getMessage("employee.create.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.CREATED.value(),
                true,
                "employee");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/addMultiple")
    public ResponseEntity<?> createEmployees(@Valid @RequestBody List<EmployeeDto> employeeDTOs) {
        List<EmployeeDto> createdEmployees = employeeService.createEmployees(employeeDTOs);
        ResponseHandler response = new ResponseHandler(
                createdEmployees,
                messageSource.getMessage("employee.bulk.create.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.CREATED.value(),
                true,
                "employees");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        EmployeeDto employee = employeeService.getEmployeeById(id);
        System.out.println(employee);
        ResponseHandler response = new ResponseHandler(
                employee,
                messageSource.getMessage("employee.get.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "employee");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        ResponseHandler response = new ResponseHandler(
                employees,
                messageSource.getMessage("employee.list.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "employees");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDto employeeDTO) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        ResponseHandler response = new ResponseHandler(
                updatedEmployee,
                messageSource.getMessage("employee.update.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "employee");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        ResponseHandler response = new ResponseHandler(
                null,
                messageSource.getMessage("employee.delete.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteMultiple")
    public ResponseEntity<?> deleteEmployees(@RequestBody List<Long> employeeIds) {
        employeeService.deleteEmployees(employeeIds);
        ResponseHandler response = new ResponseHandler(
                null,
                messageSource.getMessage("employee.bulk.delete.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                null);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/updateMutilple/salary")
    public ResponseEntity<?> updateSalaries(
            @RequestParam List<Long> employeeIds,
            @RequestParam(required = false) Double percentage,
            @RequestParam(required = false) Double fixedAmount) {
        employeeService.updateSalaries(employeeIds, percentage, fixedAmount);
        ResponseHandler response = new ResponseHandler(
                null,
                messageSource.getMessage("employee.bulk.salary.update.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                null);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{employeeId}/assignProjects")
    public ResponseEntity<?> assignProjectsToEmployee(
            @PathVariable Long employeeId,
            @RequestBody Set<ProjectDto> projectDtos) {
        EmployeeDto updatedEmployee = employeeService.assignProjectsToEmployee(employeeId, projectDtos);
        ResponseHandler response = new ResponseHandler(
                updatedEmployee,
                messageSource.getMessage("employee.projects.assign.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "employee");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/page")
    public ResponseEntity<?> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Page<EmployeeDto> employees = employeeService.getEmployees(page, size);
        ResponseHandler response = new ResponseHandler(
                employees,
                messageSource.getMessage("employee.page.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "employees");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/page/sort")
    public ResponseEntity<?> getEmployeesSorted(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,desc") String[] sort) {
        Page<EmployeeDto> employees = employeeService.getEmployeesSorted(page, size, sort);
        ResponseHandler response = new ResponseHandler(
                employees,
                messageSource.getMessage("employee.page.sort.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "employees");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/projects/more-than-two")
    public List<EmployeeDto> getEmployeesWithMoreThanTwoProjects() {
        return employeeService.getEmployeesWithMoreThanTwoProjects();
    }

    @GetMapping("/salary/greater-than-50000")
    public List<EmployeeDto> getEmployeesWithSalaryGreaterThan50000() {
        return employeeService.getEmployeesWithSalaryGreaterThan50000();
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<EmployeeDto>> getFilteredEmployees() {
        List<EmployeeDto> employees = employeeService.getEmployeesWithSalaryGreaterThan50000AndITDesignationAndMoreThanOneProject();
        return ResponseEntity.ok(employees);
    }
}