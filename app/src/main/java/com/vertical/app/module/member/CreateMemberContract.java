package com.vertical.app.module.member;

import com.vertical.app.bean.MemberBean;
import com.vertical.app.bean.OrderBean;
import com.vertical.base.BasePresenter;
import com.vertical.base.BaseView;

/**
 * Created by ls on 10/12/17.
 */

public interface CreateMemberContract {
    public interface View extends BaseView<Presenter> {
        void onMemberCreated(boolean result);
    }

    public interface Presenter extends BasePresenter {
        void createMember(MemberBean memberBean);
    }
}
