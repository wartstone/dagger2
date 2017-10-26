package com.vertical.app.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vertical.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ls on 10/26/17.
 */

public class WorkBoardLayout extends FrameLayout {
    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private Unbinder mUnbinder;

    @BindView(R.id.month_output)
    TextView mMonthOutput;

    public WorkBoardLayout(Context context) {
        this(context, null);
    }
    public WorkBoardLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public WorkBoardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_workdboard, this, true);
        ButterKnife.bind(this);  // TODO: 10/26/17  需要unBind.可能出现内存泄露
    }

    public void setMonthOutput(int amount) {
        mMonthOutput.setText(String.valueOf(amount));
    }

    @Override
    public void onDetachedFromWindow() {
        mUnbinder.unbind();
        super.onDetachedFromWindow();
    }
}
