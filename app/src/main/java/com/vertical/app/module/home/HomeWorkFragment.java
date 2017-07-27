package com.vertical.app.module.home;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by ls on 7/27/17.
 */

public class HomeWorkFragment extends SupportFragment implements HomeWorkContract.View {
    private HomeWorkContract.Presenter mPresenter;

    public static HomeWorkFragment newInstance() {
        HomeWorkFragment fragment = new HomeWorkFragment();
        return fragment;
    }

    @Override
    public void setPresenter(HomeWorkContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
