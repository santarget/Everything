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
//top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)

    @GET("toutiao/index" )
    Observable<NewsResponse> getNews(@Query("type") String type, @Query("key") String appKey);


}
