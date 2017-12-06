package com.vertical.app.bean;

import java.io.Serializable;

/**
 * Created by ls on 12/6/17.
 */

public class BaseBeanEx<T> implements Serializable {
    private int status;
    private T result;

    public boolean isSuccess(){
        return status == 0;
    }

    public T getData() {
        return result;
    }
}