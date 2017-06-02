package com.ssy.everything.network.api;

import com.ssy.everything.bean.UserInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * gank.io 接口
 */

public interface GankApi {

    @GET("data/{category}/{number}/{page}")
    Observable<UserInfo> getCategoryDate(@Path("category") String category, @Path("number") int number, @Path("page") int page);

    @GET("random/data/福利/{number}")
    Observable<UserInfo> getRandomBeauties(@Path("number") int number);

    @GET("search/query/{key}/category/all/count/{count}/page/{page}")
    Observable<UserInfo> getSearchResult(@Path("key") String key, @Path("count") int count, @Path("page") int page);

}
