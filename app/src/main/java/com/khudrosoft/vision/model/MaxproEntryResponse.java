package com.khudrosoft.vision.model;

public class MaxproEntryResponse {
    private MaxProProducts[] maxProProducts;

    private String token;

    private String status;

    public MaxProProducts[] getMaxProProducts ()
    {
        return maxProProducts;
    }

    public void setMaxProProducts (MaxProProducts[] maxProProducts)
    {
        this.maxProProducts = maxProProducts;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [maxProProducts = "+maxProProducts+", token = "+token+", status = "+status+"]";
    }

}
