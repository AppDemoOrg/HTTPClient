package com.abt.http.strategy;

import com.abt.http.bean.News;
import com.abt.http.bean.Result;
import com.abt.http.framework.retrofit.RetrofitService;
import com.abt.http.framework.retrofit.RetrofitWrapper;
import com.abt.http.global.GlobalConstant;
import com.abt.http.viewmodel.HTTPViewModel;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @描述： @RxJavaRetrofit
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class RxJavaRetrofit extends Strategy {

    @Override
    public void doHttp(boolean get) {
        RetrofitWrapper.getInstance().createService(RetrofitService.class).getNewsList(GlobalConstant.API_KEY, "\"\"")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        HTTPViewModel.getInstance().showLoadingDialog();
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
                            HTTPViewModel.getInstance().setResult(false, "token 过期");
                            return null;
                        } else {
                            return result;
                        }
                    }
                })
                .subscribe(new Subscriber<Result<List<News>>>() {
                    @Override
                    public void onCompleted() {
                        HTTPViewModel.getInstance().closeLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        HTTPViewModel.getInstance().closeLoadingDialog();
                        HTTPViewModel.getInstance().setResult(false, e.toString());
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
                                HTTPViewModel.getInstance().setResult(true, sb.toString());
                            }
                        } else {
                            HTTPViewModel.getInstance().setResult(true, result.getReason());
                        }
                    }
                });
    }

}
