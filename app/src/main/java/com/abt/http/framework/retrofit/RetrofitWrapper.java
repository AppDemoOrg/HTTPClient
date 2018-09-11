package com.abt.http.framework.retrofit;

import com.abt.http.global.GlobalConstant;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
        initRetrofit();
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

    private void initRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // log用拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        // 设置缓存目录
        File cacheFile = new File(LatestApp.getAppContext().getExternalCacheDir(), RetrofitConfig.CACHE_NAME);
        // 生成缓存50M
        Cache cache = new Cache(cacheFile, RetrofitConfig.CACHE_SIZE);
        // 缓存拦截器
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // 网络不可用
                if (!NetworkUtil.isAvailable(LatestApp.getAppContext())) {
                    // 在请求头中加入，强制使用缓存，不访问网络
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                // 网络可用
                if (NetworkUtil.isAvailable(LatestApp.getAppContext())) {
                    int maxAge = 0;
                    // 有网络时 在响应头中加入，设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("pragma")
                            .build();
                } else {
                    // 无网络时，在响应头中加入：设置超时为4周
                    int maxStale = RetrofitConfig.CONNECT_TIME_OUT;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("pragma")
                            .build();
                }
                return response;
            }
        };

        builder.addInterceptor(loggingInterceptor)
                .addInterceptor(cacheInterceptor)
                .cache(cache)
                .connectTimeout(RetrofitConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)// 设置超时
                .readTimeout(RetrofitConfig.READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(RetrofitConfig.WRITE_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true); // 错误重连
        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                // 接口基地址
                .baseUrl(URLConstant.SERVER_DOMAIN)
                // 添加JSON格式转换器
                .addConverterFactory(GsonConverterFactory.create())
                // 添加RxJava适配模式
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

}



