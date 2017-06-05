package com.ssy.everything.mvp.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ssy.everything.R;
import com.ssy.everything.base.BaseActivity;
import com.ssy.everything.mvp.presenter.NewsPresenter;
import com.ssy.everything.mvp.view.iview.INewsView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ssy on 2017/6/5.
 */

public class NewsActivity extends BaseActivity implements INewsView {
    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    @BindView(R.id.srl_root)
    SwipeRefreshLayout srlRoot;

    private NewsPresenter newsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        newsPresenter = new NewsPresenter(this);
        srlRoot.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark));
        initListener();
    }

    private void initListener() {
        srlRoot.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsPresenter.loadData();
            }
        });
    }

    @Override
    public void onFail() {
        srlRoot.setRefreshing(false);
        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        srlRoot.setRefreshing(false);
        Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
    }
}
