package com.abt.http.framework.okhttp;

import okhttp3.FormBody;

/**
 * @描述： @OKHttp 自封装请求参数对象
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class OkRequestParams {

    private FormBody.Builder builder;

    public OkRequestParams() {
        builder = new FormBody.Builder();
    }

    public void put(String key, String value) {
        builder.add(key, value);
    }

    public FormBody toParams() {
        return builder.build();
    }
}
