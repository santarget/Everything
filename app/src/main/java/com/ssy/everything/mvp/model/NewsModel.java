package com.ssy.everything.mvp.model;

import com.ssy.everything.base.BaseModel;
import com.ssy.everything.bean.NewsInfo;
import com.ssy.everything.network.NetWork;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ssy on 2017/6/5.
 */

public class NewsModel implements BaseModel {
    @Override
    public void loadData() {

    }

    public Observable<NewsInfo> getNetworkData() {
        return NetWork.getGankApi().getRandomBeauties(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
