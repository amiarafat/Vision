package com.khudrosoft.vision.model;


import com.google.gson.annotations.SerializedName;

public class RxDetails {
    private String imageUrl;

    private String status;

    private String totalPSO;

    private String totalSM;

    private String totalDSM;

    private String totalMonthRx;

    private String totalWeekRx;

    private String totalRx;

    private String totalProduct;

    private String token;

    private String totalRSM;

    private String totalTodayRx;

    private String totalDoctor;

    private String totalLastDayRx;


    @SerializedName("userRow")
    private RxUser userRow;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPSO() {
        return totalPSO;
    }

    public void setTotalPSO(String totalPSO) {
        this.totalPSO = totalPSO;
    }

    public String getTotalSM() {
        return totalSM;
    }

    public void setTotalSM(String totalSM) {
        this.totalSM = totalSM;
    }

    public String getTotalDSM() {
        return totalDSM;
    }

    public void setTotalDSM(String totalDSM) {
        this.totalDSM = totalDSM;
    }

    public String getTotalMonthRx() {
        return totalMonthRx;
    }

    public void setTotalMonthRx(String totalMonthRx) {
        this.totalMonthRx = totalMonthRx;
    }

    public String getTotalWeekRx() {
        return totalWeekRx;
    }

    public void setTotalWeekRx(String totalWeekRx) {
        this.totalWeekRx = totalWeekRx;
    }

    public String getTotalRx() {
        return totalRx;
    }

    public void setTotalRx(String totalRx) {
        this.totalRx = totalRx;
    }

    public String getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(String totalProduct) {
        this.totalProduct = totalProduct;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTotalRSM() {
        return totalRSM;
    }

    public void setTotalRSM(String totalRSM) {
        this.totalRSM = totalRSM;
    }

    public String getTotalTodayRx() {
        return totalTodayRx;
    }

    public void setTotalTodayRx(String totalTodayRx) {
        this.totalTodayRx = totalTodayRx;
    }

    public String getTotalDoctor() {
        return totalDoctor;
    }

    public void setTotalDoctor(String totalDoctor) {
        this.totalDoctor = totalDoctor;
    }

    public RxUser getUserRow() {
        return userRow;
    }

    public void setUserRow(RxUser userRow) {
        this.userRow = userRow;
    }

    @Override
    public String toString() {
        return "ClassPojo [imageUrl = " + imageUrl + ", status = " + status + ", totalPSO = " + totalPSO + ", totalSM = " + totalSM + ", totalDSM = " + totalDSM + ", totalMonthRx = " + totalMonthRx + ", totalWeekRx = " + totalWeekRx + ", totalRx = " + totalRx + ", totalProduct = " + totalProduct + ", token = " + token + ", totalRSM = " + totalRSM + ", totalTodayRx = " + totalTodayRx + ", totalDoctor = " + totalDoctor + ", userRow = " + userRow + "]";
    }

    public String getTotalLastDayRx() {
        return totalLastDayRx;
    }

    public void setTotalLastDayRx(String totalLastDayRx) {
        this.totalLastDayRx = totalLastDayRx;
    }
}
