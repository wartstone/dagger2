package com.vertical.app.network;

import android.text.TextUtils;

import com.vertical.app.common.Constant;
import com.vertical.app.common.util.PreferenceHelper;
import com.vertical.app.common.util.Trace;
import com.vertical.app.core.CatApp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    private Retrofit mRetrofit, mIMRetrofit, mNonTokenRetrofit;
    private static OkHttpClient mTokenClient, mNonTokenClient;
    private static HttpLoggingInterceptor mLogInterceptor;

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

    public <T> T createNonTokenRetrofit(Class<T> tClass){
        return createNonTokenRetrofit().create(tClass);
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

    public Retrofit createNonTokenRetrofit() {
        if(mNonTokenRetrofit == null){
            mNonTokenRetrofit = new Retrofit.Builder()
                    .baseUrl(CatApis.HOST)
                    .client(getNonTokenClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mNonTokenRetrofit;
    }

    private OkHttpClient getClient() {
        if (mTokenClient == null) {
            mLogInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Trace.d("HttpLog:", message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);

            final String token = PreferenceHelper.getInstance(CatApp.getAppContext()).getString(Constant.KEY_USERTOKEN, "");
            if(TextUtils.isEmpty(token)) {
                Trace.e(TAG, "token is null");
                return null;
            }

            Interceptor networkInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request request = originalRequest.newBuilder()
                            .method(originalRequest.method(), originalRequest.body())
                            .header("Authorization", token)
                            .build();
                    Trace.d("header:" + request.headers());
                    return chain.proceed(request);
                }
            };

            mTokenClient = new OkHttpClient.Builder()
                    //log 拦截器
                    .addInterceptor(mLogInterceptor)
                    .addNetworkInterceptor(networkInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();
        }

        return mTokenClient;
    }

    private OkHttpClient getNonTokenClient() {
        if (mNonTokenClient == null) {
            mLogInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Trace.d("HttpLog:", message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);

            mNonTokenClient = new OkHttpClient.Builder()
                    //log 拦截器
                    .addInterceptor(mLogInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();
        }

        return mNonTokenClient;
    }
}
