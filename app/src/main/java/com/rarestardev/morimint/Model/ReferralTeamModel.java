package com.rarestardev.morimint.Model;

import com.google.gson.annotations.SerializedName;

public class ReferralTeamModel {

    @SerializedName("username")
    private String username;

    @SerializedName("level")
    private int level;


    public ReferralTeamModel(String username, int level) {
        this.username = username;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
