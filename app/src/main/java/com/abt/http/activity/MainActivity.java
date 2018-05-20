package com.abt.http.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.abt.http.R;
import com.abt.http.asyn.AsynHttpUtil;
import com.abt.http.bean.News;
import com.abt.http.bean.Result;
import com.abt.http.global.GlobalConstant;
import com.abt.http.okhttp.HttpException;
import com.abt.http.okhttp.HttpRequestCallback;
import com.abt.http.okhttp.OkHttpUtil;
import com.abt.http.okhttp.OkRequestParams;
import com.abt.http.retrofit.RetrofitService;
import com.abt.http.retrofit.RetrofitWrapper;
import com.abt.http.strategy.Retrofit;
import com.abt.http.strategy.StrategyContext;
import com.abt.http.viewmodel.HTTPViewModel;
import com.abt.http.volley.VolleyCallback;
import com.abt.http.volley.VolleyRequestParams;
import com.abt.http.volley.VolleyUtil;
import com.android.volley.VolleyError;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @描述： @MainActivity
 * @作者： @黄卫旗
 * @创建时间： @16/05/2018
 */
public class MainActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener {

    private RadioGroup rgHttp;
    private TextView tvResult;
    private ProgressDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgHttp = (RadioGroup) findViewById(R.id.rg_http);
        tvResult = (TextView) findViewById(R.id.tv_result);
        rgHttp.setOnCheckedChangeListener(this);

