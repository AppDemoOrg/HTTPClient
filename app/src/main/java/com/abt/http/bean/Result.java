package com.abt.http.bean;

/**
 * @描述： @Result
 * @作者： @黄卫旗
 * @创建时间： @16/05/2018
 */
public class Result<T> {
    public static final int OK_CODE = 0;
    public static final int TOKEN_CODE = -1; // token 过期
    private String reason;
    private T result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
