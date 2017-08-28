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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;

/**
 * This class helps quickly building a custimized layout based on ButterKnife.
 * 
 * @author Ralken Liao
 * @date 2016-10-8 13:29:02
 */
public abstract class BaseFrameLayout extends FrameLayout {
	
	public BaseFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public BaseFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public BaseFrameLayout(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		View root = LayoutInflater.from(context).inflate(getInflatedLayout(), this);
		if (!isInEditMode()) {
			ButterKnife.bind(root, this);
		}
		this.onInitializeCompleted();
	}

	protected abstract int getInflatedLayout();
	
	protected abstract void onInitializeCompleted();
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}
	
}
