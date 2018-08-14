package com.sai.udstore.Main.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.Sefaresh_fact;
import com.sai.udstore.Main.App;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.sai.UF;

import java.util.List;

public class FactorsRecyclerViewAdapater extends RecyclerView.Adapter<FactorsRecyclerViewAdapater.ViewHolder> {

    private List<Sefaresh_fact> mValues;
    private static Context context;
    IInvoiceCaller caller;

    public FactorsRecyclerViewAdapater(List items, Context mc, IInvoiceCaller callerr) {
        mValues = items;
        context = mc;
//        this.product = product;
        this.caller = callerr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lele_factor_linear, parent, false);
        return new ViewHolder(view);
    }

    public void setList(List<Sefaresh_fact> values) {
        this.mValues = values;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (holder.mItem instanceof Sefaresh_fact) {
            final Sefaresh_fact cp = holder.mItem;
            final int pos = position;

            holder.factorTV.setText(String.valueOf(cp.getId()));
            if (cp.getPayType() == 0) {
                holder.dateTV.setText("اعتباری");

            } else {
                holder.dateTV.setText("نقدی");

            }
            holder.dateTV.setText(cp.getFdate());
            holder.rowTV.setText(String.valueOf(position + 1));
//            holder.paymentTV.setText(cp.ge());
//            holder.priceTV.setText(cp.getTotal_price());

        }
    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView rowTV, factorTV, paymentTV, priceTV, stateTV, dateTV;//, brandView;
        //        public final ImageView logoView;
        public Sefaresh_fact mItem;

        public ViewHolder(View view1) {
            super(view1);
            mView = view1;
            rowTV = view1.findViewById(R.id.le_tv_order_row);
            factorTV = view1.findViewById(R.id.le_tv_order_id_factor);
            paymentTV = view1.findViewById(R.id.le_tv_order_payment);
            priceTV = view1.findViewById(R.id.le_tv_order_price);
            stateTV = view1.findViewById(R.id.le_tv_order_state);
            dateTV = view1.findViewById(R.id.le_tv_order_date);
            LinearLayout mmll = view1.findViewById(R.id.mmll);
            UF.setAllFonts(App.appFont, mmll);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public interface IInvoiceCaller {
        void onOrderClicked(int pos);
    }
}
