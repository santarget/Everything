package com.ssy.everything.feature.news.model;

import com.ssy.everything.base.BaseModel;
import com.ssy.everything.bean.response.GankResult;

import rx.Observable;

/**
 * Created by ssy on 2017/6/20.
 */

public interface IGirlModel extends BaseModel {

    Observable<GankResult> getRandomGirl();

}
