package com.rarestardev.morimint.OfflineModel;

public class DailyBonusModel {

    private String days;
    private String bonus;
    private boolean isLocked;
    private boolean isComplete;
    private boolean isLarge;

    public DailyBonusModel(String days, String bonus, boolean isLocked, boolean isComplete,boolean isLarge) {
        this.days = days;
        this.bonus = bonus;
        this.isLocked = isLocked;
        this.isComplete = isComplete;
        this.isLarge = isLarge;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isLarge() {
        return isLarge;
    }

    public void setLarge(boolean large) {
        isLarge = large;
    }
}
