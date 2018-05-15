package com.abt.http.retrofit;

import android.content.Context;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * @描述： @Retrofit 网络接口服务的包装类
 * @作者： @黄卫旗
 * @创建时间： @16/05/2018
 */
public class RetrofitWrapper {

    private static RetrofitWrapper instance;
    private Context mContext;
    private Retrofit retrofit;

    private RetrofitWrapper() {
        //1.创建Retrofit对象
        retrofit = new Retrofit.Builder().baseUrl(Constant.BASEURL) // 定义访问的主机地址
                .addConverterFactory(GsonConverterFactory.create())  //解析方法
                .build();
    }

    /**
     * 单例模式
     */
    public static RetrofitWrapper getInstance() {
        if (instance == null) {
            synchronized (RetrofitWrapper.class){
                if (instance==null){
                    instance = new RetrofitWrapper();
                }
            }
        }
        return instance;
    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

}
