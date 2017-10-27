package com.vertical.app.module.home;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.base.BaseFragment;
import com.vertical.app.bean.BaseBean;
import com.vertical.app.bean.MessageBean;
import com.vertical.app.bean.UserBean;
import com.vertical.app.network.CatApis;
import com.vertical.app.network.HttpModule;
import com.vertical.core.ui.BaseFrameLayout;

import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 7/27/17.
 */

public class HomeMemberFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_member;
    }

    @Override
    protected void onInitializeView() {

    }

}
