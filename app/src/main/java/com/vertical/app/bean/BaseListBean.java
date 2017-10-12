package com.vertical.app.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ls on 10/11/17.
 */

public class BaseListBean<T> implements Serializable {
    private int status;
    private List<T> result;

    public List<T> getResult() {
        return result;
    }

    public int getResultSize() {
        return result == null ? 0 : result.size();
    }
}
