package com.abt.http.viewmodel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.TextView;

/**
 * @描述： @HTTPViewModel
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class HTTPViewModel {

    private TextView tvResult;
    private Activity activity;
    private ProgressDialog loadingDialog;

    private HTTPViewModel() { }

    private static class InnerClass {
        private static final HTTPViewModel INSTANCE = new HTTPViewModel();
    }

    public static final HTTPViewModel getInstance() {
        return HTTPViewModel.InnerClass.INSTANCE;
    }

    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(activity);
            loadingDialog.setTitle("loading...");
        }
        loadingDialog.show();
    }

    public void closeLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public void setContext(Activity activity) {
        this.activity = activity;
    }

    public void setResultView(TextView tv) {
        tvResult = tv;
    }

    public void setResult(boolean success, String result) {
        if (success) {
            tvResult.setText("请求结果\n-------------------------------------------\n" + result);
        } else {
            tvResult.setText("请求异常\n-----------------------\n" + result);
        }
    }

}
