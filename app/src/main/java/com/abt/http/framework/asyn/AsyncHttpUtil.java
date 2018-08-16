package com.abt.http.framework.asyn;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @描述： @AsyncHttpUtil
 * @作者： @黄卫旗
 * @创建时间： @16/05/2018
 */
public class AsyncHttpUtil {
    private static AsyncHttpUtil instance;

    private AsyncHttpClient asyncHttpClient;

    public static AsyncHttpUtil getInstance() {
        if (instance == null) {
            synchronized (AsyncHttpUtil.class) {
                instance = new AsyncHttpUtil();
            }
        }
        return instance;
    }

    private AsyncHttpUtil() {
        asyncHttpClient = new AsyncHttpClient();
    }

    /**
     * 发送Get 请求
     * @param context  用于取消http请求的凭证
     * @param url
     * @param callback 异步请求回调接口
     */
    public void sendGetRequest(Context context, String url, AsyncHttpResponseHandler callback) {
        asyncHttpClient.get(context, url, callback);
    }

    /**
     * 发送POST 请求
     * @param context  用于取消http请求的凭证
     * @param url
     * @param params   请求参数回对象
     * @param callback 异步请求回调接口
     */
    public void sendPostRequest(Context context, String url, RequestParams params, AsyncHttpResponseHandler callback) {
        asyncHttpClient.post(context, url, params, callback);
    }

    /**
     * 取消http 请求
     * @param context 取消该上下文中的所有请求
     */
    public void cancelRequest(Context context) {
        asyncHttpClient.cancelRequests(context, true);
    }
}
