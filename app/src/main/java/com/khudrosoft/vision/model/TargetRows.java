package com.khudrosoft.vision.model;

import com.google.gson.annotations.SerializedName;

public class TargetRows {
    private String dsm_ffc;

    private String rx_image_remaining;

    private String rsm_ffc;

    private String rx_image_requirement;

    private String sales_target;

    private String id;

    private String camp_id;

    private String sales_remaining;

    private String ffc;

    private String name;


    private String user_id;
    private String upto_date;

    @SerializedName("4p_rx_image_requirement")
    private String rx_image_requirement_4p;


    @SerializedName("4p_rx_image_remaining")
    private String rx_image_remaining_4p;


    public String getDsm_ffc ()
    {
        return dsm_ffc;
    }

    public void setDsm_ffc (String dsm_ffc)
    {
        this.dsm_ffc = dsm_ffc;
    }

    public String getRx_image_remaining ()
    {
        return rx_image_remaining;
    }

    public void setRx_image_remaining (String rx_image_remaining)
    {
        this.rx_image_remaining = rx_image_remaining;
    }

    public String getRsm_ffc ()
    {
        return rsm_ffc;
    }

    public void setRsm_ffc (String rsm_ffc)
    {
        this.rsm_ffc = rsm_ffc;
    }

    public String getRx_image_requirement ()
    {
        return rx_image_requirement;
    }

    public void setRx_image_requirement (String rx_image_requirement)
    {
        this.rx_image_requirement = rx_image_requirement;
    }

    public String getSales_target ()
    {
        return sales_target;
    }

    public void setSales_target (String sales_target)
    {
        this.sales_target = sales_target;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCamp_id ()
    {
        return camp_id;
    }

    public void setCamp_id (String camp_id)
    {
        this.camp_id = camp_id;
    }

    public String getSales_remaining ()
    {
        return sales_remaining;
    }

    public void setSales_remaining (String sales_remaining)
    {
        this.sales_remaining = sales_remaining;
    }

    public String getFfc ()
    {
        return ffc;
    }

    public void setFfc (String ffc)
    {
        this.ffc = ffc;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }



    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }


    public String getUpto_date ()
    {
        return upto_date;
    }

    public void setUpto_date (String upto_date)
    {
        this.upto_date = upto_date;
    }

    public String getRx_image_requirement_4p() {
        return rx_image_requirement_4p;
    }

    public void setRx_image_requirement_4p(String rx_image_requirement_4p) {
        this.rx_image_requirement_4p = rx_image_requirement_4p;
    }

    public String getRx_image_remaining_4p() {
        return rx_image_remaining_4p;
    }

    public void setRx_image_remaining_4p(String rx_image_remaining_4p) {
        this.rx_image_remaining_4p = rx_image_remaining_4p;
    }
}
