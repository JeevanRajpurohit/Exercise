package com.example.EmployeeManagmentSystem.service.ServiceImplementation;

import com.example.EmployeeManagmentSystem.dto.DepartmentDto;
import com.example.EmployeeManagmentSystem.dto.EmployeeDto;
import com.example.EmployeeManagmentSystem.dto.ProjectDto;
import com.example.EmployeeManagmentSystem.dto.SalaryDto;
import com.example.EmployeeManagmentSystem.entity.Department;
import com.example.EmployeeManagmentSystem.entity.Employee;
import com.example.EmployeeManagmentSystem.entity.Project;
import com.example.EmployeeManagmentSystem.entity.Salary;
import com.example.EmployeeManagmentSystem.exception.DuplicateEmailException;
import com.example.EmployeeManagmentSystem.exception.EmployeeNotFoundException;
import com.example.EmployeeManagmentSystem.exception.InvalidSalaryException;
import com.example.EmployeeManagmentSystem.repositories.DepartmentRepository;
import com.example.EmployeeManagmentSystem.repositories.EmployeeRepository;
import com.example.EmployeeManagmentSystem.service.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final SalaryService salaryService;
    private final ProjectService projectService;

    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentService departmentService, SalaryService salaryService, ProjectService projectService, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.salaryService = salaryService;
        this.projectService = projectService;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new DuplicateEmailException(employeeDto.getEmail());
        }

        DepartmentDto departmentDto = employeeDto.getDepartment();
        Department department = departmentService.getOrCreateDepartment(departmentDto);

        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDesignation(employeeDto.getDesignation());
        employee.setDepartment(department);

        if (employeeDto.getSalary() != null) {
            Salary salary = salaryService.createOrUpdateSalary(employeeDto.getSalary(), employee);
            employee.setSalary(salary);
        }

        if (employeeDto.getProjects() != null && !employeeDto.getProjects().isEmpty()) {
            Set<Project> projects = employeeDto.getProjects().stream()
                    .map(projectDto -> projectService.createOrUpdateProject(projectDto, employee))
                    .collect(Collectors.toSet());
            employee.setProjects(projects);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToDTO(savedEmployee);
    }

    public List<EmployeeDto> createEmployees(List<EmployeeDto> employeeDtos) {
        return employeeDtos.stream()
                .map(this::createEmployee)
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.map(this::mapToDTO)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setEmail(employeeDto.getEmail());
        existingEmployee.setDesignation(employeeDto.getDesignation());

        DepartmentDto departmentDto = employeeDto.getDepartment();
        Department department = departmentService.getOrCreateDepartment(departmentDto);
        existingEmployee.setDepartment(department);

        if (employeeDto.getSalary() != null) {
            Salary salary = salaryService.createOrUpdateSalary(employeeDto.getSalary(), existingEmployee);
            existingEmployee.setSalary(salary);
        }

        if (employeeDto.getProjects() != null && !employeeDto.getProjects().isEmpty()) {
            Set<Project> projects = employeeDto.getProjects().stream()
                    .map(projectDto -> projectService.createOrUpdateProject(projectDto, existingEmployee))
                    .collect(Collectors.toSet());
            existingEmployee.setProjects(projects);
        }

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return mapToDTO(updatedEmployee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }

    @Transactional
    public void deleteEmployees(List<Long> employeeIds) {
        employeeIds.forEach(id -> {
            if (!employeeRepository.existsById(id)) {
                throw new EmployeeNotFoundException(id);
            }
            employeeRepository.deleteById(id);
        });
    }

    @Transactional
    public void updateSalaries(List<Long> employeeIds, Double percentage, Double fixedAmount) {
        if (percentage == null && fixedAmount == null) {
            throw new IllegalArgumentException("Either percentage or fixedAmount must be provided");
        }

        SalaryCalculation salaryCalculation = new SalaryCalculationImpl();
        List<Employee> employees = employeeRepository.findAllById(employeeIds);

        for (Employee employee : employees) {
            Salary salary = employee.getSalary();
            if (salary == null) {
                throw new InvalidSalaryException("Employee with ID " + employee.getEmployeeId() + " has no salary record");
            }
            salary.setBaseSalary(salaryCalculation.calculate(salary.getBaseSalary(), percentage, fixedAmount));
        }
        employeeRepository.saveAll(employees);
    }

    @Transactional
    public EmployeeDto assignProjectsToEmployee(Long employeeId, Set<ProjectDto> projectDtos) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        Set<Project> projects = projectDtos.stream()
                .map(projectDto -> projectService.createOrUpdateProject(projectDto, employee))
                .collect(Collectors.toSet());

        employee.getProjects().addAll(projects);
        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToDTO(updatedEmployee);
    }


    public Page<EmployeeDto> getEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    public Page<EmployeeDto> getEmployeesSorted(int page, int size, String[] sort) {
        if (sort == null || sort.length < 2) {
            throw new IllegalArgumentException("Invalid sort parameter. Expected format: 'field,direction'");
        }
        Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
        return employeeRepository.findAll(pageable)
                .map(this::mapToDTO);
    }
    public List<EmployeeDto> getEmployeesWithMoreThanTwoProjects() {
        List<Employee> employees = employeeRepository.findEmployeesWithMoreThanTwoProjects();
        return employees.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<EmployeeDto> getEmployeesWithSalaryGreaterThan50000() {
        List<Employee> employees = employeeRepository.findEmployeesWithSalaryGreaterThan50000();
        return employees.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> getEmployeesWithSalaryGreaterThan50000AndITDesignationAndMoreThanOneProject() {
        List<Employee> employees = employeeRepository.findEmployeesWithSalaryGreaterThan50000AndITDesignationAndMoreThanOneProject();
        return employees.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private EmployeeDto mapToDTO(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId(employee.getEmployeeId());
        employeeDto.setName(employee.getName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setDesignation(employee.getDesignation());

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentId(employee.getDepartment().getDepartmentId());
        departmentDto.setName(employee.getDepartment().getName());
        employeeDto.setDepartment(departmentDto);

        if (employee.getSalary() != null) {
            SalaryDto salaryDto = new SalaryDto();
            salaryDto.setId(employee.getSalary().getId());
            salaryDto.setBaseSalary(employee.getSalary().getBaseSalary());
            salaryDto.setBonus(employee.getSalary().getBonus());
            salaryDto.setAllowance(employee.getSalary().getAllowance());
            salaryDto.setLastIncrement(employee.getSalary().getLastIncrement());

            if (employee.getSalary().getEmployee() != null) {
                salaryDto.setEmployeeId(employee.getSalary().getEmployee().getEmployeeId());
            }

            employeeDto.setSalary(salaryDto);
        }

        if (employee.getProjects() != null && !employee.getProjects().isEmpty()) {
            Set<ProjectDto> projectDtos = employee.getProjects().stream()
                    .map(project -> {
                        ProjectDto projectDto = new ProjectDto();
                        projectDto.setId(project.getId());
                        projectDto.setName(project.getName());
                        projectDto.setDescription(project.getDescription());
                        projectDto.setCreatedAt(project.getCreatedAt());
                        projectDto.setUpdatedAt(project.getUpdatedAt());
                        return projectDto;
                    })
                    .collect(Collectors.toSet());
            employeeDto.setProjects(projectDtos);
        }

        return employeeDto;
    }
}
