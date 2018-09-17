
# HTTPClient             

## Cookies          
1、[快速理解 session/token/cookie 认证方式](https://blog.csdn.net/Jmilk/article/details/55686267?locationNum=9&fps=1)       

## SSL/TLS TODO        
1、[SSL与TLS的区别](https://blog.csdn.net/bjbz_cxy/article/details/77850182)             
2、[详解SSL/TLS](http://www.mamicode.com/info-detail-1846390.html)       

## HTTPS      
1、[白话图解HTTPS原理](https://www.cnblogs.com/ghjbk/p/6738069.html)      
2、[Android HTTPS如何10分钟实现自签名SSL证书](https://www.cnblogs.com/tommylemon/p/5454303.html)     
3、[也许，这样理解HTTPS更容易](http://showme.codes/2017-02-20/understand-https/)    
4、[OpenSSL 1.0.0生成p12、jks、crt等格式证书的命令个过程(转)](https://www.cnblogs.com/bluestorm/archive/2013/06/26/3155945.html)      

## OKHTTP TODO：      
1、OKHTTP同步异步实现原理               
2、OKHTTP框架设计                

## OKHTTP 参考文献      
1、[OkHttp中https的使用](https://www.jianshu.com/p/1373889e74b2)          
2、[Android Https相关完全解析当OkHttp遇到Https](https://blog.csdn.net/lmj623565791/article/details/48129405)    
3、[hongyangAndroid/okhttputils](https://github.com/hongyangAndroid/okhttputils)           


## RETORFIT      
### AndroidStudio插件 GsonFormat          
利用AndroidStudio插件 GsonFormat 快速，方便的将json数据转为Java 对象        

### RETORFIT 实现步骤：      
1、定义API接口CloudApi和相应的JavaBean           
2、定义RetrofitWrapper初始化Retrofit             
3、CloudApi apis =  RetrofitWrapper.getInstance().create(CloudApi.class);       
   Call<LoginResultBean> callLogin = apis.userLogin(username, password);            
   RetrofitWrapper.getInstance().sendRequest(callLogin, new Callback<LoginResultBean>()          
     
### RETORFIT 参考资料       
1、[Android 网络请求库Retrofit简单使用](https://blog.csdn.net/u011974987/article/details/50895633)     
2、[Retrofit配置详解及封装，让你的网络请求更简单](https://www.jianshu.com/p/7a4b3766f86d)          
3、[Retrofit各个注解的含义及作用](https://blog.csdn.net/qiang_xi/article/details/53959437)       


### RxJava - TODO         
1、[（一）Rxjava2+Retrofit完美封装](https://blog.csdn.net/qq_20521573/article/details/70991850)              
2、           

## RxJava 参考文献             
1、[搭建自己的框架之1：Rxjava2+Retrofit2 实现Android Http请求](https://www.jianshu.com/p/04ce0c91e3ee)                   
2、[非常重要：Rxjava2+retrofit2封装 post 固定json参数](https://www.jianshu.com/p/9df6c7e3c39f)               


## 参考项目               
1、[HttpDemo](https://github.com/yxdroid/HttpDemo)                            
2、[AndroidHttpsDemo](https://github.com/Frank-Zhu/AndroidHttpsDemo)               
3、[AndroidNetworkDemo](https://github.com/dodocat/AndroidNetworkDemo)             

