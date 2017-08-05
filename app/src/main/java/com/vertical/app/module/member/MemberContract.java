package com.vertical.app.module.member;

import com.vertical.base.BasePresenter;
import com.vertical.base.BaseView;

/**
 * Created by katedshan on 17/8/5.
 */

public class MemberContract {
    public interface View extends BaseView<Presenter> {
        void showContent();
    }

    public interface Presenter extends BasePresenter {
        void getContent();
    }
}
