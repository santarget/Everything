package com.ssy.everything.network;

import android.util.Log;

import com.ssy.everything.network.api.GankApi;
import com.ssy.everything.network.api.NewsApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author ssy
 */
public class NetWork {
    private static OkHttpClient client;
    private static GankApi gankApi;
    private static NewsApi newsApi;


    private static OkHttpClient getClient() {
        if (client == null) {
            synchronized (NetWork.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            // important！！！！！！！！！！！！！！！！！！！！！！！！
                            //！！！打印retrofit日志，上线应去除此拦截器 或者设置为Level.NONE   ！！！
                            // important！！！！！！！！！！！！！！！！！！！！！！！！
                            .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                                @Override
                                public void log(String message) {
                                    Log.i("aaa", "retrofit *** " + message);
                                }
                            }).setLevel(HttpLoggingInterceptor.Level.BODY))
                            //添加请求头
//                            .addInterceptor(new Interceptor() {
//                                @Override
//                                public Response intercept(Chain chain) throws IOException {
//                                    Request request = chain.request()
//                                            .newBuilder()
//                                            .addHeader("Content-Type", "application/json; charset=UTF-8")
////                    .addHeader("Accept-Encoding", "*")
////                    .addHeader("Connection", "keep-alive")
////                    .addHeader("Accept", "*/*")
////                    .addHeader("Access-Control-Allow-Origin", "*")
////                    .addHeader("Access-Control-Allow-Headers", "X-Requested-With")
////                    .addHeader("Vary", "Accept-Encoding")
//                                            .addHeader("Authorization", MyApplication.getToken())
//                                            .build();
//                                    return chain.proceed(request);
//                                }
//                            })
                            .connectTimeout(15000, TimeUnit.MILLISECONDS)
                            .readTimeout(15000, TimeUnit.MILLISECONDS)
                            .writeTimeout(15000, TimeUnit.MILLISECONDS)
                            .build();
                }
            }
        }
        return client;
    }

    public static GankApi getGankApi() {
        if (gankApi == null) {
            synchronized (GankApi.class) {
                if (gankApi == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .client(getClient())
                            .baseUrl("http://gank.io/api/")
                            //无响应体时
                            .addConverterFactory(NobodyConverterFactory.create())
                            //增加返回值为Gson的支持(以实体类返回) 如果要自定义解析，添加返回string的支持
                            .addConverterFactory(GsonConverterFactory.create())
                            //增加返回值为Oservable<T>的支持
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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
                            .client(getClient())
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
