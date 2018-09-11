package com.abt.http.framework.retrofit;

import com.abt.http.global.GlobalConstant;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 黄卫旗
 * @description RetrofitWrapper
 * @time 2018/09/07
 */
public class RetrofitWrapper {

    private Retrofit retrofit;

    private static class InnerClass {
        private static RetrofitWrapper sInstance = new RetrofitWrapper();
    }

    private RetrofitWrapper() {
        retrofit = new Retrofit.Builder()
                // 接口基地址
                .baseUrl(URLConstant.SERVER_DOMAIN)
                // 添加格式转换器
                .addConverterFactory(GsonConverterFactory.create())
                // 添加RxJava适配模式
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static RetrofitWrapper getInstance() {
        return InnerClass.sInstance;
    }

    /**
     * 创建请求服务接口
     * @param clazz
     * @param <T>
     */
    public <T> T create(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    /**
     * 发起请求
     * @param call
     * @param callback
     */
    public void sendRequest(Call call, retrofit2.Callback callback) {
        call.enqueue(callback);
    }

    /**
     * 传入请求时的call对象
     * 取消请求
     * @param call
     */
    public void cancelRequest(Call call) {
        call.cancel();
    }

}


