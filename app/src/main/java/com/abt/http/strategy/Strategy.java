package com.abt.http.strategy;

/**
 * @描述： @抽象的网络请求策略类，定义所有支持的网络请求公共接口
 * @作者： @黄卫旗
 * @创建时间： @17/05/2018
 */
abstract class Strategy {

    public abstract void doHttp(boolean get);

}
