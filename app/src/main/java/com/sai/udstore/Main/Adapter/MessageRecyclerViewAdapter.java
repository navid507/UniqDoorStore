package com.sai.udstore.Main.Adapter;

/**
 * Created by DANIAL on 27/03/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.Message;
import com.sai.udstore.DataBase.Models.News;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.Main.MessageActivity;
import com.sai.udstore.R;

import java.util.List;


public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder> {

    private List<News> lstMessage;
    private Context context;
    private int mImageThumbSize;
    private MainActivity mainActivity;

    public MessageRecyclerViewAdapter(List items, Context mc, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        lstMessage = items;
        context = mc;
        mImageThumbSize = mc.getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_news_item, parent, false);
        return new ViewHolder(view);
    }

    public void setList(List<News> values) {
        this.lstMessage = values;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = lstMessage.get(position);
        if (holder.mItem instanceof Message) {
            final Message cp = (Message) holder.mItem;
            holder.contant.setText(cp.getContent());
            final int pos = position;

            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MessageActivity.class);
                    intent.putExtra("am_title_message", cp.getSubject());
                    intent.putExtra("am_content_message", cp.getContent());
                    intent.putExtra("am_pic_message", cp.getImage());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lstMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView contant;
        public final Button more;
        public Object mItem;

        public ViewHolder(View view1) {
            super(view1);
            mView = view1;
            contant = (TextView) view1.findViewById(R.id.am_tv_contant_message);
            more = (Button) view1.findViewById(R.id.am_btn_more_message);

            contant.setTypeface(App.appFont);
            more.setTypeface(App.appFont);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contant.getId() + "'";
        }
    }
}
