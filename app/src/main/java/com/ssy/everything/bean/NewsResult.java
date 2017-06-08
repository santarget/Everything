package com.ssy.everything.bean;

import java.util.ArrayList;

/**
 * Created by ssy on 2017/6/6.
 */

public class NewsResult {
    private String stat;

    private ArrayList<NewsInfo> data;

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getStat() {
        return this.stat;
    }

    public ArrayList<NewsInfo> getData() {
        return data;
    }

    public void setData(ArrayList<NewsInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NewsResult{" +
                "stat='" + stat + '\'' +
                ", data=" + data +
                '}';
    }
}
