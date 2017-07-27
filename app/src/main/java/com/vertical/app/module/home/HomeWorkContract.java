package com.vertical.app.module.home;

import com.vertical.base.BaseModel;
import com.vertical.base.BasePresenter;
import com.vertical.base.BaseView;

/**
 * Created by ls on 7/27/17.
 */

public class HomeWorkContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
    }

    interface Model extends BaseModel {
    }
}
