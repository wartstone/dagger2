package com.vertical.app.network;

import com.vertical.app.bean.MessageBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by katedshan on 17/9/3.
 */

public class CatApis {

    String HOST = String.format("http://127.0.0.1/getUsers");

    @FormUrlEncoded
    @POST("JtalkManager/resteasy/Other/getImRecords/1")
    Observable<List<MessageBean>> fetchHistoryData(@Field("pk") String pk, @Field("count") int count, @Field("messageId") String messageId, @Field("username") String username);
}
