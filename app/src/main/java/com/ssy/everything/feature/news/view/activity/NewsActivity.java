package com.ssy.everything.feature.news.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ssy.everything.R;
import com.ssy.everything.base.BaseActivity;
import com.ssy.everything.bean.NewsInfo;
import com.ssy.everything.feature.news.presenter.NewsPresenter;
import com.ssy.everything.feature.news.view.adapter.NewsAdapter;
import com.ssy.everything.view.recyclerview.MyItemDecoration;
import com.ssy.everything.feature.news.view.iview.INewsView;
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

        initSwipeRefreshLayout();
        initRecyclerView();
        initListener();

        newsPresenter = new NewsPresenter(this, "top");
        newsPresenter.subscribe();
    }

    private void initSwipeRefreshLayout() {
        srlRoot.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        srlRoot.setColorSchemeColors(getResources().getColor(android.R.color.white));
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
        rvNews.addItemDecoration(new MyItemDecoration(this, LinearLayoutManager.HORIZONTAL));

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
                Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("news", info);
                intent.putExtras(bundle);
                NewsActivity.this.startActivity(intent);
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
                adapter.setNewsInfos(newsInfos, false);
            }
        } else {
            Toast.makeText(this, "没有更多资讯", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void stopTooMuchRequest() {
        Toast.makeText(this, "您的请求太频繁，请稍候再试", Toast.LENGTH_SHORT).show();
        srlRoot.setRefreshing(false);
    }

    @Override
    public void showMoreData(ArrayList<NewsInfo> newsInfos) {
        adapter.setNewsInfos(newsInfos, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsPresenter.unsubscribe();
    }
}
