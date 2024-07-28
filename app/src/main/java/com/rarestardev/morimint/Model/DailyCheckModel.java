package com.rarestardev.morimint.Model;

import com.google.gson.annotations.SerializedName;

public class DailyCheckModel {

    @SerializedName("id")
    private int id;

    @SerializedName("gift")
    private long gift;

    @SerializedName("days")
    private long days;

    @SerializedName("date_time")
    private String date_time;

    public DailyCheckModel(int id, long gift, long days, String date_time) {
        this.id = id;
        this.gift = gift;
        this.days = days;
        this.date_time = date_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getGift() {
        return gift;
    }

    public void setGift(long gift) {
        this.gift = gift;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
