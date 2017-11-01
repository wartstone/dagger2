package com.vertical.app.module.member;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.vertical.annotation.autolayout.AutoLayout;
import com.vertical.app.R;
import com.vertical.app.base.BaseCatActivity;
import com.vertical.app.core.BaseSearchActivity;

import butterknife.BindView;

/**
 * Created by ls on 10/31/17.
 */

@AutoLayout(layout = R.layout.activity_searchmember)
public class SearchMemberActivity extends BaseSearchActivity {
    private ArrayAdapter<String> mMemberAdapter;

    @BindView(R.id.et_search)
    EditText mSearch;


    @Override
    protected BaseAdapter attachSearchAdapter() {
        mMemberAdapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item);
        return mMemberAdapter;
    }

    @Override
    protected void performSearch(String options) {// api
    }

    @Override
    protected void navigatorToDetail(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
//        intent.putExtra("id", mNoticeServices.get(position).getId());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}