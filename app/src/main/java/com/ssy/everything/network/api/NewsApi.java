package com.ssy.everything.network.api;

import com.ssy.everything.bean.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by ssy on 2017/6/6.
 */

public interface NewsApi {

    @GET("toutiao/index" )
    Observable<NewsResponse> getNews(@Query("type") String type, @Query("key") String appKey);

}
