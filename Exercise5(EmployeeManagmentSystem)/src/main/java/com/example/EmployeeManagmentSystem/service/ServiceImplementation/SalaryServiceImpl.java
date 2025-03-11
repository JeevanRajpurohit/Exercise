package com.example.EmployeeManagmentSystem.service.ServiceImplementation;

import com.example.EmployeeManagmentSystem.dto.SalaryDto;
import com.example.EmployeeManagmentSystem.entity.Employee;
import com.example.EmployeeManagmentSystem.entity.Salary;
import com.example.EmployeeManagmentSystem.exception.InvalidSalaryException;
import com.example.EmployeeManagmentSystem.exception.SalaryNotFoundException;
import com.example.EmployeeManagmentSystem.repositories.EmployeeRepository;
import com.example.EmployeeManagmentSystem.repositories.SalaryRepository;
import com.example.EmployeeManagmentSystem.service.SalaryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaryServiceImpl implements SalaryService {
    private final SalaryRepository salaryRepository;

    private final EmployeeRepository employeeRepository;


    public SalaryServiceImpl(SalaryRepository salaryRepository, EmployeeRepository employeeRepository) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public Salary createOrUpdateSalary(SalaryDto salaryDto, Employee employee) {
        if (salaryDto == null) {
            throw new InvalidSalaryException("Salary data cannot be null");
        }

        Salary salary = salaryRepository.findFirstByEmployee_EmployeeIdOrderByIdDesc(employee.getEmployeeId())
                .orElse(new Salary());

        salary.setBaseSalary(salaryDto.getBaseSalary());
        salary.setBonus(salaryDto.getBonus());
        salary.setAllowance(salaryDto.getAllowance());
        salary.setLastIncrement(salaryDto.getLastIncrement());
        salary.setEmployee(employee);

        return salaryRepository.save(salary);
    }
    @Transactional
    public SalaryDto createSalary(SalaryDto salaryDto) {
        Salary salary = new Salary();
        salary.setBaseSalary(salaryDto.getBaseSalary());
        salary.setBonus(salaryDto.getBonus());
        salary.setAllowance(salaryDto.getAllowance());
        salary.setLastIncrement(salaryDto.getLastIncrement());

        Salary savedSalary = salaryRepository.save(salary);
        return mapToDTO(savedSalary);
    }

    public SalaryDto getSalaryById(Long id) {
        Optional<Salary> salaryOptional = salaryRepository.findById(id);
        return salaryOptional.map(this::mapToDTO)
                .orElseThrow(() -> new SalaryNotFoundException(id));
    }


    public List<SalaryDto> getAllSalaries() {
        return salaryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SalaryDto updateSalary(Long id, SalaryDto salaryDto) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new SalaryNotFoundException(id));
        salary.setBaseSalary(salaryDto.getBaseSalary());
        salary.setBonus(salaryDto.getBonus());
        salary.setAllowance(salaryDto.getAllowance());
        salary.setLastIncrement(salaryDto.getLastIncrement());
        Salary updatedSalary = salaryRepository.save(salary);
        return mapToDTO(updatedSalary);
    }

    public void deleteSalary(Long id) {
        if (!salaryRepository.existsById(id)) {
            throw new SalaryNotFoundException(id);
        }
        salaryRepository.deleteById(id);
    }

    private SalaryDto mapToDTO(Salary salary) {
        SalaryDto salaryDto = new SalaryDto();
        salaryDto.setId(salary.getId());
        salaryDto.setBaseSalary(salary.getBaseSalary());
        salaryDto.setBonus(salary.getBonus());
        salaryDto.setAllowance(salary.getAllowance());
        salaryDto.setLastIncrement(salary.getLastIncrement());
        if (salary.getEmployee() != null) {
            salaryDto.setEmployeeId(salary.getEmployee().getEmployeeId());
        } else {
            salaryDto.setEmployeeId(null);
        }
        return salaryDto;
    }
}
