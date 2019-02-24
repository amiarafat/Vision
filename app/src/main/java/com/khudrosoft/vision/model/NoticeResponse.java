package com.khudrosoft.vision.model;

public class NoticeResponse {

    private NoticeRows[] noticeRows;

    private String token;

    private String status;

    public NoticeRows[] getNoticeRows() {
        return noticeRows;
    }

    public void setNoticeRows(NoticeRows[] noticeRows) {
        this.noticeRows = noticeRows;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [noticeRows = " + noticeRows + ", token = " + token + ", status = " + status + "]";
    }
}