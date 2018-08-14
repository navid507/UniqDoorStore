package com.sai.udstore.Main.Adapter;

/**
 * Created by DANIAL on 25/04/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.Download;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.R;

import java.util.List;

public class DownloadRecyclerViewAdapater extends RecyclerView.Adapter<DownloadRecyclerViewAdapater.ViewHolder> {

    private List<Download> mValues;
    private MainActivity activity;

    public DownloadRecyclerViewAdapater(List items, MainActivity mc) {
        mValues = items;
        activity = mc;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lele_download, parent, false);
        return new ViewHolder(view);
    }

    public void setList(List<Download> values) {
        this.mValues = values;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (holder.mItem instanceof Download) {
            final Download cp = (Download) holder.mItem;

            holder.remarkTV.setText(cp.getRemark());
            holder.idTV.setText(String.valueOf(cp.getId()));
            holder.rowTV.setText(String.valueOf(position));
            holder.typeTV.setText(String.valueOf(cp.getFileType()));

            final int pos = position;
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(cp.getFileUrl()));
                    activity.startActivity(browserIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView remarkTV, idTV, typeTV, rowTV, operationTV;
        public final TextView id_strTV, type_strTV, row_strTV, operation_strTV;
        public Object mItem;

        public ViewHolder(View view1) {
            super(view1);
            mView = view1;
            remarkTV = view1.findViewById(R.id.ld_tv_remark);
            idTV = view1.findViewById(R.id.ld_tv_id);
            id_strTV = view1.findViewById(R.id.ld_tv_id_str);
            type_strTV = view1.findViewById(R.id.ld_tv_type_str);
            typeTV = view1.findViewById(R.id.ld_tv_type);
            rowTV = view1.findViewById(R.id.ld_tv_row);
            row_strTV = view1.findViewById(R.id.ld_tv_row_str);
            operation_strTV = view1.findViewById(R.id.ld_tv_operation_str);
            operationTV = view1.findViewById(R.id.ld_tv_operation);

            remarkTV.setTypeface(App.appFont);
            idTV.setTypeface(App.appFont);
            id_strTV.setTypeface(App.appFont);
            rowTV.setTypeface(App.appFont);
            row_strTV.setTypeface(App.appFont);
            typeTV.setTypeface(App.appFont);
            type_strTV.setTypeface(App.appFont);
            operationTV.setTypeface(App.appFont);
            operation_strTV.setTypeface(App.appFont);

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
