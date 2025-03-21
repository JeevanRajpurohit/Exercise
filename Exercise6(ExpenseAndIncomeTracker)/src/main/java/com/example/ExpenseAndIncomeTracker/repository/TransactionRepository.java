package com.example.ExpenseAndIncomeTracker.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.ExpenseAndIncomeTracker.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class TransactionRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Transaction save(Transaction transaction) {
        transaction.setCreatedAtSortKey(transaction.getCreatedAt().toString());
        transaction.setDateSortKey(transaction.getDateSortKey());
        dynamoDBMapper.save(transaction);
        return transaction;
    }

    public List<Transaction> findByUserId(String userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":userId", new AttributeValue().withS(userId));

        DynamoDBQueryExpression<Transaction> queryExpression = new DynamoDBQueryExpression<Transaction>()
                .withIndexName("DateIndex")
                .withKeyConditionExpression("userId = :userId")
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false);

        return dynamoDBMapper.query(Transaction.class, queryExpression);
    }


    public List<Transaction> findByUserIdAndCategory(String userId, String category) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":userId", new AttributeValue().withS(userId));
        eav.put(":category", new AttributeValue().withS(category));

        DynamoDBQueryExpression<Transaction> queryExpression = new DynamoDBQueryExpression<Transaction>()
                .withIndexName("CategoryIndex")
                .withKeyConditionExpression("category = :category")
                .withFilterExpression("userId = :userId")
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false)
                .withScanIndexForward(true);

        return dynamoDBMapper.query(Transaction.class, queryExpression);
    }


    public List<Transaction> findByUserIdAndDateBetween(String userId, Date startDate, Date endDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":userId", new AttributeValue().withS(userId));
        eav.put(":startDate", new AttributeValue().withS(outputFormat.format(startDate)));
        eav.put(":endDate", new AttributeValue().withS(outputFormat.format(endDate)));

        DynamoDBQueryExpression<Transaction> queryExpression = new DynamoDBQueryExpression<Transaction>()
                .withIndexName("DateIndex")
                .withKeyConditionExpression("userId = :userId AND dateSortKey BETWEEN :startDate AND :endDate")
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false);

        return dynamoDBMapper.query(Transaction.class, queryExpression);
    }

    public void delete(Transaction transaction) {
        dynamoDBMapper.delete(transaction);
    }
}