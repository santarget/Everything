package com.ssy.everything.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ssy.everything.R;
import com.ssy.everything.base.BaseActivity;
import com.ssy.everything.bean.NewsInfo;
import com.ssy.everything.mvp.presenter.NewsPresenter;
import com.ssy.everything.mvp.view.adapter.NewsAdapter;
import com.ssy.everything.mvp.view.decoration.NewsItemDecoration;
import com.ssy.everything.mvp.view.iview.INewsView;
import com.ssy.everything.util.ListUtils;

import java.util.ArrayList;

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
    private NewsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        newsPresenter = new NewsPresenter(this, "top");
        srlRoot.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark));
        initListener();
        initRecyclerView();
        initData();
    }

    private void initData() {
        newsPresenter.loadData();
        srlRoot.setRefreshing(true);
    }

    private void initRecyclerView() {
        //设置布局管理器
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(this, null);
        rvNews.setAdapter(adapter);
        //设置Item增加、移除动画
//        rvNews.setItemAnimator(new DefaultItemAnimator());
//        //添加分割线
        rvNews.addItemDecoration(new NewsItemDecoration(this, DividerItemDecoration.HORIZONTAL));
    }

    private void initListener() {
        srlRoot.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsPresenter.loadNewData();
            }
        });
    }

    @Override
    public void onFailure() {
        srlRoot.setRefreshing(false);
        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        srlRoot.setRefreshing(false);
    }


    @Override
    public void showData(ArrayList<NewsInfo> newsInfos) {
        if (!ListUtils.isEmpty(newsInfos)) {
            if (adapter == null) {
                adapter = new NewsAdapter(this, newsInfos);
                rvNews.setAdapter(adapter);
            } else {
                adapter.setNewsInfos(newsInfos);
            }
        } else {
            Toast.makeText(this, "没有更多资讯", Toast.LENGTH_SHORT).show();
        }
    }

}
