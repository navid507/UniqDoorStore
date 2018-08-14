package com.sai.udstore.Main.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.News;
import com.sai.udstore.DataBase.Models.Product;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.Main.Products.ProductActivity;
import com.sai.udstore.Main.Products.ProductAddActivity;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.sai.UF;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ViewPager newsSlider;
    private MySliderAdapter mAdapter;

    LinearLayout toursHSVLL;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).curFragment = Settings.Fragments.Home;
        ((MainActivity) getActivity()).setMainTitle();
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        LinearLayout mll = v.findViewById(R.id.mainll);
        UF.setAllFonts(App.appFont, mll);
        newsSlider = v.findViewById(R.id.fm_vp_slider);
        loadNewsViewPager();
        dotsLayoutNews = v.findViewById(R.id.fm_ll_dotsNews);
        toursHSVLL = v.findViewById(R.id.fm_ll_tours);
        populateOffersHSV();
        addNewsDots();
        Button allProductsBT = v.findViewById(R.id.fh_bt_products_show);
        allProductsBT.setTypeface(App.appFont);
        allProductsBT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).displayView(Settings.Fragments.Product, "", "", "");
            }
        });
        newsSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (tm != null) {
                    tm.cancel();
                }
                tm = null;
                scheduleTimer();
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return v;

    }

    // HSV of Tours

    private void populateOffersHSV() {
//        toursHSVLL.removeAllViews();
        int size = App.products.size();
        if (size > 8) {
            size = 8;
        }
        for (int i = 0; i < size; i++) {
            final int position = i;
            final int scroll_new_id = App.products.get(i).getId();
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View subView = layoutInflater.inflate(R.layout.lele_product_new, null);

            final ImageView img = subView.findViewById(R.id.fti_iv_image);
            final TextView name_new = subView.findViewById(R.id.fti_tv_title);
            final TextView price_new = subView.findViewById(R.id.fti_tv_price);
            final Product f = App.products.get(i);

            name_new.setTypeface(App.vazirFont);
            price_new.setTypeface(App.vazirFont);

//            String pathUrl1 = String.format(Locale.US, Settings.Urls.imgproduct,f.getImage());
//            UF.loadandsave(getActivity(), Settings.Path.book, f.getImage(), img, pathUrl1, mImageThumbSize, mImageThumbSize, R.drawable.book_default, R.drawable.book_default);

            Picasso.with(getContext())
                    .load(f.getImage())
                    .error(R.drawable.logo)
                    .into(img, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            // Try again online if cache failed
                        }
                    });


            name_new.setText(f.getName());
            price_new.setText(UF.getPriceFormat(f.getPrice(),"fa"));

            toursHSVLL.addView(subView);

            subView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(getActivity(), ProductAddActivity.class);
                    ProductActivity.prd = f;
//                    i.putExtra("pos", f.getId());
//                    i.putExtra("from", Settings.From.OfferList);
                    startActivity(i);
                }
            });
        }
    }

    // For news
    @Override
    public void onResume() {
        scheduleTimer();
        super.onResume();
    }

    @Override
    public void onPause() {
        tm.cancel();
        tm = null;
        super.onPause();
    }

    private List<ImageView> dots;
    private Timer tm;

    LinearLayout dotsLayoutNews;

    private void scheduleTimer() {
        if (tm == null) {
            tm = new Timer();
        } else {
            return;
        }
        tm.schedule(new TimerTask() {

            @Override
            public void run() {
                if (getActivity() == null) {
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (isAdded()) {
                            if (newsSlider.getCurrentItem() < newsSlider.getAdapter().getCount() - 1) {
                                newsSlider.setCurrentItem(newsSlider.getCurrentItem() + 1);
                            } else {
                                newsSlider.setCurrentItem(0);
                            }
                        }
                    }
                });
            }
        }, 5000, 5000);
    }

    public void addNewsDots() {
        dotsLayoutNews.removeAllViews();
        if (dots != null) {
            dots.clear();
        }
        dots = new ArrayList<>();

        for (int i = 0; i < App.news.size(); i++) {
            ImageView dot = new ImageView(getContext());
            dot.setImageDrawable(getResources().getDrawable(R.drawable.ic_checkbox_blank_circle_outline));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) getResources().getDimension(R.dimen.dot_w),
                    (int) getResources().getDimension(R.dimen.dot_h)
            );

            dotsLayoutNews.addView(dot, params);
            dots.add(dot);
        }
    }

    public void selectDot(int idx) {
        Resources res = getResources();
        for (int i = 0; i < App.news.size(); i++) {
            int drawableId = (i == idx) ? (R.drawable.ic_checkbox_blank_circle) : (R.drawable.ic_checkbox_blank_circle_outline);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }


    private void loadNewsViewPager() {
        mAdapter = new MySliderAdapter(getChildFragmentManager());
        newsSlider.setAdapter(mAdapter);
        newsSlider.setCurrentItem(0);
        mAdapter.setList();
        scheduleTimer();
    }

    public static class MySliderAdapter extends FragmentStatePagerAdapter {
        public MySliderAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setList() {
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {

            return App.news.size();
        }

        @Override
        public Fragment getItem(int position) {

            return NewsSliderFragment.newInstance(position);
        }
    }

    public static class NewsSliderFragment extends Fragment {
        int mNum;

        static NewsSliderFragment newInstance(int num) {
            NewsSliderFragment f = new NewsSliderFragment();
            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.news_slider_fragment, container, false);
            ImageView iv = v.findViewById(R.id.nsf_iv_image);
            TextView titleTV = v.findViewById(R.id.nsf_tv_shortdesc);
            TextView descTV = v.findViewById(R.id.nsf_tv_shortdesc);
            try {
                final News cb = App.news.get(mNum);
                titleTV.setText(cb.getSubject());
                titleTV.setTypeface(App.vazirFont);
                String pathUrl1 = cb.getImage();
                Picasso.with(getContext())
                        .load(pathUrl1)
                        .error(R.drawable.logo)
                        .into(iv);

                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(getContext(), NewsFragment.class);
//                        intent.putExtra("an_title_news", cb.getTitle());
//                        intent.putExtra("an_content_news", cb.getContent());
//                        intent.putExtra("an_pic_news", cb.getImage());
//                        intent.putExtra("an_link_news", cb.getLink());
//                        startActivity(intent);
//                        ((MainActivity) getActivity()).displayView(Settings.Fragments.News, mNum + "", "", "");

                    }
                });
            } catch (Exception ex) {
            }

            return v;
        }
    }
}
