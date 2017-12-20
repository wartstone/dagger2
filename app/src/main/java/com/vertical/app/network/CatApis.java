package com.vertical.app.network;

import com.vertical.app.bean.BaseBean;
import com.vertical.app.bean.BaseBeanEx;
import com.vertical.app.bean.BaseListBean;
import com.vertical.app.bean.MemberBean;
import com.vertical.app.bean.MessageBean;
import com.vertical.app.bean.OrderBean;
import com.vertical.app.bean.UserBean;
import com.vertical.app.module.work.bizanalysis.salesquery.SaleRecordBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by katedshan on 17/9/3.
 */

public interface CatApis {

//    String HOST = String.format("http://192.168.1.4:11111");
    String HOST= String.format("http://192.168.9.81:11111");

    @GET("/greeting/")
    Observable<MessageBean> fetchData();

    @GET("users/")
    Observable<UserBean> fetchUsers();

    @GET("users/db/")
    Observable<UserBean> getUsrs();

    @FormUrlEncoded
    @POST("users/db/add")
    Observable<BaseBean> addUser(@Field("name") String name, @Field("age") int age);

    @GET("getMembers")
    Observable<MemberBean> getMembers();

    @FormUrlEncoded
    @POST("order/create")
    Observable<BaseListBean<OrderBean>> createOrder(@Field("id") long id, @Field("order_id") long order_id, @Field("product_name") String product_name,
                                                    @Field("quantity") int quantity, @Field("amount") double amount, @Field("comments") String comments);

    @POST("order/create3")
    Observable<BaseListBean<OrderBean>> createOrder(@Body OrderBean orderBean);

    @POST("member/create")
    Observable<BaseBean> createMember(@Body MemberBean memberBean);

    @GET("member/get")
    Observable<BaseListBean<MemberBean>> fetchMembers();

    @POST("user/create")
    Observable<BaseBeanEx<String>> createUser(@Body UserBean userBean);

    @POST("auth/register")
    Observable<BaseBeanEx<String>> register(@Body UserBean userBean);

    @POST("auth/login")
    Observable<BaseBeanEx<String>> login(@Body UserBean userBean);

    @POST("auth/logout")
    Observable<BaseBeanEx<String>> logout(@Body UserBean userBean);

    @GET("sale/query")
    Observable<BaseListBean<SaleRecordBean>> querySaleRecords();

}
