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

public class ProductRecyclerViewAdapater extends RecyclerView.Adapter<ProductRecyclerViewAdapater.ViewHolder> {

    private List<Product> mValues;
    private static Context context;
    private int mImageThumbSize;
    //    private ProductsActivity product;
    private IProductCaller ipCaller;

    public ProductRecyclerViewAdapater(List items, Context mc, IProductCaller iProductCaller) {
        mValues = items;
        context = mc;
//        this.product = product;
        mImageThumbSize = context.getResources().getDimensionPixelSize(R.dimen.image_thumbnail);
        ipCaller = iProductCaller;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lele_product, parent, false);
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

            holder.titleTV.setText(cp.getName());
            holder.priceTV.setText(UF.getPriceFormat(cp.getPrice(), "fa") + "ریال");
            UF.loadandsave(context, "", "", holder.logoView, cp.getImage(), mImageThumbSize, mImageThumbSize, R.drawable.logo, R.drawable.logo);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ipCaller.onProductClicked(pos);
//                    Intent intent = new Intent(product, Products_moreActivity.class);
//                    intent.putExtra("apm_page_product", String.valueOf(pos));
//                    product.startActivity(intent);
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
        public final TextView titleTV, priceTV;//, brandView;
        public final ImageView logoView;
        public Object mItem;

        public ViewHolder(View view1) {
            super(view1);
            mView = view1;
            titleTV = view1.findViewById(R.id.lp_name);
            priceTV = view1.findViewById(R.id.lp_price);
            logoView = view1.findViewById(R.id.lp_img);
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
