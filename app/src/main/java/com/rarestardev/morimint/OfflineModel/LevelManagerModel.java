package com.rarestardev.morimint.OfflineModel;

public class LevelManagerModel {

    private int level;

    private int imageId;

    private boolean isPassed;

    private int energy;

    private int tap;

    private long coin;

    public LevelManagerModel(int level, int imageId, boolean isPassed, int energy, int tap, long coin) {
        this.level = level;
        this.imageId = imageId;
        this.isPassed = isPassed;
        this.energy = energy;
        this.tap = tap;
        this.coin = coin;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getTap() {
        return tap;
    }

    public void setTap(int tap) {
        this.tap = tap;
    }

    public long getCoin() {
        return coin;
    }

    public void setCoin(long coin) {
        this.coin = coin;
    }
}
