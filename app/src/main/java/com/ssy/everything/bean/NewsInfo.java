package com.ssy.everything.bean;

import java.io.Serializable;

/**
 * Created by ssy on 2017/6/5.
 */

public class NewsInfo implements Serializable {

    private static final long serialVersionUID = 664582289432294352L;
    public String uniquekey;

    public String title;

    public String date;

    public String category;

    public String author_name;

    public String url;

    public String thumbnail_pic_s;

    public String thumbnail_pic_s02;

    public String thumbnail_pic_s03;


    @Override
    public String toString() {
        return "NewsInfo{" +
                "uniquekey='" + uniquekey + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                ", author_name='" + author_name + '\'' +
                ", url='" + url + '\'' +
                ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                ", thumbnail_pic_s02='" + thumbnail_pic_s02 + '\'' +
                ", thumbnail_pic_s03='" + thumbnail_pic_s03 + '\'' +
                '}';
    }
}
