package com.sai.udstore.Main.Fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.Download;
import com.sai.udstore.Main.Adapter.DownloadRecyclerViewAdapater;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.excomponents.Web;
import com.sai.udstore.sai.UF;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DownloadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DownloadFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GetDownloadsTask mDownloadTask = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DownloadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DownloadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DownloadFragment newInstance(String param1, String param2) {
        DownloadFragment fragment = new DownloadFragment();
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

    private MainActivity activity = null;
    private List<Download> cats;
    RecyclerView recyclerView;
    private View mProgressCatView;
    private DownloadRecyclerViewAdapater adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).curFragment = Settings.Fragments.Download;
        ((MainActivity) getActivity()).setMainTitle();
        activity = (MainActivity) getActivity();

        View v = inflater.inflate(R.layout.fragment_download, container, false);


        cats = new ArrayList<>();

        TextView title = (TextView) v.findViewById(R.id.fd_tv_title);
        recyclerView = (RecyclerView) v.findViewById(R.id.fd_lv_downloads);
        mProgressCatView = v.findViewById(R.id.fd_pv_cat);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DownloadRecyclerViewAdapater(cats, (MainActivity) getActivity());
        recyclerView.setAdapter(adapter);

        populateCats();

        return v;
    }

    private void populateCats() {
        showProgress(true, true);
        if (mDownloadTask == null) {
            mDownloadTask = new GetDownloadsTask();
            mDownloadTask.execute((Void) null);
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show, boolean hideList) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            if (hideList) {
                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                recyclerView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });
            }


            mProgressCatView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressCatView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressCatView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressCatView.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public class GetDownloadsTask extends AsyncTask<Void, Void, Boolean> {

        GetDownloadsTask() {
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {

                String response = Web.send(Settings.Urls.GetAllDownload, "");
                cats = UF.Update_Downloads(response);

            } catch (Exception err) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mDownloadTask = null;
            if (success) {
            }
            showProgress(false, true);
            adapter.setList(cats);

            super.onPostExecute(success);
        }
    }


}
