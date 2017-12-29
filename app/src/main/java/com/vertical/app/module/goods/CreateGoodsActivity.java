package com.vertical.app.module.goods;

import android.widget.Toast;

import com.vertical.annotation.AutoWire;
import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.bean.OrderBean;
import com.vertical.app.common.widget.CatEditText;
import com.vertical.app.common.widget.CatTextView;
import com.vertical.app.module.transaction.CreateOrderContract;
import com.vertical.app.module.transaction.CreateOrderPresenter;

import java.sql.Date;
import java.sql.Timestamp;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ls on 10/12/17.
 */

@AutoWire(presenter = CreateGoodsPresenter.class)
@AutoLayout(layout = R.layout.activity_creategoods, title = "新建商品")
public class CreateGoodsActivity extends BaseCatActivity<CreateGoodsContract.Presenter> implements CreateGoodsContract.View {
    @BindView(R.id.register_number)
    CatEditText mRegisterNo;
    @BindView(R.id.register_name)
    CatEditText mRegisterName;
    @BindView(R.id.goods_name)
    CatEditText mGoodsName;
    @BindView(R.id.goods_category)
    CatTextView mGoodsCategory;
    @BindView(R.id.goods_saleprice)
    CatTextView mGoodsSalePrice;
    @BindView(R.id.goods_stockprice)
    CatTextView mGoodsStockPrice;
    @BindView(R.id.goods_stockcount)
    CatTextView mGoodsStockCount;
    @BindView(R.id.goods_product_date)
    CatEditText mGoodsProductDa;
    @BindView(R.id.goods_comments)
    CatTextView mGoodsComments;

    @Override
    public void onGoodsCreation(boolean success) {
        Toast.makeText(this, "创建商品成功!", Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.cancel)
    protected void cancel() {
        finish();
    }

    @OnClick(R.id.save_btn)
    protected void save_goods() {
        GoodsBean goodsBean = new GoodsBean();


        long receiver_id = 1;
        long goods_id = 1;
        int payment = 1;
        int goods_count = 1;
        double order_bill = 100.00;
        String comments = "nice try";


        mPresenter.createGoods(receiver_id, goods_id, payment, goods_count, order_bill, comments);
    }
}
