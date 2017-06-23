package com.ssy.everything.bean;

import java.util.List;

/**
 * GankResult
 * Created by bakumon on 2016/12/8.
 */
public class GankResult {

    public boolean error;
    public List<GankBean> results;

    public static class GankBean {

        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
        public List<String> images;
    }
}
