package com.ssy.everything.bean.response;

/**
 * Created by ssy on 2017/6/6.
 */

public class NewsResponse {
    public String reason;

    public NewsResult result;


    @Override
    public String toString() {
        return "NewsResponse{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
