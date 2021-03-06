package com.ssy.everything.feature.news.presenter;

import com.ssy.everything.base.BasePresenter;
import com.ssy.everything.bean.NewsInfo;
import com.ssy.everything.feature.news.model.impl.NewsModel;
import com.ssy.everything.feature.news.view.iview.INewsView;
import com.ssy.everything.util.ListUtils;
import com.ssy.everything.util.StringUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ssy on 2017/6/5.
 */

public class NewsPresenter implements BasePresenter {

    private NewsModel newsModel;
    private INewsView iNewsView;
    private ArrayList<NewsInfo> newsInfoList;
    private final String type;
    private long lastLoadTimeStamp;//控制频繁刷新的变量

    private CompositeSubscription mSubscriptions;

    public NewsPresenter(INewsView iNewsView, String type) {
        newsModel = new NewsModel();
        mSubscriptions = new CompositeSubscription();
        this.iNewsView = iNewsView;
        this.type = type;
    }

    @Override
    public void subscribe() {
        loadData();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    private void loadData() {
        Subscription subscription = newsModel.getNetworkData(type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<NewsInfo>>() {
                    @Override
                    public void onCompleted() {
                        iNewsView.onSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iNewsView.onFailure();
                    }

                    @Override
                    public void onNext(ArrayList<NewsInfo> list) {
                        newsInfoList = new ArrayList<>(list);
                        if (!ListUtils.isEmpty(list)) {
                            iNewsView.showData(list);
                        }
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 下拉刷新
     */
    public void loadNewData() {
        if (System.currentTimeMillis() - lastLoadTimeStamp <= 3000l) {
            iNewsView.stopTooMuchRequest();
        } else if (!StringUtils.isEmpty(type)) {
            lastLoadTimeStamp = System.currentTimeMillis();
            Subscription subscription = newsModel.getNetworkData(type)
                    .observeOn(Schedulers.newThread())
                    .map(new Func1<ArrayList<NewsInfo>, ArrayList<NewsInfo>>() {

                        @Override
                        public ArrayList<NewsInfo> call(ArrayList<NewsInfo> newsInfos) {
                            if (ListUtils.isEmpty(newsInfoList)) {
                                newsInfoList = new ArrayList<>(newsInfos);
                                return newsInfos;
                            } else {
                                //重复新闻不添加，判断与之前的第一条新闻一样则之后的都丢弃
                                ArrayList<NewsInfo> addNewsInfos = new ArrayList<>();
                                String uniqueKey = newsInfoList.get(0).uniquekey;
                                for (NewsInfo info : newsInfos) {
                                    if (uniqueKey.equals(info.uniquekey)) {
                                        break;
                                    } else {
                                        addNewsInfos.add(info);
                                    }
                                }
                                if (addNewsInfos.size() > 0) {
                                    newsInfoList.addAll(0, addNewsInfos);
                                }
                                return addNewsInfos;
                            }
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ArrayList<NewsInfo>>() {
                        @Override
                        public void onCompleted() {
                            iNewsView.onSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            iNewsView.onFailure();
                        }

                        @Override
                        public void onNext(ArrayList<NewsInfo> newsInfos) {
                            iNewsView.showData(newsInfos);
                        }
                    });
            mSubscriptions.add(subscription);
        }
    }

    /**
     * 上拉加载更多
     */
    public void loadMore() {
        Subscription subscription = Observable.just(newsInfoList.get(0), newsInfoList.get(1), newsInfoList.get(2))
                .subscribeOn(Schedulers.newThread())
                .delay(1, TimeUnit.SECONDS)
                .map(new Func1<NewsInfo, ArrayList<NewsInfo>>() {
                    @Override
                    public ArrayList<NewsInfo> call(NewsInfo newsInfo) {
                        ArrayList<NewsInfo> moreInfos = new ArrayList<>();
                        moreInfos.add(newsInfo);
                        return moreInfos;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ArrayList<NewsInfo>>() {
                    @Override
                    public void call(ArrayList<NewsInfo> newsInfos) {
                        iNewsView.showMoreData(newsInfos);
                    }
                });
        mSubscriptions.add(subscription);
    }
}