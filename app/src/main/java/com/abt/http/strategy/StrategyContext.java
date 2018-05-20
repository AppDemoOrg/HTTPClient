package com.abt.http.strategy;

/**
 * @描述： @StrategyContext
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class StrategyContext {

    private Strategy strategy;

    public StrategyContext() {

    }

    public Strategy getStrategy(int httpType) {
        if (httpType<0 || httpType>5) httpType = 1;
        switch (httpType) {
            case 1:
                strategy = new OKHttp();
                break;
            case 2:
                strategy = new Volley();
                break;
            case 3:
                strategy = new Retrofit();
                break;
            case 4:
                strategy = new RxJavaRetrofit();
                break;
            case 5:
                strategy = new AsynHttp();
                break;
        }
        return strategy;
    }

    public StrategyContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public void doHttp(boolean get) {
        strategy.doHttp(get);
    }
}
