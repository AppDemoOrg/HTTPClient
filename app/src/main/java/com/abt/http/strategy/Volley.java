package com.abt.http.strategy;

import android.util.Log;

import com.abt.http.HttpApp;
import com.abt.http.global.GlobalConstant;
import com.abt.http.viewmodel.HTTPViewModel;
import com.abt.http.framework.volley.VolleyCallback;
import com.abt.http.framework.volley.VolleyRequestParams;
import com.abt.http.framework.volley.VolleyUtil;
import com.android.volley.VolleyError;

/**
 * @描述： @volley
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class Volley extends Strategy {

    @Override
    public void doHttp(boolean get) {
        HTTPViewModel.getInstance().showLoadingDialog();
        if (get) {
            VolleyUtil.getInstance(HttpApp.getAppContext()).sendGetRequest(HttpApp.getAppContext(), GlobalConstant.API_GET, volleyCallback());
        } else {
            VolleyRequestParams params = new VolleyRequestParams();
            params.put("key", GlobalConstant.API_KEY);
            params.put("q", "\"\"");
            VolleyUtil.getInstance(HttpApp.getAppContext()).sendPostRequest(HttpApp.getAppContext(), GlobalConstant.API, params, volleyCallback());
        }
    }

    /**
     * 获取Volley异步请求接口回调
     * @return
     */
    private VolleyCallback<String> volleyCallback() {
        return new VolleyCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                HTTPViewModel.getInstance().closeLoadingDialog();
                HTTPViewModel.getInstance().setResult(false, error.getMessage());
            }

            @Override
            public void onResponse(Object response) {
                HTTPViewModel.getInstance().closeLoadingDialog();
                Log.d("", response.toString());
                HTTPViewModel.getInstance().setResult(true, response.toString());
            }
        };
    }

}
