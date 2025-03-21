package com.example.ExpenseAndIncomeTracker.service;

import com.example.ExpenseAndIncomeTracker.dto.TransactionDto;
import com.example.ExpenseAndIncomeTracker.model.Transaction;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(TransactionDto transactionDTO, String userId);

    List<Transaction> getAllTransactionsByUserId(String userId);

    List<Transaction> getTransactionsByCategory(String userId, String category);

    List<Transaction> getTransactionsByDateRange(String userId, Date startDate, Date endDate);

    void deleteTransaction(String transactionId, String userId);
}
