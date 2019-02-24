package com.khudrosoft.vision.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir on 11/26/2016.
 */

public class LoginResponse {

//    {
//        "status": "success",
//            "message": "Login successfully",
//            "token": "GNTBClhw8oE1lGfIhE3tjntSfDkb2Wchby4qtmzq",
//            "user_id": "RX-PS-1"
//    }

    private String status;
    private String token;
    private String message;
    private String user_id;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
