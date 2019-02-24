package com.khudrosoft.vision.model;

/**
 * Created by User on 1/7/2017.
 */

public class SimpleResponse {

    private String status;
    private String data;
    private String transaction_id;
    private String driver_balance;
    private String profile_picture;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getDriver_balance() {
        return driver_balance;
    }

    public void setDriver_balance(String driver_balance) {
        this.driver_balance = driver_balance;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
