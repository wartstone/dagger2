package com.vertical.app.network;

import android.util.Log;

import com.henong.im.api.IMApis;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ls on 5/23/17.
 * IM的HTTP module. 优化事项: 依赖注入.
 * note: 目前retrofit仍位于biz层, 因此在IM和biz层都有retorfit的封装， 需要优化.
 */

public class HttpModule {
    private final String TAG = "HttpModule";

    private static HttpModule INSTANCE;

    private Retrofit mRetrofit, mIMRetrofit;
    private static OkHttpClient mOkHttpClient;
    private static HttpLoggingInterceptor HTTP_LOGGING_INTERCEPTOR;

    private HttpModule() {}

    public static HttpModule getSharedInstance() {
        if(INSTANCE == null) {
            INSTANCE = new HttpModule();
        }

        return INSTANCE;
    }

    public <T> T createRetrofit(Class<T> tClass){
        return createRetrofit().create(tClass);
    }

    public Retrofit createRetrofit() {
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(CatApis.HOST)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    private OkHttpClient getClient() {
        if (mOkHttpClient == null) {
            HTTP_LOGGING_INTERCEPTOR = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("Http:", message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);

            mOkHttpClient = new OkHttpClient.Builder()
                    //log 拦截器
                    .addInterceptor(HTTP_LOGGING_INTERCEPTOR)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        return mOkHttpClient;
    }
}
