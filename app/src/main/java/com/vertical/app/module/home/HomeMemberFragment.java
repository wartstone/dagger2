package com.vertical.app.module.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatFragment;
import com.vertical.app.bean.BaseListBean;
import com.vertical.app.bean.MemberBean;
import com.vertical.app.module.member.HomeMemberAdapter;
import com.vertical.app.network.CatApis;
import com.vertical.app.network.HttpModule;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 7/27/17.
 */

@AutoLayout(layout = R.layout.fragment_member)
public class HomeMemberFragment extends BaseCatFragment implements HomeMemberAdapter.OnItemClickListener {
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private HomeMemberAdapter mMemberAdapter;

    @Override
    protected void onViewCreated() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMemberAdapter = new HomeMemberAdapter();
        mMemberAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mMemberAdapter);
    }

    @Override
    protected void initEventAndData() {
        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
                .fetchMembers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseListBean<MemberBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "fetchMembers onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "fetchMembers onError : " + e);
                    }

                    @Override
                    public void onNext(BaseListBean<MemberBean> memberBeanBaseListBean) {
                        Log.d(TAG, "fetchMembers onNext");
                        if(memberBeanBaseListBean.getResult() == null || memberBeanBaseListBean.getResult().size() == 0)    return;
                        mMemberAdapter.refreshData(memberBeanBaseListBean.getResult());
                        mMemberAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemClick(int index) {

    }
}
