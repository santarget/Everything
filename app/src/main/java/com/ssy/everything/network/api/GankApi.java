package com.ssy.everything.network.api;

import com.ssy.everything.bean.GankResult;
import com.ssy.everything.bean.NewsInfo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * gank.io 接口
 */

public interface GankApi {

    @GET("data/{category}/{number}/{page}")
    Observable<GankResult> getCategoryDate(@Path("category") String category, @Path("number") int number, @Path("page") int page);

    @GET("random/data/福利/{number}")
    Observable<GankResult> getRandomBeauties(@Path("number") int number);

    @GET("search/query/{key}/category/all/count/{count}/page/{page}")
    Observable<NewsInfo> getSearchResult(@Path("key") String key, @Path("count") int count, @Path("page") int page);

    @POST("mobileLogin/submit.html")
    Call<String> getString(@Query("loginname") String loginname, @Query("nloginpwd") String loginpwd);

    @GET("book/search")
    Call<NewsInfo> getSearchBooks(@Query("q") String name, @Query("tag") String tag, @Query("start") int start,
                                  @Query("count") int count);

    @GET("/txapi/weixin/wxhot")
    Call<String> getHotArticleStr(@Header("apikey") String apiKey, @Query("word") String word,
                                  @Query("src") String src, @QueryMap HashMap map);

}
