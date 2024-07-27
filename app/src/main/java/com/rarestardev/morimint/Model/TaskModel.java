package com.rarestardev.morimint.Model;

import com.google.gson.annotations.SerializedName;

public class TaskModel {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("gift_coin")
    private long gift_coin;

    @SerializedName("link")
    private String link;


    public TaskModel(int id, String title, long gift_coin, String link) {
        this.id = id;
        this.title = title;
        this.gift_coin = gift_coin;
        this.link = link;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getGift_coin() {
        return gift_coin;
    }

    public void setGift_coin(long gift_coin) {
        this.gift_coin = gift_coin;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
