package com.ssy.everything;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.ssy.everything.common.Constants;
import com.ssy.everything.common.CrashHandler;
import com.ssy.everything.image.skip_cer_verify.OkHttpUrlLoader;
import com.ssy.everything.image.skip_cer_verify.UnsafeOkHttpClient;
import com.ssy.greendao.gen.DaoMaster;
import com.ssy.greendao.gen.DaoSession;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.InputStream;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * @author ssy
 * @date 2017/5/19
 */

public class EverythingApplication extends Application {
    private static final String TAG = "EverythingApplication";
    private static EverythingApplication instance;
    public static boolean hasLogin;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //true 开启调试模式
        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APP_ID, true);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        //初始化滑动返回
        BGASwipeBackManager.getInstance().init(this);

        //Glide绕过https安全验证
        Glide.get(this).register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(UnsafeOkHttpClient.getUnsafeOkHttpClient()));

        initDatabase();
    }

    public static EverythingApplication getInstance() {
        return instance;
    }

    /**
     * 设置greenDao
     */
    private void initDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "everything_database", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}

