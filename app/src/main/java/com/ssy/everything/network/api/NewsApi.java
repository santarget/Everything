package com.ssy.everything.network.api;

import com.ssy.everything.bean.response.NewsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ssy on 2017/6/6.
 */

public interface NewsApi {

    @GET("toutiao/index" )
    Observable<NewsResponse> getNews(@Query("type") String type, @Query("key") String appKey);

}
