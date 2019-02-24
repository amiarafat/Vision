package com.khudrosoft.vision.model;

public class UpdatePassResponse {

    private String status;
    private String message;
    private String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
//    {
//        "status": "success",
//            "message": "Password has been updated successfully",
//            "token": "Ye2QbokCvn45K8K3fnvfVsKoOMsXKn0OmvyWjgsA"
//    }
}
