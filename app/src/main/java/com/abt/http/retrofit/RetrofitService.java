package com.abt.http.retrofit;

import com.abt.http.bean.News;
import com.abt.http.bean.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @描述： @RetrofitService
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public interface RetrofitService {

    @GET("onebox/news/query")
    Call<Result<List<News>>> getNewsListByGet(@Query("key") String key, @Query("q") String q);

    @POST("onebox/news/query")
    Call<Result<List<News>>> getNewsListByPost(@Query("key") String key, @Query("q") String q);

    @POST("onebox/news/query")
    Observable<Result<List<News>>> getNewsList(@Query("key") String key, @Query("q") String q);

}
