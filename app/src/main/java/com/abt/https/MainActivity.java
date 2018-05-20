package com.abt.https;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.abt.http.R;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @描述： @MainActivity
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class MainActivity extends AppCompatActivity {

    private String GET_URL = "http://op.juhe.cn/"; // 你服务器的接口地址
    private String POS_URL = "http://op.juhe.cn/"; //你服务器的接口地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https);
        findViewById(R.id.tv_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        getTest();
                        postTest();
                    }
                }).start();
            }
        });
    }

    private void postTest() {
        try {
            String json = "Post的数据";
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = HttpClientSslHelper.getSslOkHttpClient(MainActivity.this);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(POS_URL)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Logger.d(response.body().string());
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getTest() {
        try {
            Request request = new Request.Builder().url(GET_URL).build();
            Response response = HttpClientSslHelper.getSslOkHttpClient(MainActivity.this).newCall(request).execute();
            if (response.isSuccessful()) {
                Log.d("MainActivity", response.body().string());
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
