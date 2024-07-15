package com.rarestardev.morimint.Model;

import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("id")
    private int id;

    @SerializedName("total_invites")
    private int total_invites;

    @SerializedName("is_superuser")
    private boolean is_superuser;

    @SerializedName("username")
    private String username;

    @SerializedName("is_staff")
    private boolean is_staff;

    @SerializedName("is_active")
    private boolean is_active;

    @SerializedName("email")
    private String email;

    @SerializedName("referral_code")
    private long referral_code;

    @SerializedName("level")
    private int level;

    @SerializedName("level_xp")
    private int level_xp;

    @SerializedName("blue_tick")
    private boolean blue_tick;

    @SerializedName("coin")
    private long coin;

    @SerializedName("wallet")
    private String wallet;

    @SerializedName("ads")
    private int ads;

    @SerializedName("referred_by")
    private int referred_by;


    public Users(int id, int total_invites, boolean is_superuser, String username, boolean is_staff, boolean is_active, String email, int referral_code, int level, int level_xp, boolean blue_tick, int coin, String wallet, int ads, int referred_by) {
        this.id = id;
        this.total_invites = total_invites;
        this.is_superuser = is_superuser;
        this.username = username;
        this.is_staff = is_staff;
        this.is_active = is_active;
        this.email = email;
        this.referral_code = referral_code;
        this.level = level;
        this.level_xp = level_xp;
        this.blue_tick = blue_tick;
        this.coin = coin;
        this.wallet = wallet;
        this.ads = ads;
        this.referred_by = referred_by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel_xp() {
        return level_xp;
    }

    public void setLevel_xp(int level_xp) {
        this.level_xp = level_xp;
    }

    public boolean isBlue_tick() {
        return blue_tick;
    }

    public void setBlue_tick(boolean blue_tick) {
        this.blue_tick = blue_tick;
    }

    public long getCoin() {
        return coin;
    }

    public void setCoin(long coin) {
        this.coin = coin;
    }

    public int getAds() {
        return ads;
    }

    public void setAds(int ads) {
        this.ads = ads;
    }

    public int getTotal_invites() {
        return total_invites;
    }

    public void setTotal_invites(int total_invites) {
        this.total_invites = total_invites;
    }

    public boolean isIs_superuser() {
        return is_superuser;
    }

    public void setIs_superuser(boolean is_superuser) {
        this.is_superuser = is_superuser;
    }

    public boolean isIs_staff() {
        return is_staff;
    }

    public void setIs_staff(boolean is_staff) {
        this.is_staff = is_staff;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public long getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(long referral_code) {
        this.referral_code = referral_code;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public int getReferred_by() {
        return referred_by;
    }

    public void setReferred_by(int referred_by) {
        this.referred_by = referred_by;
    }
}
