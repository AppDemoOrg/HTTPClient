package com.abt.http;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @描述： @HttpApp
 * @作者： @黄卫旗
 * @创建时间： @16/05/2018
 */
public class HttpApp extends Application {

    private static HttpApp mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static final HttpApp getAppContext(){
        return mContext;
    }

}
