package com.abt.http.strategy;

import com.abt.http.bean.News;
import com.abt.http.bean.Result;
import com.abt.http.global.GlobalConstant;
import com.abt.http.framework.retrofit.RetrofitService;
import com.abt.http.framework.retrofit.RetrofitWrapper;
import com.abt.http.viewmodel.HTTPViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * @描述： @Retrofit
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class Retrofit extends Strategy {

    @Override
    public void doHttp(boolean get) {
        HTTPViewModel.getInstance().showLoadingDialog();
        // 通过call 发起请求和取消请求
        retrofit2.Call<Result<List<News>>> call;
        if (get) {
            call = RetrofitWrapper.getInstance().createService(RetrofitService.class).getNewsListByGet(GlobalConstant.API_KEY, "\"\"");
        } else {
            call = RetrofitWrapper.getInstance().createService(RetrofitService.class).getNewsListByPost(GlobalConstant.API_KEY, "\"\"");
        }
        RetrofitWrapper.getInstance().sendRequest(call, retrofitCallback());
    }

    /**
     * 获取Retrofit 异步回掉接口
     * @return
     */
    private Callback<Result<List<News>>> retrofitCallback() {
        return new Callback<Result<List<News>>>() {
            @Override
            public void onResponse(retrofit2.Call<Result<List<News>>> call, Response<Result<List<News>>> response) {
                HTTPViewModel.getInstance().closeLoadingDialog();

                if (response.isSuccessful()) {
                    //注意这里用第一个Response参数的
                    Result<List<News>> result = response.body();
                    if (result.getError_code() == 0) {
                        List<News> list = result.getResult();
                        if (list != null && !list.isEmpty()) {
                            StringBuffer sb = new StringBuffer();
                            for (News news : list) {
                                sb.append(news.getFull_title() + "\n");
                            }
                            HTTPViewModel.getInstance().setResult(true, sb.toString());
                        }
                    } else {
                        HTTPViewModel.getInstance().setResult(true, result.getReason());
                    }
                } else {
                    try {
                        HTTPViewModel.getInstance().setResult(false, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Result<List<News>>> call, Throwable t) {
                HTTPViewModel.getInstance().closeLoadingDialog();
                HTTPViewModel.getInstance().setResult(false, t.getMessage());
            }
        };
    }

}
