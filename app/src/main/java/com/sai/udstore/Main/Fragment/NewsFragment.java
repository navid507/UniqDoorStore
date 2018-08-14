package com.sai.udstore.Main.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sai.udstore.Main.Adapter.MessageRecyclerViewAdapter;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.R;
import com.sai.udstore.Settings;

public class NewsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private MessageRecyclerViewAdapter urv;
    private RecyclerView recyclerView;
    private ProgressBar mProgressView;
    private EB_Preference prefrence;
    private MainActivity mainActivity;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        ((MainActivity) getActivity()).curFragment = Settings.Fragments.News;
        ((MainActivity) getActivity()).setMainTitle();

        View rv = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = (RecyclerView) rv.findViewById(R.id.fm_rvl_message);
        mProgressView = (ProgressBar) rv.findViewById(R.id.fm_pb_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mainActivity = (MainActivity) getActivity();
        urv = new MessageRecyclerViewAdapter(App.news, getContext(), mainActivity);
        recyclerView.setAdapter(urv);

        prefrence = new EB_Preference(getContext());
        setList();
        return rv;
    }




    private void setList() {
        if (App.news != null) {
            urv.setList(App.news);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (getActivity() == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
