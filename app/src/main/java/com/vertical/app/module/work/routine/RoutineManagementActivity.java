package com.vertical.app.module.work.routine;

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

@AutoLayout(layout = R.layout.activity_routinemanagement, title = "事务管理")
public class RoutineManagementActivity extends BaseCatActivity implements RoutineManagementAdapter.Callback {
    @BindView(R.id.rv_operation_mgr)
    RecyclerView mRecyclerView;

    @Override
    protected void onViewCreated() {
        SparseArray<List<RoutineManagementMenu>> data = new SparseArray<>();
        data.put(0, Arrays.asList(RoutineManagementMenu.values()));

        RoutineManagementAdapter adapter = new RoutineManagementAdapter(this, data);
        GridLayoutManager manager = new GridLayoutManager(this,4);
        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(adapter,manager);
        manager.setSpanSizeLookup(lookup);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        adapter.setCallback(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(RoutineManagementMenu item) {
        switch (item) {
            case DAILYPUSH:
                launchScreen(CustomerMemoActivity.class);
            default:
                break;
        }
    }
}
