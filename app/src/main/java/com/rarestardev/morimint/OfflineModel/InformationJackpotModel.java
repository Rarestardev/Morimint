package com.rarestardev.morimint.OfflineModel;

public class InformationJackpotModel {

    private String desc;
    private int imageView;

    public InformationJackpotModel(String desc, int imageView) {
        this.desc=desc;
        this.imageView=imageView;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getDesc() {
        return desc;
    }

    public int getImageView() {
        return imageView;
    }
}
