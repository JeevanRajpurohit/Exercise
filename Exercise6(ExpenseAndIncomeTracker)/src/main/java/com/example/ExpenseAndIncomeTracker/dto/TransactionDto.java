package com.example.ExpenseAndIncomeTracker.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDto {
    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String type;

    @NotBlank
    private String category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date modifiedAt;

}
