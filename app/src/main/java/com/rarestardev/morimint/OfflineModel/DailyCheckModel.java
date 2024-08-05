package com.rarestardev.morimint.Model;


public class DailyCheckModel {

    private int day;
    private boolean isClaimed;
    private String reward;
    private long coin;

    public DailyCheckModel(int day, boolean isClaimed,String reward,long coin) {
        this.day = day;
        this.isClaimed = isClaimed;
        this.reward = reward;
        this.coin = coin;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isClaimed() {
        return isClaimed;
    }

    public void setClaimed(boolean claimed) {
        isClaimed = claimed;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public long getCoin() {
        return coin;
    }

    public void setCoin(long coin) {
        this.coin = coin;
    }
}
