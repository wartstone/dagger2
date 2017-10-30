package com.vertical.app.module.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.base.BaseFragment;
import com.vertical.app.bean.BaseBean;
import com.vertical.app.bean.MessageBean;
import com.vertical.app.bean.UserBean;
import com.vertical.app.common.widget.pullRefreshView.PullToRefreshView;
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

public class HomeMemberFragment extends Fragment implements HomeMemberAdapter.OnMenuClickListener {
    private final String TAG = getClass().getSimpleName();
    private PullToRefreshView mPullToRefreshView;
    private final int REFRESH_DELAY = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_member, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new HomeMemberAdapter(getActivity()));

        mPullToRefreshView = (PullToRefreshView) rootView.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });

        return rootView;
    }

    @Override
    public void onMenuClick(HomeMemberMenu menu) {
        switch (menu) {
            case MEMBERLIST:
                break;
            case SALESBACK:
                break;
            case OPERATION:
                break;
            case HOLIDAY:
                break;
            case OPERATION2:
                break;
            case SETTING:
                break;
        }
    }
}
