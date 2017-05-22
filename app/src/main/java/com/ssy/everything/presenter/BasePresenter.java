package com.ssy.everything.presenter;

/**
 * Created by ssy on 2017/5/5.
 */

import android.support.annotation.NonNull;

import com.ssy.everything.view.BaseView;


/**
 * Created by Administrator on 2016/3/25.
 */
public interface BasePresenter {
    /**
     * 注入View，使之能够与View相互响应
     *
     * @param view
     */
    void attachView(@NonNull BaseView view);

    /**
     * 释放资源
     */
    void detachView();

}
