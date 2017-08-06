package com.vertical.api;

/**
 * Created by katedshan on 17/8/5.
 */

public interface LSViewBinder<T> {
    void bindView(T host, Object object, LSViewFinder finder);

    void unBindView(T host);
}
