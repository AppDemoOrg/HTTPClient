package com.abt.http.framework.okhttp;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * @描述： @HttpRequestCallback
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public abstract class HttpRequestCallback<T> {
    public Type type;

    public HttpRequestCallback() {
        type = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter");
        }
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    /**
     * 开始请求
     */
    public abstract void onStart();

    /**
     * 请求结束
     */
    public abstract void onFinish();

    /**
     * 响应回调
     *
     * @param t
     */
    public abstract void onResponse(T t);

    /**
     * 请求异常错误回调
     *
     * @param call
     * @param e
     */
    public abstract void onFailure(Call call, HttpException e);
}
