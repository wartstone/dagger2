package com.vertical.app.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vertical.app.R;
import com.vertical.app.common.widget.pullRefreshView.PullToRefreshView;
import com.vertical.app.module.home.HomeMemberAdapter;

/**
 * Created by ls on 11/2/17.
 */

public class BaseRefreshFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private PullToRefreshView mPullToRefreshView;
    private final int REFRESH_DELAY = 1000;
    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_baserefresh, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new HomeMemberAdapter();
        recyclerView.setAdapter(mAdapter);

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
}
