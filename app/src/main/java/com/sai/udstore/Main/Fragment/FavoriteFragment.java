package com.sai.udstore.Main.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sai.udstore.DataBase.Models.Product;
import com.sai.udstore.Main.Adapter.FavoriteRecyclerViewAdapater;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.Main.Products.ProductActivity;
import com.sai.udstore.Main.Products.ProductAddActivity;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.excomponents.Web;
import com.sai.udstore.sai.UF;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements FavoriteRecyclerViewAdapater.IProductCaller {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    private View mProgressCatView;
    FavoriteRecyclerViewAdapater adapter;
    ArrayList<Product> favs;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteFragment() {
        // Required empty public constructor
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


        ((MainActivity) getActivity()).curFragment = Settings.Fragments.Favorites;
        ((MainActivity) getActivity()).setMainTitle();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = v.findViewById(R.id.ff_lv_cat);
        mProgressCatView = v.findViewById(R.id.ff_pv_cat);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoriteRecyclerViewAdapater(favs, getContext(), this);
        recyclerView.setAdapter(adapter);
        populateFavs();
        return v;

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
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onProductClicked(int pos) {
        Intent intent = new Intent(getActivity(), ProductAddActivity.class);
        ProductActivity.prd = favs.get(pos);
        getActivity().startActivity(intent);

    }


    private void populateFavs() {
        mProgressCatView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    JSONObject js = new JSONObject();
//                    String uniq = android.provider.Settings.Secure.getString(getActivity().getContentResolver(),
//                            android.provider.Settings.Secure.ANDROID_ID);
//                    js.put("uniq", uniq);
////                    js.put("token", App.userProfile.getToken());
//
//                    String response = Web.send(Settings.Urls.FavoriteList, js.toString());
//                    favs = UF.UpdateFavoriteList(response);
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mProgressCatView.setVisibility(View.GONE);
//                            recyclerView.setVisibility(View.VISIBLE);
//                            adapter.setList(favs);
//                        }
//                    });
//                } catch (Exception errr) {
//
//                }
//            }
//        }).start();
    }
}
