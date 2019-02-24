package com.khudrosoft.vision.model;

public class ProfileResponse {
    private String token;

    private String imageUrl;

    private String status;

    private UserRow userRow;
    private String  message;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserRow getUserRow() {
        return userRow;
    }

    public void setUserRow(UserRow userRow) {
        this.userRow = userRow;
    }

    @Override
    public String toString() {
        return "ClassPojo [token = " + token + ", imageUrl = " + imageUrl + ", status = " + status + ", userRow = " + userRow + "]";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
