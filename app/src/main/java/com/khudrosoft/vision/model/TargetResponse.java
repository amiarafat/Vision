package com.khudrosoft.vision.model;

public class TargetResponse {
    private String token;

    private String status;

    private TargetRows[] targetRows;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TargetRows[] getTargetRows() {
        return targetRows;
    }

    public void setTargetRows(TargetRows[] targetRows) {
        this.targetRows = targetRows;
    }

    @Override
    public String toString() {
        return "ClassPojo [token = " + token + ", status = " + status + ", targetRows = " + targetRows + "]";
    }
}
