package com.rarestardev.morimint.Response;

import com.google.gson.annotations.SerializedName;

public class MiniAppResponse {


    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private String detail;


    public MiniAppResponse(String status, String message, String detail) {
        this.status = status;
        this.message = message;
        this.detail = detail;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
