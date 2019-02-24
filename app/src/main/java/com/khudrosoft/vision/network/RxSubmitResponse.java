package com.khudrosoft.vision.network;

public class RxSubmitResponse {
    private String message;

    private String rx_no;

    private String token;

    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRx_no() {
        return rx_no;
    }

    public void setRx_no(String rx_no) {
        this.rx_no = rx_no;
    }

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

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", rx_no = " + rx_no + ", token = " + token + ", status = " + status + "]";
    }
}
