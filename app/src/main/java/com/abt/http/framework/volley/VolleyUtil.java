package com.abt.http.framework.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * @描述： @VolleyUtil
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class VolleyUtil {

    private static VolleyUtil instance;

    private RequestQueue requestQueue;

    public static synchronized VolleyUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (VolleyUtil.class) {
                instance = new VolleyUtil(context);
            }
        }
        return instance;
    }

    private VolleyUtil(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /**
     * Get 请求
     * @param context
     * @param url
     * @param callback
     */
    public void sendGetRequest(Context context, String url, VolleyCallback callback) {
        StringRequest req = new StringRequest(Request.Method.GET, url, callback, callback);
        req.setTag(context);
        requestQueue.add(req);
    }

    /**
     * 发送Post请求
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    public void sendPostRequest(Context context, String url, final VolleyRequestParams params, VolleyCallback callback) {
        StringRequest req = new StringRequest(Request.Method.POST, url, callback, callback) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        req.setTag(context);
        requestQueue.add(req);
    }

    /**
     * 取消该tag下的请求
     * @param context
     */
    public void cancelRequest(Context context) {
        requestQueue.cancelAll(context);
    }
}
