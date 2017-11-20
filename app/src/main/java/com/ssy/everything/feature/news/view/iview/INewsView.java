package com.ssy.everything.feature.news.view.iview;

import com.ssy.everything.base.BaseView;
import com.ssy.everything.bean.NewsInfo;

import java.util.ArrayList;

/**
 * Created by ssy on 2017/6/5.
 */

public interface INewsView extends BaseView {

    void showData(ArrayList<NewsInfo> newsInfos);

    void stopTooMuchRequest();

    void showMoreData(ArrayList<NewsInfo> newsInfos);

    void showNoMoreData();

}
