package com.rarestardev.morimint.Model;

import com.google.gson.annotations.SerializedName;

public class GiftCodeModel {

    @SerializedName("code")
    private String code;


    public GiftCodeModel(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
