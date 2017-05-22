package com.ssy.everything.view;

/**
 * Created by ssy on 2017/5/5.
 */

public interface BaseView {
    void showProgress(String msg);

    void showProgress(String msg, int progress);

    void hideProgress();

    void close();
}
