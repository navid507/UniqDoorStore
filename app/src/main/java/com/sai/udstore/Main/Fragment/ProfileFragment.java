package com.sai.udstore.Main.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.Prefrence.Daos.User;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.sai.UF;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView nameTV, creditTV, creditstrTV, offstrTV, offTV;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    Button ordersBT, exitBT, downloadBT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).curFragment = Settings.Fragments.Profile;
        ((MainActivity) getActivity()).setMainTitle();
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        View root;
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        LinearLayout mll = root.findViewById(R.id.fp_ll_main);
        UF.setAllFonts(App.appFont, mll);

        ordersBT = v.findViewById(R.id.fp_btn_orders);
        exitBT = v.findViewById(R.id.fp_btn_exit);
        downloadBT = v.findViewById(R.id.fp_btn_download);
        ordersBT.setTypeface(App.appFont);
        exitBT.setTypeface(App.appFont);
        downloadBT.setTypeface(App.appFont);
        nameTV = v.findViewById(R.id.fp_tv_name);
        creditTV = v.findViewById(R.id.fp_tv_credit);
        creditstrTV = v.findViewById(R.id.fp_tv_credit_str);
        offstrTV = v.findViewById(R.id.fp_tv_off_str);
        offTV = v.findViewById(R.id.fp_tv_off);
        creditTV.setTypeface(App.appFont);
        nameTV.setTypeface(App.appFont);
        creditstrTV.setTypeface(App.appFont);
        offstrTV.setTypeface(App.appFont);
        creditTV.setText(UF.getPriceFormat(App.userProfile.getCredit(), "fa") + " ریال");
        offTV.setText(UF.getPriceFormat(App.userProfile.getDiscount(), "fa") + " درصد");

        if (App.userProfile != null) {
            nameTV.setText(String.format(getString(R.string.profile_text), App.userProfile.getDisplayName()));

            if (App.userProfile.getPicContentType().length() > 1) {
                ImageView iv = v.findViewById(R.id.fp_iv_);
                Picasso.with(getContext())
                        .load(App.userProfile.getPicContentType())
                        .error(R.drawable.logo)
                        .into(iv);

            }
        }
        ordersBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrders();
            }
        });
        exitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.userProfile = null;
                EB_Preference prefrence = new EB_Preference(getContext());
                User userp = prefrence.User();
                userp.setRegState(Settings.Register_State.Nothing);
                ((MainActivity) getActivity()).doRegister();
            }
        });
        downloadBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).displayView(Settings.Fragments.Download, "", ",", "");
            }
        });

        return v;
    }

    public void showOrders() {
        ((MainActivity) getActivity()).displayView(Settings.Fragments.Invoices, "", "", "");
    }

}
