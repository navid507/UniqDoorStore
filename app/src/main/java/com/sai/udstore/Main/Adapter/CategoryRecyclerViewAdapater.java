package com.sai.udstore.Main.Adapter;

/**
 * Created by DANIAL on 25/04/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.Category;
import com.sai.udstore.Main.Fragment.CategoryFragment;
import com.sai.udstore.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryRecyclerViewAdapater extends RecyclerView.Adapter<CategoryRecyclerViewAdapater.ViewHolder> {

    private List<Category> mValues;
    private Context context;
    private int mImageThumbSize;
    private CategoryFragment category;

    public CategoryRecyclerViewAdapater(List items, Context mc, CategoryFragment category) {
        mValues = items;
        context = mc;
        this.category = category;
        mImageThumbSize = context.getResources().getDimensionPixelSize(R.dimen.image_thumbnail);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lele_cat, parent, false);
        return new ViewHolder(view);
    }

    public void setList(List<Category> values) {
        this.mValues = values;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (holder.mItem instanceof Category) {
            final Category cp = (Category) holder.mItem;

            holder.headlineView.setText(cp.getName());

            final int pos = position;
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    category.click_item_list(pos);
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
        public final TextView headlineView;
        public Object mItem;

        public ViewHolder(View view1) {
            super(view1);
            mView = view1;
            headlineView = (TextView) view1.findViewById(R.id.lc_name);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
