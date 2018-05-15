package com.abt.http.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.abt.http.bean.FamousInfo;
import com.abt.http.model.FamousInfoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @描述： @ClientActivity
 * @作者： @黄卫旗
 * @创建时间： @16/05/2018
 */
public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void handleNet() {
        FamousInfoModel famousInfoModel =FamousInfoModel.getInstance(getApplicationContext());
        // 获取事件
        private void initEvent() {
            mSerachBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //创建访问的API请求
                    Call<FamousInfo> callFamous= famousInfoModel.queryLookUp(initParams());
                    //发送请求
                    callFamous.enqueue(new Callback<FamousInfo>() {
                        @Override
                        public void onResponse(Call<FamousInfo> call, Response<FamousInfo> response) {
                            if(response.isSuccess()){
                                FamousInfo result = response.body();
                                if(result!=null){
                                    List<FamousInfo.ResultEntity> entity = result.getResult();
                                    mTxtContent.setText("1、"+entity.get(0).getFamous_saying()+"\n---"+entity.get(0).getFamous_name()+"\n 2、"
                                            +entity.get(1).getFamous_saying()+"\n---"+entity.get(1).getFamous_name());
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<FamousInfo> call, Throwable t) {

                        }
                    });
                }
            });
        }
    }

}
