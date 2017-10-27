package com.vertical.app.module.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vertical.app.R;
import com.vertical.app.common.widget.pullRefreshView.PullToRefreshView;

/**
 * Created by ls on 10/27/17.
 */

public class HomeRecyclerViewFragment extends Fragment implements HomeMemberAdapter.OnMenuClickListener {
    private final String TAG = getClass().getSimpleName();
    private PullToRefreshView mPullToRefreshView;
    private final int REFRESH_DELAY = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recycler_view, container, false);

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
