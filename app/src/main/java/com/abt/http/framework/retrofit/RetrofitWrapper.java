package com.abt.http.framework.retrofit;

import com.abt.http.global.GlobalConstant;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @描述： @Retrofit网络请求库
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class RetrofitWrapper {

    private static RetrofitWrapper instance;

    private Retrofit retrofit;

    public static RetrofitWrapper getInstance() {
        if (instance == null) {
            synchronized (RetrofitWrapper.class) {
                instance = new RetrofitWrapper();
            }
        }
        return instance;
    }

    private RetrofitWrapper() {
        retrofit = new Retrofit.Builder()
                // 接口基地址
                .baseUrl(GlobalConstant.BASE_URL)
                // 添加格式转换器
                .addConverterFactory(GsonConverterFactory.create())
                // 添加RxJava适配模式
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 创建请求服务接口
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> clazz) {
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
