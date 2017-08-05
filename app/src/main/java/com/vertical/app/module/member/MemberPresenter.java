package com.vertical.app.module.member;

import com.vertical.app.base.BaseCatPresenter;

/**
 * Created by katedshan on 17/8/5.
 */

public class MemberPresenter extends BaseCatPresenter<MemberContract.View> implements MemberContract.Presenter {

    public MemberPresenter(MemberContract.View view) {
        super(view);
    }

    @Override
    public void getContent() {

    }
}
