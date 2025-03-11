package com.example.EmployeeManagmentSystem.exception;

import com.example.EmployeeManagmentSystem.response.ResponseHandler;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        LOG.error("Validation error: {}", errors);

        ResponseHandler response = new ResponseHandler(
                errors,
                messageSource.getMessage("validation.error", null, LocaleContextHolder.getLocale()),
                HttpStatus.BAD_REQUEST.value(),
                false,
                "errors"
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        LOG.error("Validation error: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        ResponseHandler response = new ResponseHandler(
                errors,
                messageSource.getMessage("validation.error", null, LocaleContextHolder.getLocale()),
                HttpStatus.BAD_REQUEST.value(),
                false,
                "errors"
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<?> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        LOG.error("Employee not found: {}", ex.getMessage());

        ResponseHandler response = new ResponseHandler(
                null,
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<?> handleDepartmentNotFoundException(DepartmentNotFoundException ex) {
        LOG.error("Department not found: {}", ex.getMessage());

        ResponseHandler response = new ResponseHandler(
                null,
               "Jeevan department not found",
                HttpStatus.NOT_FOUND.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<?> handleDuplicateEmailException(DuplicateEmailException ex) {
        LOG.error("Duplicate email: {}", ex.getMessage());

        ResponseHandler response = new ResponseHandler(
                null,
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidSalaryException.class)
    public ResponseEntity<?> handleInvalidSalaryException(InvalidSalaryException ex) {
        LOG.error("Invalid salary: {}", ex.getMessage());

        ResponseHandler response = new ResponseHandler(
                null,
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(SalaryNotFoundException.class)
    public ResponseEntity<?> handleSalaryNotFoundException(SalaryNotFoundException ex) {
        LOG.error("Salary not found: {}", ex.getMessage());

        ResponseHandler response = new ResponseHandler(
                null,
                "Salary not found with ID: " + ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<?> handleProjectNotFoundException(ProjectNotFoundException ex) {
        LOG.error("Project not found: {}", ex.getMessage());

        ResponseHandler response = new ResponseHandler(
                null,
                "Project not found with ID: " + ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        LOG.error("Unsupported HTTP method: {}", ex.getMessage());

        ResponseHandler response = new ResponseHandler(
                null,
                "Request method '" + ex.getMethod() + "' is not supported for this endpoint.",
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        LOG.error("Illegal argument: {}", ex.getMessage());

        ResponseHandler response = new ResponseHandler(
                null,
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex) {
        LOG.error("Internal Server Error: ", ex);

        ResponseHandler response = new ResponseHandler(
                null,
                messageSource.getMessage("internal.server.error", null, LocaleContextHolder.getLocale()),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}