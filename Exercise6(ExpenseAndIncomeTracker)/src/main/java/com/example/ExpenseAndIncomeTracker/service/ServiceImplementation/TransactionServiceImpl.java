package com.example.ExpenseAndIncomeTracker.service.ServiceImplementation;

import com.example.ExpenseAndIncomeTracker.dto.TransactionDto;
import com.example.ExpenseAndIncomeTracker.exception.EntityNotFoundException;
import com.example.ExpenseAndIncomeTracker.model.Transaction;
import com.example.ExpenseAndIncomeTracker.repository.TransactionRepository;
import com.example.ExpenseAndIncomeTracker.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Transaction createTransaction(TransactionDto transactionDTO, String userId) {
        logger.info("Creating transaction for user ID: {}", userId);
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
        transaction.setUserId(userId);
        transaction.setCreatedAt(new Date());
        transaction.setModifiedAt(new Date());
        return transactionRepository.save(transaction);
    }


    @Override
    public List<Transaction> getAllTransactionsByUserId(String userId) {
        logger.info("Fetching all transactions for user ID: {}", userId);
        return transactionRepository.findByUserId(userId);
    }

    @Override
    public List<Transaction> getTransactionsByCategory(String userId, String category) {
        logger.info("Fetching transactions for user ID: {} and category: {}", userId, category);
        return transactionRepository.findByUserIdAndCategory(userId, category);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(String userId, Date startDate, Date endDate) {
        logger.info("Fetching transactions for user ID: {} between {} and {}", userId, startDate, endDate);
        return transactionRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }

    @Override
    public void deleteTransaction(String transactionId, String userId) {
        logger.info("Deleting transaction with ID: {} for user ID: {}", transactionId, userId);

        List<Transaction> userTransactions = transactionRepository.findByUserId(userId);
        Optional<Transaction> transactionOptional = userTransactions.stream()
                .filter(transaction -> transaction.getTransactionId().equals(transactionId))
                .findFirst();

        if (transactionOptional.isEmpty()) {
            logger.error("Transaction not found with ID: {} for user ID: {}", transactionId, userId);
            throw new EntityNotFoundException("Transaction not found with ID: " + transactionId);
        }
        Transaction transaction = transactionOptional.get();
        transactionRepository.delete(transaction);
        logger.info("Transaction deleted successfully: {}", transactionId);
    }
}
