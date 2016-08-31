package com.wy.rxjavademo;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wy on 2016/8/29.
 */
public interface RequestSerives {

    @POST("list.from")
    Call<String> getNewInfo(@Query("key") String key, @Query("page") String page, @Query("pagesize") String pagesize, @Query("sort") String sort, @Query("time") String time);

    @POST("list.from")
    Observable<Object> getTopMovie(@Query("key") String key, @Query("page") String page, @Query("pagesize") String pagesize, @Query("sort") String sort, @Query("time") String time);

}
