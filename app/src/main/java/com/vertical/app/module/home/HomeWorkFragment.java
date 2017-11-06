package com.vertical.app.module.home;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.vertical.app.R;
import com.vertical.app.base.BaseFragment;
import com.vertical.app.module.member.CreateMemberActivity;
import com.vertical.app.module.transaction.CreateOrderActivity;
import com.vertical.app.module.work.DividerGridItemDecoration;
import com.vertical.app.module.work.HomeWorkMenu;
import com.vertical.app.module.work.HomeWorkMenuAdapter;
import com.vertical.app.module.work.operation.OperationManagementActivity;
import com.vertical.app.module.work.salesvisit.SalesVisitActivity;

import butterknife.BindView;

/**
 * Created by ls on 10/31/17.
 */

public class HomeWorkFragment extends BaseFragment implements HomeWorkMenuAdapter.OnMenuClickListener {
    private final String TAG = getClass().getSimpleName();
    private HomeWorkMenuAdapter mWorkMenuAdapter;

    @BindView(R.id.gridContent)
    RecyclerView mGridView;

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_work;
    }

    @Override
    protected void onInitializeView() {
        mWorkMenuAdapter = new HomeWorkMenuAdapter(getActivity());
        mWorkMenuAdapter.setOnMenuClickListener(this);

        mGridView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mGridView.setHasFixedSize(true);
        mGridView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        mGridView.setAdapter(mWorkMenuAdapter);
    }

    @Override
    public void onMenuClick(HomeWorkMenu menu) {
        switch (menu) {
            case TRADE:
                launchScreen(CreateOrderActivity.class);
                break;
            case NOTICE:
                break;
            case GOODS:
                break;
            case OPERATION_MANAGEMENT:
                launchScreen(OperationManagementActivity.class);
                break;
            case OPERATION_ANALYSIS:
                launchScreen(SalesVisitActivity.class);
                break;
            case MEMBERMANAGEMENT:
                launchScreen(CreateMemberActivity.class);
                break;
        }
    }
}
