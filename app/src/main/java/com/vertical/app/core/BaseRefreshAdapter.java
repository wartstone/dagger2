package com.vertical.app.core;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vertical.app.R;

import java.util.Collection;

/**
 * Created by ls on 11/2/17.
 */

public class BaseRefreshAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public String TAG = getClass().getSimpleName();
    protected Collection<T> mData;

    protected final int VIEW_EMPTY = 0;
    protected final int VIEW_LIST = 1;

    public BaseRefreshAdapter() {
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.size() == 0) {
            return VIEW_EMPTY;
        } else {
            return VIEW_LIST;
        }
    }

    @CallSuper
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_EMPTY:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item, null, false));
            default:
                return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item, null, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(mData.size() == 0) {
            return 1;
        } else {
            return mData.size();
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        public ListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