        HTTPViewModel.getInstance().setContext(this);
        HTTPViewModel.getInstance().setResultView(tvResult);
    }

    /**
     * 获取新闻列表
     * @param get
     */
    private void getNewsList(boolean get) {
        tvResult.setText("");
        int httpType = rgHttp.getCheckedRadioButtonId();
        switch (httpType) {
            // OKHttp
            case 1:
                if (get) {
                    OkHttpUtil.getInstance().sendGetRequest(this, GlobalConstant.API_GET, okHttpCallback());
                } else {
                    OkRequestParams params = new OkRequestParams();
                    params.put("key", "5173fa20d74cf85747dcf6f4636856af");
                    params.put("q", "\"\"");
                    OkHttpUtil.getInstance().sendPostRequest(this, GlobalConstant.API, params, okHttpCallback());
                }
                break;
            // Volley
            case 2:
                showLoadingDialog();
                if (get) {
                    VolleyUtil.getInstance(this).sendGetRequest(this, GlobalConstant.API_GET, volleyCallback());
                } else {
                    VolleyRequestParams params = new VolleyRequestParams();
                    params.put("key", "5173fa20d74cf85747dcf6f4636856af");
                    params.put("q", "\"\"");
                    VolleyUtil.getInstance(this).sendPostRequest(this, GlobalConstant.API, params, volleyCallback());
                }
                break;
            // Retrofit
            case 3:
                /*showLoadingDialog();
                // 通过call 发起请求和取消请求
                retrofit2.Call<Result<List<News>>> call;
                if (get) {
                    call = RetrofitWrapper.getInstance().createService(RetrofitService.class).getNewsListByGet(GlobalConstant.RETROFIT_APIKEY, "\"\"");
                } else {
                    call = RetrofitWrapper.getInstance().createService(RetrofitService.class).getNewsListByPost(GlobalConstant.RETROFIT_APIKEY, "\"\"");
                }
                RetrofitWrapper.getInstance().sendRequest(call, retrofitCallback());*/

                StrategyContext context = new StrategyContext(new Retrofit());
                context.doHttp(get);
                break;
            // RxJava + Retrofit
            case 4:
                RetrofitWrapper.getInstance().createService(RetrofitService.class).getNewsList("5173fa20d74cf85747dcf6f4636856af", "\"\"")
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                showLoadingDialog();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Func1<Result<List<News>>, Result<List<News>>>() {
                            @Override
                            public Result<List<News>> call(Result<List<News>> result) {
                                if (Result.TOKEN_CODE == result.getError_code()) {
                                    // 跳转到用户登录页面
                                    //startActivity(new Intent(AppManager.getInstance().currentActivity(), LoginActivity.class));
                                    setResult(false, "token 过期");
                                    return null;
                                } else {
                                    return result;
                                }
                            }
                        })
                        .subscribe(new Subscriber<Result<List<News>>>() {
                            @Override
                            public void onCompleted() {
                                closeLoadingDialog();
                            }

                            @Override
                            public void onError(Throwable e) {
                                closeLoadingDialog();
                                setResult(false, e.toString());
                            }

                            @Override
                            public void onNext(Result<List<News>> result) {
                                if (result == null) {
                                    return;
                                }

                                if (result.getError_code() == 0) {
                                    List<News> list = result.getResult();
                                    if (list != null && !list.isEmpty()) {
                                        StringBuffer sb = new StringBuffer();
                                        for (News news : list) {
                                            sb.append(news.getFull_title() + "\n");
                                        }
                                        setResult(true, sb.toString());
                                    }
                                } else {
                                    setResult(true, result.getReason());
                                }
                            }
                        });
                break;
            // Android Asynchronous Http Client
            case 5:
                if (get) {
                    AsynHttpUtil.getInstance().sendGetRequest(this, GlobalConstant.API_GET, asynCallback());
                } else {
                    RequestParams params = new RequestParams();
                    params.put("key", "5173fa20d74cf85747dcf6f4636856af");
                    params.put("q", "\"\"");
                    AsynHttpUtil.getInstance().sendPostRequest(this, GlobalConstant.API, params, asynCallback());
                }
                break;
        }

    }

    /**
     * 获取Retrofit 异步回掉接口
     *
     * @return
     */
    private Callback<Result<List<News>>> retrofitCallback() {
        return new Callback<Result<List<News>>>() {
            @Override
            public void onResponse(retrofit2.Call<Result<List<News>>> call, Response<Result<List<News>>> response) {
                closeLoadingDialog();

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
                            setResult(true, sb.toString());
                        }
                    } else {
                        setResult(true, result.getReason());
                    }
                } else {
                    try {
                        setResult(false, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Result<List<News>>> call, Throwable t) {
                closeLoadingDialog();
                setResult(false, t.getMessage());
            }
        };
    }

    /**
     * 获取Volley异步请求接口回调
     *
     * @return
     */
    private VolleyCallback<String> volleyCallback() {
        return new VolleyCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                closeLoadingDialog();
                setResult(false, error.getMessage());
            }

            @Override
            public void onResponse(Object response) {
                closeLoadingDialog();
                Log.d("", response.toString());
                setResult(true, response.toString());
            }
        };
    }

    /**
     * 获取OKHttp 异步请求回调接口
     * @return
     */
    private HttpRequestCallback<String> okHttpCallback() {
        return new HttpRequestCallback<String>() {
            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onFinish() {
                closeLoadingDialog();
            }

            @Override
            public void onResponse(String s) {
                setResult(true, s);
            }

            @Override
            public void onFailure(Call call, HttpException e) {
                setResult(false, e.getMessage());
            }
        };
    }

    /**
     * 获取Android Asynchronous Http Client 异步请求回调接口
     * 也可返回 JsonHttpResponseHandler 接口回调 自动将响应结果解析为json格式
     *
     * @return
     */
    private AsyncHttpResponseHandler asynCallback() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onFinish() {
                closeLoadingDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                setResult(true, new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                setResult(false, error.getMessage());
            }
        };
    }

    private void setResult(boolean success, String result) {
        if (success) {
            tvResult.setText("请求结果\n-------------------------------------------\n" + result);
        } else {
            tvResult.setText("请求异常\n-----------------------\n" + result);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                getNewsList(true);
                break;
            case R.id.btn_post:
                getNewsList(false);
                break;
        }
    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setTitle("loading...");
        }
        loadingDialog.show();
    }

    private void closeLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
