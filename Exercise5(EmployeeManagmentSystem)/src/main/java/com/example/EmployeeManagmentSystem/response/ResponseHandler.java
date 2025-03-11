package com.example.EmployeeManagmentSystem.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseHandler {
    private Object data;
    private String message;
    private int status;
    private boolean success;
    private String key;

    public ResponseHandler() {

    }

    public ResponseHandler(Object data, String message, int status, boolean success, String key) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.success = success;
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}