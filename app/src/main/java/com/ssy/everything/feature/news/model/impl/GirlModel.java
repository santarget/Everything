package com.ssy.everything.feature.news.model.impl;

import com.ssy.everything.bean.response.GankResult;
import com.ssy.everything.feature.news.model.IGirlModel;
import com.ssy.everything.network.NetWork;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by ssy on 2017/6/5.
 */

public class GirlModel implements IGirlModel {

    @Override
    public Observable<GankResult> getRandomGirl() {
        return NetWork.getGankApi().getRandomBeauties(1)
                .subscribeOn(Schedulers.io());
    }
}

