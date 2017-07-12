package com.ssy.everything.feature.news.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ssy.everything.R;
import com.ssy.everything.base.BaseLazyFragment;
import com.ssy.everything.bean.NewsInfo;
import com.ssy.everything.common.Constants;
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

public class NewsFragment extends BaseLazyFragment implements INewsView {
    private String type;
    private Activity activity;

    private RecyclerView rvNews;
    private SwipeRefreshLayout srlRoot;

    private NewsPresenter newsPresenter;
    private NewsAdapter adapter;
    private boolean isLoadingMore;

    public NewsFragment setType(String type) {
        if (StringUtils.isEmpty(type)) {
            this.type = Constants.NEWS_TOP;
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
        isLoadingMore = false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        activity = getActivity();
        rvNews = (RecyclerView) view.findViewById(R.id.rv_news);
        srlRoot = (SwipeRefreshLayout) view.findViewById(R.id.srl_root);

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
                intent.putExtra("news", info);
                activity.startActivity(intent);
            }
        });
        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//               上拉到底部再上拉才加载更多
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
                    if (dy == 0) {
                        recyclerView.removeView(recyclerView.getChildAt(adapter.getItemCount() - 1));
//                        adapter.notifyItemRemoved(adapter.getItemCount() - 1);
                    } else if (!isLoadingMore) {
                        isLoadingMore = true;
                        newsPresenter.loadMore();
                    }

                }
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
        if (newsPresenter == null) {
            newsPresenter = new NewsPresenter(this, type);
            newsPresenter.subscribe();
        }
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected void DetoryViewAndThing() {
        if (newsPresenter != null) {
            newsPresenter.unsubscribe();
        }
    }

}
