package com.vertical.app.module.work.operation;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.common.widget.pullRefreshView.PullToRefreshView;
import com.vertical.app.module.home.HomeMemberAdapter;
import com.vertical.app.module.home.HomeMemberMenu;

import butterknife.BindView;

/**
 * Created by ls on 11/2/17.
 */

@AutoLayout(layout = R.layout.activity_revokeoperation, title = "休眠激活")
public class RevokeOperationActivity extends BaseCatActivity implements RevokeOperationAdapter.OnMenuClickListener{
    @BindView(R.id.pull_to_refresh)
    PullToRefreshView mPullToRefreshView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private RevokeOperationAdapter mAdapter;
    private final int REFRESH_DELAY = 100;

    @Override
    protected void onViewCreated() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new RevokeOperationAdapter();
        mAdapter.setOnMenuClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

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
    }

    @Override
    public void onMenuClick(HomeMemberMenu menu) {

    }
}
