package com.rarestardev.morimint.Response;

import com.google.gson.annotations.SerializedName;

public class SiteGiftCodeResponse {

    @SerializedName("code")
    private String code;


    public SiteGiftCodeResponse(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
