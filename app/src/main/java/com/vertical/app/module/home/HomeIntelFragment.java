package com.vertical.app.module.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vertical.app.R;
import com.vertical.app.common.util.Trace;
import com.vertical.app.common.widget.pullRefreshView.PullToRefreshView;
import com.vertical.app.module.devtool.CatDevService;
import com.vertical.app.module.login.GuideActivity;
import com.vertical.app.module.transaction.CreateOrderActivity;

/**
 * Created by ls on 7/27/17.
 */

public class HomeIntelFragment extends Fragment implements HomeAdapter.OnMenuClickListener {

    private final String TAG = getClass().getSimpleName();
    private PullToRefreshView mPullToRefreshView;
    private final int REFRESH_DELAY = 1000;
    private HomeAdapter mMemberAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_baserefresh, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMemberAdapter = new HomeAdapter();
        mMemberAdapter.setOnMenuClickListener(this);
        recyclerView.setAdapter(mMemberAdapter);

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
                ((HomeActivity)getActivity()).launchScreen(GuideActivity.class);
                break;
            case SALESBACK:
                ((HomeActivity)getActivity()).launchScreen(CreateOrderActivity.class);
                break;
            case OPERATION:
                ((HomeActivity)getActivity()).launchScreen(CreateOrderActivity.class);
                break;
            case HOLIDAY:
                ((HomeActivity)getActivity()).launchScreen(CreateOrderActivity.class);
                break;
            case OPERATION2:
                ((HomeActivity)getActivity()).launchScreen(CreateOrderActivity.class);
                break;
            case SETTING:
                ((HomeActivity)getActivity()).launchScreen(CreateOrderActivity.class);
                break;
        }
    }
}
