package com.sai.udstore.Main.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sai.udstore.Main.Adapter.NewsMoreAdapter;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.Settings;
import com.sai.udstore.R;

public class NewsMoreFragment extends Fragment {

    private int mImageThumbSize;
    private ViewPager viewerpage;
    private NewsMoreAdapter adapter;

    private static final String ARG_PARAM1 = "param1", ARG_PARAM2 = "param2";
    private String position = "";

    public NewsMoreFragment() {
        // Required empty public constructor
    }

    public static NewsMoreFragment newInstance(String param1, String param2) {
        NewsMoreFragment fragment = new NewsMoreFragment();
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
            position = getArguments().getString(ARG_PARAM1);
        }

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).curFragment = Settings.Fragments.News;
        ((MainActivity) getActivity()).setMainTitle();
        View rv = inflater.inflate(R.layout.fragment_pager, container, false);

        viewerpage = (ViewPager) rv.findViewById(R.id.pager);


        adapter = new NewsMoreAdapter(getActivity(), App.news);
        viewerpage.setAdapter(adapter);
        viewerpage.setCurrentItem(Integer.valueOf(position));

        return rv;
    }
}
