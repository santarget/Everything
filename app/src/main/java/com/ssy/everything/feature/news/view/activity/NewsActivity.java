package com.ssy.everything.feature.news.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.ssy.everything.R;
import com.ssy.everything.base.BaseActivity;
import com.ssy.everything.common.Constants;
import com.ssy.everything.feature.news.view.fragment.NewsFragment;
import com.ssy.everything.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ssy on 2017/6/5.
 */

public class NewsActivity extends BaseActivity {

    @BindView(R.id.ivBanner)
    ImageView ivBanner;
    @BindView(R.id.ivSetting)
    AppCompatImageView ivSetting;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.collapsingToolBar)
    CollapsingToolbarLayout collapsingToolBar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<NewsFragment> fragmentList;
    private NewsFragmentAdapter adapter;
    private int[] titlesRes;
    private int appBarHeight = 220;//appbar高度
    private CollapsingToolbarLayoutState state; // CollapsingToolbarLayout 折叠状态

    private enum CollapsingToolbarLayoutState {
        EXPANDED, // 完全展开
        COLLAPSED, // 折叠
        INTERNEDIATE // 中间状态
    }

    private boolean isBannerBig; // banner 是否是大图
    private boolean isBannerAniming; // banner 放大缩小的动画是否正在执行

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        init();
        initListener();
    }

    private void init() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new NewsFragment().setType(Constants.NEWS_TOP));
        fragmentList.add(new NewsFragment().setType(Constants.NEWS_GUOJI));
        fragmentList.add(new NewsFragment().setType(Constants.NEWS_GUONEI));
        fragmentList.add(new NewsFragment().setType(Constants.NEWS_KEJI));
        fragmentList.add(new NewsFragment().setType(Constants.NEWS_YULE));
        fragmentList.add(new NewsFragment().setType(Constants.NEWS_TIYU));
        titlesRes = new int[]{R.string.news_top, R.string.news_guoji, R.string.news_guonei, R.string.news_keji,
                R.string.news_yule, R.string.news_tiyu};
        adapter = new NewsFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());
        tab.setupWithViewPager(viewPager);

    }

    private void initListener() {
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED; // 修改状态标记为展开
                        CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolBar.getLayoutParams();
                        layoutParams.topMargin = CommonUtils.getStatusBarHeight(NewsActivity.this);
                        toolBar.setLayoutParams(layoutParams);
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
//                        mFloatingActionButton.hide();
                        state = CollapsingToolbarLayoutState.COLLAPSED; // 修改状态标记为折叠
                        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
                        layoutParams.height = CommonUtils.dp2PxInt(NewsActivity.this, appBarHeight);
                        appBar.setLayoutParams(layoutParams);
                        isBannerBig = false;
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
//                            mFloatingActionButton.show();
                        }
                        state = CollapsingToolbarLayoutState.INTERNEDIATE; // 修改状态标记为中间
                    }
                }
            }
        });

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBannerAniming) {
                    return;
                }
                startBannerAnim();
            }
        });
    }

    private void startBannerAnim() {
        final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
        ValueAnimator animator;
        if (isBannerBig) {
            animator = ValueAnimator.ofInt(CommonUtils.getScreenHeight(this), CommonUtils.dp2PxInt(this, appBarHeight));
        } else {
            animator = ValueAnimator.ofInt(CommonUtils.dp2PxInt(this, appBarHeight), CommonUtils.getScreenHeight(this));
        }
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                appBar.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isBannerBig = !isBannerBig;
                isBannerAniming = false;
            }
        });
        animator.start();
        isBannerAniming = true;
    }

    class NewsFragmentAdapter extends FragmentPagerAdapter {


        public NewsFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(titlesRes[position]);
        }
    }

    @Override
    public void onBackPressed() {
        if (isBannerAniming) {
            return;
        }
        if (isBannerBig) {
            startBannerAnim();
        } else {
            super.onBackPressed();
        }
    }
}
