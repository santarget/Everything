package com.ssy.everything.bean;

/**
 * Created by ssy on 2017/6/6.
 */

public class NewsResponse {
    private String reason;

    private NewsResult result;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }

    public NewsResult getResult() {
        return result;
    }

    public void setResult(NewsResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
