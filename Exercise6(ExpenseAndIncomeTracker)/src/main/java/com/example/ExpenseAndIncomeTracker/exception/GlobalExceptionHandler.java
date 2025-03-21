package com.example.ExpenseAndIncomeTracker.exception;

import com.example.ExpenseAndIncomeTracker.util.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseHandler> handleEntityNotFoundException(EntityNotFoundException ex) {
        LOG.error("Entity Not Found: ", ex);
        ResponseHandler response = new ResponseHandler(
                null,
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseHandler> handleValidationExceptions(MethodArgumentNotValidException ex) {
        LOG.error("Validation Error: ", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ResponseHandler response = new ResponseHandler(
                errors,
                messageSource.getMessage("validation.error", null, LocaleContextHolder.getLocale()),
                HttpStatus.BAD_REQUEST.value(),
                false,
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseHandler> handleRuntimeException(RuntimeException ex) {
        LOG.error("Runtime Exception: ", ex);
        ResponseHandler response = new ResponseHandler(
                null,
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                false,
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseHandler> handleAllExceptions(Exception ex) {
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
