package com.abt.http.strategy;

/**
 * @描述： @StrategyContext
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class StrategyContext {

    Strategy strategy;

    public StrategyContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public void doHttp(boolean get) {
        strategy.doHttp(get);
    }
}
