package com.vertical.app.module.work.operation;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vertical.app.R;
import com.vertical.app.common.widget.CatTextView;
import com.vertical.app.common.widget.WorkBoardLayout;
import com.vertical.app.module.home.HomeMemberMenu;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 11/7/17.
 */

public class RevokeOperationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public String TAG = getClass().getSimpleName();
    private OnMenuClickListener onMenuClickListener;

    public RevokeOperationAdapter(){
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_menu, null, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder listViewHolder = (ViewHolder)holder;
        final HomeMemberMenu menu = HomeMemberMenu.values()[position];
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
    }

    @Override
    public int getItemCount() {
        return HomeMemberMenu.values().length;
    }

    public interface OnMenuClickListener{
        void onMenuClick(HomeMemberMenu menu);
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.root)
        View root;
        @BindView(R.id.content)
        CatTextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}