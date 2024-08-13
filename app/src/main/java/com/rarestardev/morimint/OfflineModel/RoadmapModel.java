package com.rarestardev.morimint.OfflineModel;

public class RoadmapModel {


    private boolean isComplete;

    private int number;

    private String roadmap;

    public RoadmapModel(boolean isComplete, int number, String roadmap) {
        this.isComplete = isComplete;
        this.number = number;
        this.roadmap = roadmap;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getRoadmap() {
        return roadmap;
    }

    public void setRoadmap(String roadmap) {
        this.roadmap = roadmap;
    }
}
