package com.sai.udstore.Main.Adapter;

/**
 * Created by DANIAL on 27/03/2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.News;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.sai.JustifiedTextView;
import com.sai.udstore.sai.UF;

import java.util.List;

public class NewsMoreAdapter extends PagerAdapter {

    private TextView subject;
    private ImageView pic;
    private JustifiedTextView content;
    private Typeface vazir;
    private int mImageThumbSize;
    private List<News> News;
    private LayoutInflater inflater;
    private Context context;

    public NewsMoreAdapter(Context c, List<News> paths) {
        this.News = paths;
        this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = c;
    }

    @Override
    public int getCount() {
        return this.News.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // View viewLayout

        ((ViewPager) container).removeView((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View rv = inflater.inflate(R.layout.fragment_news_more, container, false);
        final News news = News.get(position);

        vazir = Typeface.createFromAsset(context.getAssets(), "font/vazir.ttf");
        mImageThumbSize = context.getResources().getDimensionPixelSize(R.dimen.image_thumbnail);

        subject = (TextView) rv.findViewById(R.id.fnm_tv_news);
        content = (JustifiedTextView) rv.findViewById(R.id.fnm_tv_content_news);
        pic = (ImageView) rv.findViewById(R.id.fnm_img_news);

        content.setTypeFace(vazir);
        content.setLineSpacing(14);
        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        content.setText(news.getContent());

        subject.setText(news.getSubject());

        String pathUrl1 = news.getImage();
        UF.loadandsave(context, Settings.Path.News, news.getImage(), pic, pathUrl1, mImageThumbSize, mImageThumbSize, R.drawable.logo, R.drawable.logo);

        ((ViewPager) container).addView(rv);
        return rv;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((LinearLayout) arg1);
    }
}
