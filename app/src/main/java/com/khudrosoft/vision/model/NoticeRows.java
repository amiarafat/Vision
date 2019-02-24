package com.khudrosoft.vision.model;

public class NoticeRows {

    private String id;

    private String n_date;

    private String team;

    private String notice_id;

    private String notice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getN_date() {
        return n_date;
    }

    public void setN_date(String n_date) {
        this.n_date = n_date;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(String notice_id) {
        this.notice_id = notice_id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", n_date = " + n_date + ", team = " + team + ", notice_id = " + notice_id + ", notice = " + notice + "]";
    }
}
