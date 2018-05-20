package com.abt.http.strategy;

import com.abt.http.HttpApp;
import com.abt.http.framework.asyn.AsynHttpUtil;
import com.abt.http.global.GlobalConstant;
import com.abt.http.viewmodel.HTTPViewModel;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * @描述： @
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class AsynHttp extends Strategy {

    @Override
    public void doHttp(boolean get) {
        if (get) {
            AsynHttpUtil.getInstance().sendGetRequest(HttpApp.getAppContext(), GlobalConstant.API_GET, asynCallback());
        } else {
            RequestParams params = new RequestParams();
            params.put("key", "5173fa20d74cf85747dcf6f4636856af");
            params.put("q", "\"\"");
            AsynHttpUtil.getInstance().sendPostRequest(HttpApp.getAppContext(), GlobalConstant.API, params, asynCallback());
        }
    }

    /**
     * 获取Android Asynchronous Http Client 异步请求回调接口
     * 也可返回 JsonHttpResponseHandler 接口回调 自动将响应结果解析为json格式
     *
     * @return
     */
    private AsyncHttpResponseHandler asynCallback() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                HTTPViewModel.getInstance().showLoadingDialog();
            }

            @Override
            public void onFinish() {
                HTTPViewModel.getInstance().closeLoadingDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                HTTPViewModel.getInstance().setResult(true, new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                HTTPViewModel.getInstance().setResult(false, error.getMessage());
            }
        };
    }
}
