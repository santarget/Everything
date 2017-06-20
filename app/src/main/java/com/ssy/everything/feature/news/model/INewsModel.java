package com.ssy.everything.feature.news.model;

import com.ssy.everything.base.BaseModel;
import com.ssy.everything.bean.NewsInfo;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ssy on 2017/6/20.
 */

public interface INewsModel extends BaseModel {
    public Observable<ArrayList<NewsInfo>> getNetworkData(String type);

}
