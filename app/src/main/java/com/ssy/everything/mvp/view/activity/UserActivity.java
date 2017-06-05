package com.ssy.everything.mvp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ssy.everything.R;
import com.ssy.everything.base.BaseActivity;

/**
 * Created by ssy on 2017/5/19.
 */

public class UserActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }
}
