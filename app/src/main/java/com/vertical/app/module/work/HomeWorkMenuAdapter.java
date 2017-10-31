package com.vertical.app.module.work;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vertical.app.R;
import com.vertical.app.common.widget.WorkBoardLayout;
import com.vertical.app.module.home.HomeMemberMenu;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 10/27/17.
 */

public class HomeWorkMenuAdapter extends RecyclerView.Adapter<HomeWorkMenuAdapter.ViewHolder> {
    public String TAG = getClass().getSimpleName();
    final Context mContext;
    private OnMenuClickListener onMenuClickListener;

    public HomeWorkMenuAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public HomeWorkMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_work_gridview, null, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HomeWorkMenu workMenu = HomeWorkMenu.values()[position];
        holder.icon.setImageResource(workMenu.getDrawable());
        holder.label.setText(workMenu.getTitle());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (null != onMenuClickListener) {
                    onMenuClickListener.onMenuClick(workMenu);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return HomeWorkMenu.values().length;
    }

    public interface OnMenuClickListener{
        void onMenuClick(HomeWorkMenu menu);
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.root) View root;
        @BindView(R.id.img_icon)
        ImageView icon;
        @BindView(R.id.tv_title)
        TextView label;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}