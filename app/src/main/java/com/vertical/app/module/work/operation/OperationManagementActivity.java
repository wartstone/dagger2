package com.vertical.app.module.work.operation;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.common.widget.SectionedSpanSizeLookup;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ls on 11/6/17.
 */

@AutoLayout(layout = R.layout.activity_operationmanagement, title = "运营管理")
public class OperationManagementActivity extends BaseCatActivity implements OperationManagementAdapter.Callback {
    @BindView(R.id.rv_operation_mgr)
    RecyclerView mRecyclerView;

    @Override
    protected void onViewCreated() {
        SparseArray<List<OperationManagerMenu>> data = new SparseArray<>();
        data.put(0, Arrays.asList(OperationManagerMenu.values()));
        data.put(1, Arrays.asList(OperationManagerMenu.values()));

        OperationManagementAdapter adapter = new OperationManagementAdapter(this,data);
        GridLayoutManager manager = new GridLayoutManager(this,4);
        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(adapter,manager);
        manager.setSpanSizeLookup(lookup);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        adapter.setCallback(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(OperationManagerMenu item) {
        switch (item) {
            case BIRTHDAYMGR:
                break;
            case HOLIDAYMGR:
                break;
            default:
                break;
        }
    }
}
