package com.khudrosoft.vision.model;

public class CampaignEntryPageResponse {
    private String region;

    private String token;

    private String status;

    private String team;
    private String camp_id;

    //@SerializedName("products")
    private SuggestedProducts[] products;

    private SuggestedDoctors[] suggestedDoctors;

/*    @SerializedName("product1")
    private Product[] productOne;*/

    public String getRegion ()
    {
        return region;
    }

    public void setRegion (String region)
    {
        this.region = region;
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

    public String getTeam ()
    {
        return team;
    }

    public void setTeam (String team)
    {
        this.team = team;
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
        return "ClassPojo [region = "+region+", token = "+token+", status = "+status+", team = "+team+",   suggestedDoctors = "+suggestedDoctors+", productOne = " +"]";
    }

    public SuggestedProducts[] getProducts() {
        return products;
    }

    public void setSuggestedProducts(SuggestedProducts[] products) {
        this.products = products;
    }

    public String getCamp_id() {
        return camp_id;
    }

    public void setCamp_id(String camp_id) {
        this.camp_id = camp_id;
    }
}
