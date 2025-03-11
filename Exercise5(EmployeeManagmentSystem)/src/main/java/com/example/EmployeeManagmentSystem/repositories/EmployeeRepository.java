package com.example.EmployeeManagmentSystem.repositories;

import com.example.EmployeeManagmentSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE SIZE(e.projects) > 2")
    List<Employee> findEmployeesWithMoreThanTwoProjects();

    @Query("SELECT e FROM Employee e WHERE e.salary.baseSalary > 50000")
    List<Employee> findEmployeesWithSalaryGreaterThan50000();

    @Query(value = "SELECT e.* FROM employee e " +
            "JOIN salary s ON e.employee_id = s.employee_id " +
            "JOIN employee_project ep ON e.employee_id = ep.employee_id " +
            "WHERE s.base_salary > 50000 " +
            "AND e.designation = 'Full-Stack Engineer Intern' " +
            "GROUP BY e.employee_id " +
            "HAVING COUNT(ep.project_id) > 1", nativeQuery = true)
    List<Employee> findEmployeesWithSalaryGreaterThan50000AndITDesignationAndMoreThanOneProject();

}
