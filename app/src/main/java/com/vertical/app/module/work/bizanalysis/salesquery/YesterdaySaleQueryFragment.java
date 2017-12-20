package com.vertical.app.module.work.bizanalysis.salesquery;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatFragment;
import com.vertical.app.bean.BaseListBean;
import com.vertical.app.network.CatApis;
import com.vertical.app.network.HttpModule;

import butterknife.BindView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ls on 11/2/17.
 */

@AutoLayout(layout = R.layout.fg_salequery_yesterday)
public class YesterdaySaleQueryFragment extends BaseCatFragment implements SaleQueryAdapter.OnItemClickListener {
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private SaleQueryAdapter mSaleQueryAdapter;

    @Override
    protected void onViewCreated() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSaleQueryAdapter = new SaleQueryAdapter();
        mSaleQueryAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mSaleQueryAdapter);
    }

    @Override
    protected void initEventAndData() {
        HttpModule.getSharedInstance().createRetrofit(CatApis.class)
                .querySaleRecords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseListBean<SaleRecordBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "querySaleRecords onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "querySaleRecords onError : " + e);
                    }

                    @Override
                    public void onNext(BaseListBean<SaleRecordBean> listBean) {
                        Log.d(TAG, "querySaleRecords onNext");
                        if(listBean.getResult() == null || listBean.getResult().size() == 0)    return;
                        mSaleQueryAdapter.refreshData(listBean.getResult());
                        mSaleQueryAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemClick(int index) {

    }
}
