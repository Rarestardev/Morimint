package com.rarestardev.morimint.Model;

import com.google.gson.annotations.SerializedName;

public class DailyRewardModel {
    @SerializedName("id")
    private int id;

    @SerializedName("is_active")
    private boolean is_active;

    @SerializedName("link")
    private String link;

    @SerializedName("gift_coin")
    private int gift_coin;

    @SerializedName("title")
    private String title;

    @SerializedName("deactivate_date")
    private String deactivate_date;

    @SerializedName("image")
    private String image;

    public DailyRewardModel(int id, boolean is_active, String link, int gift_coin, String title, String deactivate_date, String image) {
        this.id = id;
        this.is_active = is_active;
        this.link = link;
        this.gift_coin = gift_coin;
        this.title = title;
        this.deactivate_date = deactivate_date;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getGift_coin() {
        return gift_coin;
    }

    public void setGift_coin(int gift_coin) {
        this.gift_coin = gift_coin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeactivate_date() {
        return deactivate_date;
    }

    public void setDeactivate_date(String deactivate_date) {
        this.deactivate_date = deactivate_date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
