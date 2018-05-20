package com.abt.http.framework.okhttp;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * @描述： @News
 * @作者： @黄卫旗
 * @创建时间： @16/05/2018
 */
public class HttpException {

    public static int EXCEPTION_DATA = -1;
    private int code;
    private Exception e;

    public HttpException() { }

    public HttpException(int code) {
        this.code = code;
    }

    public HttpException(Exception e) {
        this.code = 0;
        this.e = e;
    }

    public HttpException(int code, String eMsg) {
        this.code = code;
        this.e = new Exception(eMsg);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        if (this.code == 0) {
            if (e instanceof ConnectException) {
                return "无法连接服务器，请检查网络设置";
            } else if (e instanceof SocketTimeoutException) {
                return "服务器连接超时，请稍后再试";
            }
            return "网络异常，请检查网络设置";
        } else if (this.code == -1) {
            return "数据异常，请稍后重试";
        } else if (code >= 200 && code < 300) {
            return "请求异常，请稍后重试";
        } else {
            return "服务器异常";
        }
    }
}
