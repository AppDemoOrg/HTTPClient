
/**
 * @author 黄卫旗
 * @description RetrofitConfig
 * @time 2018/09/07
 */
public class RetrofitConfig {

    public static final String CACHE_NAME  = "retrofit_cache";
    public static final int CACHE_SIZE = 1024 * 1024 * 50; // 50M
    public static final int CACHE_TIME_OUT = 60 * 60 * 24 * 28; // 4周

    public static final int CONNECT_TIME_OUT = 15; // 15s
    public static final int READ_TIME_OUT = 20; // 20s
    public static final int WRITE_TIME_OUT = 20; // 20s

}


