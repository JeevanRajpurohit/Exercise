package com.example.ExpenseAndIncomeTracker.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHandler {
    private Object data;
    private String message;
    private int status;
    private boolean success;
    private String entity;
    private String token;

    public ResponseHandler(Object data, String message, int status, boolean success, String entity) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.success = success;
        this.entity = entity;
    }
}