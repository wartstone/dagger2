package com.vertical.app.network;

import com.vertical.app.bean.MessageBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by katedshan on 17/9/3.
 */

public interface CatApis {

    String HOST = String.format("http://127.0.0.1");

    @GET("greeting")
    Observable<MessageBean> fetchData();
}
