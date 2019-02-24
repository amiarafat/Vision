package com.khudrosoft.vision.model;

public class SuggestedDoctorResponse {

    private String token;

    private String status;

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
        return "ClassPojo [token = "+token+", status = "+status+", suggestedDoctors = "+suggestedDoctors+"]";
    }
}
