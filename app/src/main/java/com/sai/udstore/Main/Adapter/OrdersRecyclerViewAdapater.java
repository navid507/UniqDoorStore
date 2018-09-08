package com.sai.udstore.Main.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.Sefaresh;
import com.sai.udstore.Main.App;
import com.sai.udstore.R;
import com.sai.udstore.sai.UF;

import java.util.List;

public class OrdersRecyclerViewAdapater extends RecyclerView.Adapter<OrdersRecyclerViewAdapater.ViewHolder> {

    private List<Sefaresh> mValues;
    private static Context context;
    //    private ProductsActivity product;
    private IOrderCaller ipCaller;

    public OrdersRecyclerViewAdapater(List items, Context mc, IOrderCaller iProductCaller) {
        mValues = items;
        context = mc;
//        this.product = product;
        ipCaller = iProductCaller;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lele_order_linear, parent, false);
        return new ViewHolder(view);
    }

    public void setList(List<Sefaresh> values) {
        this.mValues = values;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (holder.mItem instanceof Sefaresh) {
            final Sefaresh cp = holder.mItem;
            final int pos = position;

            holder.nameView.setText(cp.getName());
//            holder.priceTV.setText(context.getString(R.string.article_number) + cp.getCode());
            if (cp.getDiscount().equals("1")) {
                holder.discountTV.setText(UF.getPriceFormat(App.userProfile.getDiscount(), "fa") + "%");
            } else {
                holder.discountTV.setText("ندارد");

            }
            holder.quantityTV.setText(cp.getCount() + " عدد");
            int totalPrice = 0;
            try {
                int priced = Integer.valueOf(cp.getPrice());
                totalPrice = cp.getCount() * priced;

            } catch (Exception err) {

            }
            holder.priceTV.setText(UF.getPriceFormat(totalPrice, "fa") + "ریال");

            UF.loadandsave(context, "", "", holder.logoView, cp.getImage(), 128, 128, R.drawable.logo, R.drawable.logo);


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ipCaller.onOrderClicked(pos);
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
        public final TextView nameView, priceTV, quantityTV, discountTV, discount_strTV;//, price_strTV; //, brandView;
        public final ImageView logoView;
        public Sefaresh mItem;

        public ViewHolder(View view1) {
            super(view1);
            mView = view1;
            nameView = view1.findViewById(R.id.lo_name);
            discountTV = view1.findViewById(R.id.lo_discount);
            logoView = view1.findViewById(R.id.lo_iv_image);

            priceTV = view1.findViewById(R.id.lo_price);
//            price_strTV = view1.findViewById(R.id.lo_price_str);
            quantityTV = view1.findViewById(R.id.lo_quantity);
            discount_strTV = view1.findViewById(R.id.lo_discount);
            LinearLayout mmll = view1.findViewById(R.id.mmll);
            UF.setAllFonts(App.appFont, mmll);

//            nameView.setTypeface(App.appFont);
//            nameView.setTypeface(App.appFont);
//            nameView.setTypeface(App.appFont);
//            nameView.setTypeface(App.appFont);
//            nameView.setTypeface(App.appFont);
//            nameView.setTypeface(App.appFont);
//            nameView.setTypeface(App.appFont);

        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public interface IOrderCaller {
        void onOrderClicked(int pos);
    }
}
