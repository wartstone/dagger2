package com.vertical.app.module.work.bizanalysis;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.common.widget.SectionedSpanSizeLookup;
import com.vertical.app.module.work.bizanalysis.salesquery.SalesQueryActivity;
import com.vertical.app.module.work.bizanalysis.salesvisit.SalesVisitActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ls on 11/6/17.
 */

@AutoLayout(layout = R.layout.activity_bizanalysis, title = "经营分析")
public class BizAnalysisActivity extends BaseCatActivity implements BizAnalysisAdapter.Callback {
    @BindView(R.id.rv_operation_mgr)
    RecyclerView mRecyclerView;

    @Override
    protected void onViewCreated() {
        SparseArray<List<BizAnalysisMenu>> data = new SparseArray<>();
        data.put(0, Arrays.asList(BizAnalysisMenu.values()));

        BizAnalysisAdapter adapter = new BizAnalysisAdapter(this, data);
        GridLayoutManager manager = new GridLayoutManager(this,4);
        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(adapter,manager);
        manager.setSpanSizeLookup(lookup);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        adapter.setCallback(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(BizAnalysisMenu item) {
        switch (item) {
            case SALESVISIT:
                launchScreen(SalesVisitActivity.class);
                break;
            case SALESQUERY:
                launchScreen(SalesQueryActivity.class);
                break;
            default:
                break;
        }
    }
}
