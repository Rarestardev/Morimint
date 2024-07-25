package com.rarestardev.morimint.Model;

import com.google.gson.annotations.SerializedName;

public class ApplicationSetupModel {

    @SerializedName("id")
    private int id;

    @SerializedName("is_active")
    private boolean is_active;

    @SerializedName("is_mint_on")
    private boolean is_mint_on;

    @SerializedName("count")
    private long count;

    @SerializedName("deactivate_date")
    private String deactivate_date;

    public ApplicationSetupModel(int id, boolean is_active, boolean is_mint_on, long count, String deactivate_date) {
        this.id = id;
        this.is_active = is_active;
        this.is_mint_on = is_mint_on;
        this.count = count;
        this.deactivate_date = deactivate_date;
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

    public boolean isIs_mint_on() {
        return is_mint_on;
    }

    public void setIs_mint_on(boolean is_mint_on) {
        this.is_mint_on = is_mint_on;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getDeactivate_date() {
        return deactivate_date;
    }

    public void setDeactivate_date(String deactivate_date) {
        this.deactivate_date = deactivate_date;
    }
}
