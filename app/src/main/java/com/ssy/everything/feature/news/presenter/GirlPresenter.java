package com.ssy.everything.feature.news.presenter;

import com.ssy.everything.EverythingApplication;
import com.ssy.everything.base.BasePresenter;
import com.ssy.everything.bean.response.GankResult;
import com.ssy.everything.feature.news.model.impl.GirlModel;
import com.ssy.everything.feature.news.view.iview.IGirlView;
import com.ssy.greendao.gen.DaoMaster;
import com.ssy.greendao.gen.DaoSession;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ssy on 2017/6/5.
 */

public class GirlPresenter implements BasePresenter {

    private GirlModel girlModel;
    private IGirlView girlView;
    private CompositeSubscription mSubscriptions;

    public GirlPresenter(IGirlView girlView) {
        girlModel = new GirlModel();
        mSubscriptions = new CompositeSubscription();
        this.girlView = girlView;

    }

    @Override
    public void subscribe() {
        getGirl();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }


    public void getGirl() {
        Subscription subscription = girlModel.getRandomGirl()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        girlView.onFailure();
                    }

                    @Override
                    public void onNext(GankResult meiziResult) {
                        if (meiziResult != null && meiziResult.results != null && meiziResult.results.size() > 0 && meiziResult.results.get(0).url != null) {
                            girlView.showNetGirl(meiziResult.results.get(0).url);
                        } else {
                            girlView.onFailure();
                        }
                    }
                });
        mSubscriptions.add(subscription);
        EverythingApplication.getInstance().getDaoSession().getGankBeanDao();
    }
}