package com.khudrosoft.vision.model;

public class GetCampaignResponse {
    private String token;

    private String status;

    private CampaignRows[] campaignRows;

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

    public CampaignRows[] getCampaignRows ()
    {
        return campaignRows;
    }

    public void setCampaignRows (CampaignRows[] campaignRows)
    {
        this.campaignRows = campaignRows;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [token = "+token+", status = "+status+", campaignRows = "+campaignRows+"]";
    }
}
