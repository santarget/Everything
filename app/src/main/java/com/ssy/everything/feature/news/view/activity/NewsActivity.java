package com.ssy.everything.feature.news.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4 以上版本
            // 设置 Toolbar 高度为 80dp，适配状态栏
            ViewGroup.LayoutParams layoutParams = toolBar.getLayoutParams();
            layoutParams.height = CommonUtils.dpToPxInt(this, 80);
            toolBar.setLayoutParams(layoutParams);
        } else { // 4.4 以下版本
            // 设置 设置图标距离顶部（状态栏最底）为
            ivSetting.setPadding(ivSetting.getPaddingLeft(),
                    CommonUtils.dpToPxInt(this, 15),
                    ivSetting.getPaddingRight(),
                    ivSetting.getPaddingBottom());
        }

        init();
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
        tab.setupWithViewPager(viewPager);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

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
}
