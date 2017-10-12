/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright (c) 2016 Shanghai HeNong Network Technology Co.,Ltd.All rights reserved.
 *
 * This source code has been made available to you by HeNong on
 * AS-IS.Anyone receiving this source is licensed under
 * HeNong copyrights to use it in any way he or she deems fit,including
 * copying it,modifying it,compiling it,and redistributing it either with
 * or without modifictions.
 *
 * Any person who transfers this source code or any derivative work must
 * include the HeNong copyright notice, this paragraph,and the preceding
 * two paragraphs in the transferred software.
 *
 * Copyright (c) 2016 Shanghai HeNong Network Technology Co.,Ltd.All rights reserved.
 * LICENSED MATERIAL - PROGRAM PROPERTY OF HENONG
 */
package com.vertical.core.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.vertical.core.R;
import com.vertical.core.R2;
import com.vertical.core.util.TextUtil;

import butterknife.BindView;

public class TitleBarLayout extends BaseFrameLayout implements OnClickListener {

	@BindView(R2.id.mLeftGroup) LinearLayout mLeftGroup;
	@BindView(R2.id.mLeftIcon) ImageView mLeftIcon;
	@BindView(R2.id.mLeftText) TextView mLeftText;
	@BindView(R2.id.mTitleText) TextView mTitleText;
	@BindView(R2.id.mRightIcon) ImageView mRightIcon;
	@BindView(R2.id.mRightText) TextView mRightText;

	private OnTitleBarClickListener onTitleBarClickListener;

	public TitleBarLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TitleBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TitleBarLayout(Context context) {
		super(context);
	}

	public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
		this.onTitleBarClickListener = onTitleBarClickListener;
	}

	@Override
	protected int getInflatedLayout() {
		return R.layout.component_common_title_bar;
	}

	@Override
	protected void onInitializeCompleted() {
		mLeftGroup.setOnClickListener(this);
		mRightIcon.setOnClickListener(this);
		mRightText.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (null == onTitleBarClickListener)
			return;
		if (v == mLeftGroup) {
			onTitleBarClickListener.onNavigationLeftClicked();
		} else if (v == mRightIcon) {
			onTitleBarClickListener.onNavigationRightClicked();
		} else if (v == mRightText) {
			onTitleBarClickListener.onNavigationRightClicked();
		}
	}

	public void configTitleBar(int leftDrawble, String leftTitle, String title,
			int rightDrawable, String rightText) {

		setLeftDrawable(leftDrawble);

		setLeftTitle(leftTitle);

		setTitle(title);

		setRightDrawable(rightDrawable);

		setRightText(rightText);
	}

	private void setRightText(String rightText) {
		if (TextUtil.isValidate(rightText)) {
			mRightText.setText(rightText);
		}else {
			mRightText.setText("");
		}
	}

	private void setRightDrawable(int rightDrawable) {
		if (rightDrawable != 0) {
			mRightIcon.setVisibility(View.VISIBLE);
			mRightIcon.setImageResource(rightDrawable);
		}else{
			mRightIcon.setVisibility(View.GONE);
			mRightIcon.setImageResource(0);
		}
	}

	private void setTitle(String title) {
		if (TextUtil.isValidate(title)) {
			mTitleText.setText(title);
		}else {
			mTitleText.setText("");
		}
	}

	private void setLeftTitle(String leftTitle) {
		if (TextUtil.isValidate(leftTitle)) {
			mLeftText.setText(leftTitle);
		}else {
			mLeftText.setText("");
		}
	}

	private void setLeftDrawable(int leftDrawble) {
		if (leftDrawble != 0) {
			mLeftIcon.setImageResource(leftDrawble);
		}
	}

	public interface OnTitleBarClickListener {
		void onNavigationLeftClicked();
		void onNavigationRightClicked();
	}

}
