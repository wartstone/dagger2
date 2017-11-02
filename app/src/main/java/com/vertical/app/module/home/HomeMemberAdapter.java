package com.vertical.app.module.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vertical.app.R;
import com.vertical.app.common.widget.CatEditText;
import com.vertical.app.common.widget.CatTextView;
import com.vertical.app.common.widget.WorkBoardLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 10/27/17.
 */

public class HomeMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public String TAG = getClass().getSimpleName();
    private OnMenuClickListener onMenuClickListener;
    private final int VIEW_WORKBAORD = 0;
    private final int VIEW_LIST = 1;

    public HomeMemberAdapter(){
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_WORKBAORD : VIEW_LIST;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_WORKBAORD) {
            return new BoardViewHolder(new WorkBoardLayout(parent.getContext()));
        } else {
            return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_menu, null, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_WORKBAORD:
                break;
            case VIEW_LIST:
                ListViewHolder listViewHolder = (ListViewHolder)holder;
                final HomeMemberMenu menu = HomeMemberMenu.values()[position - 1];
                Log.v(TAG, "[onBindViewHolder] menu = " + menu);
                listViewHolder.content.setLeftIconRes(menu.getDrawable());
                listViewHolder.content.setLeftTextString(menu.getTitle());

                listViewHolder.root.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View view) {
                        if (null != onMenuClickListener) {
                            onMenuClickListener.onMenuClick(menu);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return HomeMemberMenu.values().length + 1;
    }

    public interface OnMenuClickListener{
        void onMenuClick(HomeMemberMenu menu);
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    class BoardViewHolder extends RecyclerView.ViewHolder {
        public BoardViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.root)
        View root;
        @BindView(R.id.content)
        CatTextView content;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}