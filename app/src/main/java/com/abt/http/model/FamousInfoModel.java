package com.abt.http.model;

import android.content.Context;

import com.abt.http.bean.FamousInfo;
import com.abt.http.retrofit.RetrofitWrapper;

import retrofit2.Call;

/**
 * @描述： @FamousInfoModel
 * @作者： @黄卫旗
 * @创建时间： @16/05/2018
 */
public class FamousInfoModel {
    private static FamousInfoModel famousInfoModel;
    private FamousService mFamousService;

    /**
     * 单例模式
     *
     * @return
     */
    public static FamousInfoModel getInstance(Context context) {
        if (famousInfoModel == null) {
            famousInfoModel = new FamousInfoModel(context.getApplicationContext());
        }
        return famousInfoModel;
    }


    private FamousInfoModel(Context context) {
        mFamousService = (FamousService) RetrofitWrapper.getInstance().create(FamousService.class);
    }

    /**
     * 查询名人名言
     *
     * @param famousInfoReq
     * @return
     */
    public Call<FamousInfo> queryLookUp(FamousInfoReq famousInfoReq) {
        Call<FamousInfo> infoCall = mFamousService.getFamousResult(famousInfoReq.apiKey, famousInfoReq.keyword, famousInfoReq.page, famousInfoReq.rows);
        return infoCall;
    }
}
