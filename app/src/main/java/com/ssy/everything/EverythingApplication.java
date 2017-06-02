package com.ssy.everything;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.ssy.everything.image.skip_cer_verify.OkHttpUrlLoader;
import com.ssy.everything.image.skip_cer_verify.UnsafeOkHttpClient;

import java.io.InputStream;

/**
 * Created by ssy on 2017/5/19.
 */

public class EverythingApplication extends Application {
    private static final String TAG = "EverythingApplication";
    private static EverythingApplication instance;
    public static boolean hasLogin;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //Glide绕过https安全验证
        Glide.get(this).register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(UnsafeOkHttpClient.getUnsafeOkHttpClient()));
    }

    public static EverythingApplication getInstance() {
        return instance;
    }
}
