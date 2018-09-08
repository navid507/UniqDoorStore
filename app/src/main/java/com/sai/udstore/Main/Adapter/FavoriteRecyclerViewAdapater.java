package com.sai.udstore.Main.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.Product;
import com.sai.udstore.R;
import com.sai.udstore.sai.UF;

import java.util.List;

public class FavoriteRecyclerViewAdapater extends RecyclerView.Adapter<FavoriteRecyclerViewAdapater.ViewHolder> {

    private List<Product> mValues;
    private static Context context;
    private int mImageThumbSize;
    //    private ProductsActivity product;
    private IProductCaller ipCaller;

    public FavoriteRecyclerViewAdapater(List items, Context mc, IProductCaller iProductCaller) {
        mValues = items;
        context = mc;
//        this.product = product;
        mImageThumbSize = context.getResources().getDimensionPixelSize(R.dimen.image_thumbnail);
        ipCaller = iProductCaller;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lele_fav, parent, false);
        return new ViewHolder(view);
    }

    public void setList(List<Product> values) {
        this.mValues = values;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (holder.mItem instanceof Product) {
            final Product cp = (Product) holder.mItem;
            final int pos = position;

            holder.nameTV.setText(cp.getName());

            try {
                holder.commentTV.setText(cp.getRemark().substring(0, 50));
            } catch (Exception ex) {
                holder.commentTV.setText(cp.getRemark());
            }

//            holder.unitTV.setText(cp.getUnit());
//            holder.codeTV.setText(context.getString(R.string.article_number) + cp.getCode());
            holder.priceTV.setText(cp.getPrice());
            holder.DpriceTV.setText(cp.getDprice());


            if (!cp.getDprice().equals("1")) {
                holder.DpriceTV.setVisibility(View.GONE);
            }
            UF.loadandsave(context, "", "", holder.imageIV, cp.getImage(), mImageThumbSize, mImageThumbSize, R.drawable.logo, R.drawable.logo);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ipCaller.onProductClicked(pos);

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
        public final TextView nameTV, unitTV, commentTV, codeTV, priceTV, DpriceTV;
        public final ImageView imageIV;
        public Object mItem;

        public ViewHolder(View view1) {
            super(view1);
            mView = view1;
            nameTV = (TextView) view1.findViewById(R.id.lf_tv_name);
            codeTV = (TextView) view1.findViewById(R.id.lf_tv_code);
            commentTV = (TextView) view1.findViewById(R.id.lf_tv_comment);
            unitTV = (TextView) view1.findViewById(R.id.lf_tv_unit);
            priceTV = (TextView) view1.findViewById(R.id.lf_tv_price);
            DpriceTV = (TextView) view1.findViewById(R.id.lf_tv_dprice);
            imageIV = view1.findViewById(R.id.lf_iv_image);


        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public interface IProductCaller {
        void onProductClicked(int pos);
    }
}
