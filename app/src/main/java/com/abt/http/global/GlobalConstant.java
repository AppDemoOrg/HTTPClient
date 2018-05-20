package com.abt.http.global;

/**
 * @描述： @GlobalConstant
 * @作者： @黄卫旗
 * @创建时间： @20/05/2018
 */
public class GlobalConstant {

    /**
     * q	string	是	需要检索的关键字,请UTF8 URLENCODE
     * key	string	是	应用APPKEY(应用详细页查询)
     * dtype	string	否	返回数据的格式,xml或json，默认json
     */
    public static final String API = "http://op.juhe.cn/onebox/news/query";
    public static final String API_GET = "http://op.juhe.cn/onebox/news/query?key=5173fa20d74cf85747dcf6f4636856af&q=\"\"";

    public static String RETROFIT_BASEURL = "http://op.juhe.cn/"; // 服务器地址
    public static String RETROFIT_APIKEY = "5173fa20d74cf85747dcf6f4636856af";

    public static final String OKHTTP_APIKEY = "5173fa20d74cf85747dcf6f4636856af";
}
