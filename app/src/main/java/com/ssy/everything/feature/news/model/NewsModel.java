package com.ssy.everything.feature.news.model;

import com.ssy.everything.base.BaseModel;
import com.ssy.everything.bean.NewsInfo;
import com.ssy.everything.bean.NewsResponse;
import com.ssy.everything.common.Constants;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.ssy.everything.network.NetWork.getNewsApi;

/**
 * Created by ssy on 2017/6/5.
 */

public class NewsModel implements BaseModel {

    @Override
    public void loadData() {

    }

    public Observable<ArrayList<NewsInfo>> getNetworkData(String type) {
        return getNewsApi().getNews(type, Constants.APPKEY_NEWS)
                .subscribeOn(Schedulers.io())
                .map(new Func1<NewsResponse, ArrayList<NewsInfo>>() {
                    @Override
                    public ArrayList<NewsInfo> call(NewsResponse newsResponse) {
                        return newsResponse.getResult().getData();
                    }
                });

    }

}
