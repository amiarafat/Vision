package com.khudrosoft.vision.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amir on 11/26/2016.
 */

public class SuggestedProductResponse {

//    {
//        "status": "success",
//            "products": "Login successfully",
//            "token": "GNTBClhw8oE1lGfIhE3tjntSfDkb2Wchby4qtmzq",
//            "user_id": "RX-PS-1"
//    }

    private String status;
    private String token;
    @SerializedName("suggestedProducts")
    private Product products[];


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

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }
}
