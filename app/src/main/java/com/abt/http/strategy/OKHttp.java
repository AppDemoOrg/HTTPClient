package com.abt.http.strategy;

import com.abt.http.HttpApp;
import com.abt.http.framework.okhttp.HttpException;
import com.abt.http.framework.okhttp.HttpRequestCallback;
import com.abt.http.framework.okhttp.OkHttpUtil;
import com.abt.http.framework.okhttp.OkRequestParams;
import com.abt.http.global.GlobalConstant;
import com.abt.http.viewmodel.HTTPViewModel;

import okhttp3.Call;

/**
 * @描述： @具体的网络请求
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class OKHttp extends Strategy {

    @Override
    public void doHttp(boolean get) {
        if (get) {
            OkHttpUtil.getInstance().sendGetRequest(HttpApp.getAppContext(), GlobalConstant.API_GET, okHttpCallback());
        } else {
            OkRequestParams params = new OkRequestParams();
            params.put("key", GlobalConstant.API_KEY);
            params.put("q", "\"\"");
            OkHttpUtil.getInstance().sendPostRequest(HttpApp.getAppContext(), GlobalConstant.API, params, okHttpCallback());
        }
    }

    /**
     * 获取OKHttp 异步请求回调接口
     * @return
     */
    private HttpRequestCallback<String> okHttpCallback() {
        return new HttpRequestCallback<String>() {
            @Override
            public void onStart() {
                HTTPViewModel.getInstance().showLoadingDialog();
            }

            @Override
            public void onFinish() {
                HTTPViewModel.getInstance().closeLoadingDialog();
            }

            @Override
            public void onResponse(String s) {
                HTTPViewModel.getInstance().setResult(true, s);
            }

            @Override
            public void onFailure(Call call, HttpException e) {
                HTTPViewModel.getInstance().setResult(false, e.getMessage());
            }
        };
    }

}
