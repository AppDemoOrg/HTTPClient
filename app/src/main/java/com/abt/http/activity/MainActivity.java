package com.abt.http.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.abt.http.R;
import com.abt.http.strategy.Strategy;
import com.abt.http.strategy.StrategyContext;
import com.abt.http.viewmodel.HTTPViewModel;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @描述： @MainActivity
 * @作者： @黄卫旗
 * @创建时间： @16/05/2018
 */
public class MainActivity extends AppCompatActivity implements
        RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rg_http)
    RadioGroup rgHttp;
    @BindView(R.id.ok_http)
    RadioButton okHttp;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @OnClick(R.id.btn_get) void doGet() {
        getNewsList(true);
    }
    @OnClick(R.id.btn_post) void doPost() {
        getNewsList(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        HTTPViewModel.getInstance().setContext(this);
        HTTPViewModel.getInstance().setResultView(tvResult);
        rgHttp.setOnCheckedChangeListener(this);
        okHttp.setChecked(true);
    }

    /**
     * 获取新闻列表
     * @param get
     */
    private void getNewsList(boolean get) {
        tvResult.setText("");
        int httpType = rgHttp.getCheckedRadioButtonId();
        StrategyContext context = new StrategyContext();
        Strategy strategy = context.getStrategy(httpType);
        strategy.doHttp(get);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Logger.d("onCheckedChanged, checkId="+checkedId);
    }
}
