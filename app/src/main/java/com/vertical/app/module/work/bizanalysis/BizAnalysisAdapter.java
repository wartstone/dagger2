package com.vertical.app.module.work.bizanalysis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vertical.app.R;
import com.vertical.app.common.widget.SectionedRecyclerViewAdapter;
import com.vertical.app.module.work.bizanalysis.BizAnalysisAdapter.TextHeaderViewHolder;
import com.vertical.app.module.work.bizanalysis.BizAnalysisAdapter.TransactionManagementItemViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ls on 11/6/17.
 */

public class BizAnalysisAdapter extends SectionedRecyclerViewAdapter<TextHeaderViewHolder,TransactionManagementItemViewHolder,RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private SparseArray<List<BizAnalysisMenu>> mData;
    private Callback mCallback;

    public BizAnalysisAdapter(Context context, SparseArray<List<BizAnalysisMenu>> data){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mData = data;
    }

    @Override
    protected int getSectionCount() {
        return mData.size();
    }


    @Override
    protected int getItemCountForSection(int section) {
        return mData.get(section).size();
    }


    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }


    @Override
    protected TextHeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new TextHeaderViewHolder(mInflater.inflate(R.layout.item_text_header_view_holder,parent,false));
    }


    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected TransactionManagementItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new TransactionManagementItemViewHolder(mInflater.inflate(R.layout.item_bizanalysis,parent,false));
    }

    @Override
    protected void onBindSectionHeaderViewHolder(TextHeaderViewHolder holder, int section) {
        if (section == 0){
            holder.header.setText("销售分析");
        }else {
//            holder.header.setText("订单管理");
        }
    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {
    }

    @Override
    protected void onBindItemViewHolder(TransactionManagementItemViewHolder holder, int section, int position) {
        final BizAnalysisMenu item = mData.get(section).get(position);
        holder.icon.setImageResource(item.getDrawable());
        holder.label.setText(item.getTitle());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.onItemClick(item);
                }
            }
        });
    }

    public interface Callback {
        void onItemClick(BizAnalysisMenu item);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public class TextHeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_header)
        TextView header;
        public TextHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class TransactionManagementItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.root)
        View root;
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.label)
        TextView label;

        public TransactionManagementItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
