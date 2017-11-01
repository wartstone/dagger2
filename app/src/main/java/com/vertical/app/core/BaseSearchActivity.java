package com.vertical.app.core;

import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

/**
 * Created by ls on 11/1/17.
 */

@AutoLayout(layout = R.layout.activity_basesearch)
public abstract class BaseSearchActivity extends BaseCatActivity {
    @BindView(R.id.listview)
    ListView mListView;
    private BaseAdapter mAdapter;

    @OnTextChanged(value = R.id.et_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onSearchAfterTextChanged(Editable s) {
        performSearch(s.toString());
    }

    @OnItemClick(R.id.listview)
    void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigatorToDetail(parent, view, position, id);
    }

    @Override
    protected void onViewCreated() {
        mAdapter = attachSearchAdapter();
        mListView.setAdapter(mAdapter);
    }

    protected abstract BaseAdapter attachSearchAdapter();

    protected abstract void performSearch(String options);

    protected abstract void navigatorToDetail(AdapterView<?> parent, View view, int position, long id);
}
