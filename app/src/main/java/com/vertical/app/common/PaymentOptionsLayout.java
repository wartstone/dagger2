package com.vertical.app.common;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.vertical.app.R;
import com.vertical.core.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by ls on 9/21/17.
 */

public class PaymentOptionsLayout extends FrameLayout implements View.OnClickListener {
    private Context mContext;
    private LinearLayout mContentLayout;
    private List<ImageView> mCheckBoxs = new ArrayList<>();
    private OnItemSelected mCallback;
    private int mIndex;

    public PaymentOptionsLayout(Context context) {
        this(context, null);
    }

    public PaymentOptionsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaymentOptionsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void initView(Map<String, Object>... data) {
        setBackgroundColor(0x80000000);
        setOnClickListener(this);
        setVisibility(VISIBLE);

        mContentLayout = new LinearLayout(mContext);
        mContentLayout.setOrientation(VERTICAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM;
        addView(mContentLayout, layoutParams);


        TextView title = new TextView(mContext);
        title.setTextColor(getResources().getColor(R.color.text_black));
        title.setTextSize(14);
        title.setBackgroundColor(getResources().getColor(R.color.c_fafafa));
        title.setText("选择支付方式");
        title.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dip2px(mContext, 40));

        mContentLayout.addView(title, params);

        for (int i = 0; i < data.length; i++) {
            //解析数据
            Map<String, Object> map = data[i];
            int headIconResId = (int) map.get("headIcon");
            String optionName = (String) map.get("optionName");
            String optionDesc = map.containsKey("optionDesc") ? (String) map.get("optionDesc") : null;

            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(HORIZONTAL);
            layout.setBackgroundColor(getResources().getColor(R.color.white));
            layout.setTag(i);

            //选项图标
            ImageView paymentIcon = new ImageView(mContext);
            paymentIcon.setImageResource(headIconResId);
            params = new LinearLayout.LayoutParams(Utils.dip2px(mContext, 30), Utils.dip2px(mContext, 30));
            params.leftMargin = Utils.dip2px(mContext, 10);
            params.gravity = Gravity.CENTER_VERTICAL;
            layout.addView(paymentIcon, params);

            //选项名称
            TextView paymentName = new TextView(mContext);
            paymentName.setTextColor(getResources().getColor(R.color.txt_color));
            paymentName.setTextSize(17);
            paymentName.setText(optionName);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = Utils.dip2px(mContext, 10);
            params.gravity = Gravity.CENTER_VERTICAL;
            params.weight = 1;

            //是否有副标题
            if (optionDesc != null) {
                TextView descName = new TextView(mContext);
                descName.setTextColor(getResources().getColor(R.color.text_column_title));
                descName.setTextSize(14);
                descName.setText(optionDesc);

                LinearLayout innerLayout = new LinearLayout(mContext);
                innerLayout.setOrientation(VERTICAL);
                innerLayout.addView(paymentName);
                innerLayout.addView(descName);

                layout.addView(innerLayout, params);

            } else {
                layout.addView(paymentName, params);
            }

            //选择按钮
            ImageView checkIcon = new ImageView(mContext);
            checkIcon.setImageDrawable(getResources().getDrawable(R.drawable.common_icon_radio40_selected));
            checkIcon.setVisibility(GONE);
            params = new LinearLayout.LayoutParams(Utils.dip2px(mContext, 20), Utils.dip2px(mContext, 20));
            params.rightMargin = Utils.dip2px(mContext, 15);
            params.gravity = Gravity.CENTER_VERTICAL;
            layout.addView(checkIcon, params);
            mCheckBoxs.add(checkIcon);

            final int index = i;
            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCheckBoxs.get(mIndex).setVisibility(GONE);
                    mIndex = (int) v.getTag();
                    mCheckBoxs.get(mIndex).setVisibility(VISIBLE);
                    mCallback.onSelected(mIndex);
                    startSlideOutAnim();
                }
            });

            //第一个选项没有分割线
            if (i != 0) {
                View divider = new View(mContext);
                divider.setBackgroundColor(getResources().getColor(R.color.bg_divider));
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = Utils.dip2px(mContext, 50);
                mContentLayout.addView(divider, params);
            }

            //添加选项View
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dip2px(mContext, 55));
            mContentLayout.addView(layout, params);
        }
    }

    @Override
    public void onClick(View v) {
        startSlideOutAnim();
    }

    public void startSlideInAnim() {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_from_bottom);
        Animation anim2 = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        mContentLayout.startAnimation(anim);
        startAnimation(anim2);
    }

    private void startSlideOutAnim() {
        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_from_bottom);
        Animation anim2 = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(GONE);
                removeAllViews();
                mCheckBoxs.clear();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mContentLayout.startAnimation(anim);
        startAnimation(anim2);
    }

    public void setOnItemSelectedListener(OnItemSelected callback) {
        mCallback = callback;
    }

    interface OnItemSelected {
        void onSelected(int position);
    }

    public void setIndex(int index) {
        mIndex = index;
    }
}
