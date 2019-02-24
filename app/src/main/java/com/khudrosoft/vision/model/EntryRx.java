package com.khudrosoft.vision.model;

public class EntryRx
{
    private String token;

    private String status;

    private SuggestedProducts[] suggestedProducts;

    private SuggestedDoctors[] suggestedDoctors;

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

    public SuggestedProducts[] getSuggestedProducts ()
    {
        return suggestedProducts;
    }

    public void setSuggestedProducts (SuggestedProducts[] suggestedProducts)
    {
        this.suggestedProducts = suggestedProducts;
    }

    public SuggestedDoctors[] getSuggestedDoctors ()
    {
        return suggestedDoctors;
    }

    public void setSuggestedDoctors (SuggestedDoctors[] suggestedDoctors)
    {
        this.suggestedDoctors = suggestedDoctors;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [token = "+token+", status = "+status+", suggestedProducts = "+suggestedProducts+", suggestedDoctors = "+suggestedDoctors+"]";
    }
}

