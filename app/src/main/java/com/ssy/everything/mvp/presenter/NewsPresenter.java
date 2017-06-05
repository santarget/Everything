package com.ssy.everything.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ssy.everything.base.BasePresenter;
import com.ssy.everything.base.BaseView;
import com.ssy.everything.bean.NewsInfo;
import com.ssy.everything.mvp.model.NewsModel;
import com.ssy.everything.mvp.view.iview.INewsView;

import rx.Observer;

/**
 * Created by ssy on 2017/6/5.
 */

public class NewsPresenter implements BasePresenter {

    private NewsModel newsModel;
    private INewsView iNewsView;

    public NewsPresenter(INewsView iNewsView) {
        newsModel = new NewsModel();
        this.iNewsView = iNewsView;
    }

    @Override
    public void attachView(@NonNull BaseView view) {

    }

    @Override
    public void detachView() {

    }

    public void loadData() {
        newsModel.getNetworkData().subscribe(new Observer<NewsInfo>() {
            @Override
            public void onCompleted() {
                iNewsView.onSuccess();
            }

            @Override
            public void onError(Throwable e) {
                iNewsView.onFail();
            }

            @Override
            public void onNext(NewsInfo newsInfo) {

            }
        });
    }
}
