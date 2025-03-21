package com.example.ExpenseAndIncomeTracker.controller;

import com.example.ExpenseAndIncomeTracker.dto.TransactionDto;
import com.example.ExpenseAndIncomeTracker.dto.TransactionFilterDto;
import com.example.ExpenseAndIncomeTracker.model.Transaction;
import com.example.ExpenseAndIncomeTracker.service.TransactionService;
import com.example.ExpenseAndIncomeTracker.util.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/addSingleTransaction")
    public ResponseEntity<?> addTransaction(@Valid @RequestBody TransactionDto transactionDTO, Principal principal) {
        Transaction transaction = transactionService.createTransaction(transactionDTO, principal.getName());
        ResponseHandler response = new ResponseHandler(
                transaction,
                messageSource.getMessage("transaction.add.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.CREATED.value(),
                true,
                "transaction"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PostMapping("/filter")
    public ResponseEntity<?> getTransactions(
            @RequestBody TransactionFilterDto filterDto,
            Principal principal) {

        List<Transaction> transactions;

        if (filterDto.getCategory() != null) {
            transactions = transactionService.getTransactionsByCategory(principal.getName(), filterDto.getCategory());
        } else if (filterDto.getStartDate() != null && filterDto.getEndDate() != null) {
            transactions = transactionService.getTransactionsByDateRange(principal.getName(), filterDto.getStartDate(), filterDto.getEndDate());
        } else {
            transactions = transactionService.getAllTransactionsByUserId(principal.getName());
        }

        ResponseHandler response = new ResponseHandler(
                transactions,
                messageSource.getMessage("transaction.list.success", null, LocaleContextHolder.getLocale()),
                HttpStatus.OK.value(),
                true,
                "transactions"
        );

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String id, Principal principal) {
        try {
            transactionService.deleteTransaction(id, principal.getName());
            ResponseHandler response = new ResponseHandler(
                    null,
                    messageSource.getMessage("transaction.delete.success", null, LocaleContextHolder.getLocale()),
                    HttpStatus.OK.value(),
                    true,
                    "transaction"
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            ResponseHandler response = new ResponseHandler(
                    null,
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND.value(),
                    false,
                    "transaction"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}