package com.ssy.everything.network;

import com.ssy.everything.network.api.GankApi;
import com.ssy.everything.network.api.NewsApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetWork {
    private static GankApi gankApi;
    private static NewsApi newsApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();


    public static GankApi getGankApi() {
        if (gankApi == null) {
            synchronized (GankApi.class) {
                if (gankApi == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .client(okHttpClient)
                            .baseUrl("http://gank.io/api/")
                            .addConverterFactory(GsonConverterFactory.create()) //增加返回值为Gson的支持(以实体类返回) 如果要自定义解析，添加返回string的支持
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   //增加返回值为Oservable<T>的支持
                            .build();
                    gankApi = retrofit.create(GankApi.class);
                }
            }
        }
        return gankApi;
    }

    public static NewsApi getNewsApi() {
        if (newsApi == null) {
            synchronized (NewsApi.class) {
                if (newsApi == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .client(okHttpClient)
                            .baseUrl("http://v.juhe.cn/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                    newsApi = retrofit.create(NewsApi.class);
                }
            }
        }
        return newsApi;
    }
}
