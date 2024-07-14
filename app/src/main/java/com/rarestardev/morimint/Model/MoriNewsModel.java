package com.rarestardev.morimint.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MoriNewsModel {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("image")
    private String image;

    @SerializedName("link")
    private String link;

    @SerializedName("DateTime")
    private Date DateTime;

    @SerializedName("is_published")
    private boolean is_published;

    public MoriNewsModel(int id, String title, String content, String image, String link, Date dateTime, boolean is_published) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.link = link;
        this.DateTime = dateTime;
        this.is_published = is_published;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDateTime() {
        return DateTime;
    }

    public void setDateTime(Date dateTime) {
        DateTime = dateTime;
    }

    public boolean isIs_published() {
        return is_published;
    }

    public void setIs_published(boolean is_published) {
        this.is_published = is_published;
    }
}
