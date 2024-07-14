package com.rarestardev.morimint.Model;

public class TaskModel {

    private int id;

    private String title;

    private int reward;

    private boolean isComplete;

    private String task_link;

    public TaskModel(int id, String title, int reward, boolean isComplete, String task_link) {
        this.id = id;
        this.title = title;
        this.reward = reward;
        this.isComplete = isComplete;
        this.task_link = task_link;
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

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getTask_link() {
        return task_link;
    }

    public void setTask_link(String task_link) {
        this.task_link = task_link;
    }
}
