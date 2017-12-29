package com.vertical.app.module.goods;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vertical.annotation.AutoWire;
import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ls on 12/29/17.
 */

@AutoWire(presenter = GoodsManagementPresenter.class)
@AutoLayout(layout = R.layout.activity_goods_management, title = "商品管理", title_right = "新建商品")
public class GoodsManagementActivity extends BaseCatActivity<GoodsManagementContract.Presenter> implements GoodsManagementContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private GoodsManagementAdapter mGoodsAdapter;

    @Override
    protected void onViewCreated() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mGoodsAdapter = new GoodsManagementAdapter();
        mRecyclerView.setAdapter(mGoodsAdapter);
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onShowGoods(List<GoodsBean> goodsBeanList) {

    }

    @Override
    public void onNavigationRightClicked() {

    }
}
