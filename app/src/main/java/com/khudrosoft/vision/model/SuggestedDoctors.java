package com.khudrosoft.vision.model;

public class SuggestedDoctors {
    private String id;

    private String l_degree;

    private String address;

    private String name;

    private String birth_day;

    private String designation;

    private String doctor_id;

    private String m_day;

    private String institution;

    private String mobile_no;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getL_degree ()
    {
        return l_degree;
    }

    public void setL_degree (String l_degree)
    {
        this.l_degree = l_degree;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getBirth_day ()
    {
        return birth_day;
    }

    public void setBirth_day (String birth_day)
    {
        this.birth_day = birth_day;
    }

    public String getDesignation ()
    {
        return designation;
    }

    public void setDesignation (String designation)
    {
        this.designation = designation;
    }

    public String getDoctor_id ()
    {
        return doctor_id;
    }

    public void setDoctor_id (String doctor_id)
    {
        this.doctor_id = doctor_id;
    }

    public String getM_day ()
    {
        return m_day;
    }

    public void setM_day (String m_day)
    {
        this.m_day = m_day;
    }

    public String getInstitution ()
    {
        return institution;
    }

    public void setInstitution (String institution)
    {
        this.institution = institution;
    }

    public String getMobile_no ()
    {
        return mobile_no;
    }

    public void setMobile_no (String mobile_no)
    {
        this.mobile_no = mobile_no;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

}
