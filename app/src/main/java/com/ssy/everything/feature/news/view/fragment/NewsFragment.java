package com.ssy.everything.feature.news.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ssy.everything.R;
import com.ssy.everything.base.LazyLoadFragment;
import com.ssy.everything.bean.NewsInfo;
import com.ssy.everything.feature.news.presenter.NewsPresenter;
import com.ssy.everything.feature.news.view.activity.NewsDetailActivity;
import com.ssy.everything.feature.news.view.adapter.NewsAdapter;
import com.ssy.everything.feature.news.view.iview.INewsView;
import com.ssy.everything.util.ListUtils;
import com.ssy.everything.util.StringUtils;
import com.ssy.everything.view.recyclerview.MyItemDecoration;

import java.util.ArrayList;

/**
 * Created by ssy on 2017/6/16.
 */

public class NewsFragment extends LazyLoadFragment implements INewsView {
    private String type;
    private Activity activity;

    private RecyclerView rvNews;
    private SwipeRefreshLayout srlRoot;

    private NewsPresenter newsPresenter;
    private NewsAdapter adapter;

    @Override
    protected void init() {
        activity = getActivity();
        rvNews = findViewById(R.id.rv_news);
        srlRoot = findViewById(R.id.srl_root);

        srlRoot.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        srlRoot.setColorSchemeColors(getResources().getColor(android.R.color.white));
        srlRoot.setRefreshing(true);

        //设置布局管理器
        rvNews.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new NewsAdapter(activity, null);
        rvNews.setAdapter(adapter);
        //设置Item增加、移除动画
//        rvNews.setItemAnimator(new DefaultItemAnimator());
//        //添加分割线
        rvNews.addItemDecoration(new MyItemDecoration(activity, LinearLayoutManager.HORIZONTAL));

        newsPresenter = new NewsPresenter(this, type);
        newsPresenter.subscribe();

        initListener();

    }
    private void initListener() {
        srlRoot.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsPresenter.loadNewData();
            }
        });
        adapter.setOnItemClickListener(new NewsAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, NewsInfo info) {
                Intent intent = new Intent(activity, NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", info);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });
        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("aaa", "onScrollStateChanged:" + newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == adapter.getItemCount()) {
//                    Log.d("test", "loading executed");
//
//                    boolean isRefreshing = srlRoot.isRefreshing();
//                    if (isRefreshing) {
//                        adapter.notifyItemRemoved(adapter.getItemCount());
//                        return;
//                    }
//                    //load data
//                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = srlRoot.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    newsPresenter.loadMore();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        newsPresenter.unsubscribe();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_news;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void stopLoad() {
    }

    public NewsFragment setType(String type) {
        if (StringUtils.isEmpty(type)) {
            this.type = "";
        } else {
            this.type = type;
        }
        return this;
    }

    @Override
    public void onFailure() {
        srlRoot.setRefreshing(false);
        Toast.makeText(activity, R.string.fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        srlRoot.setRefreshing(false);
    }

    @Override
    public void showData(ArrayList<NewsInfo> newsInfos) {
        if (!ListUtils.isEmpty(newsInfos)) {
            if (adapter == null) {
                adapter = new NewsAdapter(activity, newsInfos);
                rvNews.setAdapter(adapter);
            } else {
                adapter.setNewsInfos(newsInfos, false);
            }
        } else {
            Toast.makeText(activity, R.string.no_more_data, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void stopTooMuchRequest() {
        Toast.makeText(activity, R.string.request_too_much, Toast.LENGTH_SHORT).show();
        srlRoot.setRefreshing(false);
    }

    @Override
    public void showMoreData(ArrayList<NewsInfo> newsInfos) {
        adapter.setNewsInfos(newsInfos, true);
    }
}
