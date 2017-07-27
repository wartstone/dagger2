package com.vertical.base;


import rx.Observable;

/**
 * Created by ls on 17-5-2.
 * Mvp arch with Rxjava support.
 */

public interface BaseModel<T> {
    // 获取数据, 数据源可为网络, 数据库, SharedPreference, Cache等
    Observable<T> getData();

    // 存储数据， 数据源可为网络, 数据库, SharedPreference, Cache等
    Observable<T> saveData();
}
