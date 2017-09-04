package com.vertical.app.bean;

/**
 * Created by katedshan on 17/9/4.
 */

public class BaseBean<T> {
    private int code;
    private String message;
    private T result;

    public boolean isSuccess(){
        return code == 0;
    }

    public T getData() {
        return result;
    }
}
