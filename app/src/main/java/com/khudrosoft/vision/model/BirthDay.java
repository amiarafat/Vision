package com.khudrosoft.vision.model;

import com.google.gson.annotations.SerializedName;

public class BirthDay
{
    private String token;

    private String status;

    @SerializedName("doctorRows")
    private SuggestedDoctors[] doctorRows;

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

    public SuggestedDoctors[] getDoctorRows ()
    {
        return doctorRows;
    }

    public void setDoctorRows (SuggestedDoctors[] doctorRows)
    {
        this.doctorRows = doctorRows;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [token = "+token+", status = "+status+", doctorRows = "+doctorRows+"]";
    }
}

