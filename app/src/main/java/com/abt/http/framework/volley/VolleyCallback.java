package com.abt.http.framework.volley;

import com.android.volley.Response;

/**
 * @描述： @重新封装 VolleyCallback 接口回调
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public interface VolleyCallback<T> extends Response.Listener<T>, Response.ErrorListener {

}
