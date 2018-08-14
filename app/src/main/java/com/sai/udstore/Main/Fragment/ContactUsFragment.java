package com.sai.udstore.Main.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.sai.UF;

public class ContactUsFragment extends Fragment {

    private TextView address, tel, mob;//, title_email, email, title_site, site;
    //private LinearLayout site_linear, email_linear, tel_linear, mob_linear;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    public static ContactUsFragment newInstance(String param1, String param2) {
        ContactUsFragment fragment = new ContactUsFragment();
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
        ((MainActivity) getActivity()).curFragment = Settings.Fragments.ContactUs;
        ((MainActivity) getActivity()).setMainTitle();
        View rv = inflater.inflate(R.layout.fragment_contact_us, container, false);


        address = (TextView) rv.findViewById(R.id.fc_tv_addr);
//        title_address = (TextView) rv.findViewById(R.id.fc_tv_title_add_contact);
//        title_phone = (TextView) rv.findViewById(R.id.fc_tv_title_tel_contact);
        tel = (TextView) rv.findViewById(R.id.fc_tv_tel);
        mob = (TextView) rv.findViewById(R.id.fc_tv_mobile);
//        title_email = (TextView) rv.findViewById(R.id.fc_tv_title_email_contact);
//        email = (TextView) rv.findViewById(R.id.fc_tv_email_contact);
//        title_site = (TextView) rv.findViewById(R.id.fc_tv_title_site_contact);
//        site = (TextView) rv.findViewById(R.id.fc_tv_site_contact);
//        linkedin = (ImageView) rv.findViewById(R.id.fc_img_linkedin);
//        site_linear = (LinearLayout) rv.findViewById(R.id.fc_linear_site_contact);
//        email_linear = (LinearLayout) rv.findViewById(R.id.fc_linear_email_contact);
//        tel_linear = (LinearLayout) rv.findViewById(R.id.linear_tel_contact);
//        mob_linear = (LinearLayout) rv.findViewById(R.id.linear_mob_contact);

        address.setTypeface(App.appFont);
//        title_address.setTypeface(vazir);
//        title_phone.setTypeface(vazir);
        tel.setTypeface(App.appFont);
        mob.setTypeface(App.appFont);
//        title_email.setTypeface(vazir);
//        email.setTypeface(vazir);
//        title_site.setTypeface(vazir);
//        site.setTypeface(vazir);

        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UF.doCall(getString(R.string.contact_tell), getContext());
            }
        });

        mob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UF.doCall(getString(R.string.contact_mobile), getContext());
            }
        });

//        site_linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.site_info)));
//                startActivity(browserIntent);
//            }
//        });
//
//        email_linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.email)});
//                emailIntent.setType("text/html");
//                startActivity(Intent.createChooser(emailIntent, "Email:"));
//            }
//        });


//
//        linkedin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //UF.callApps(getString(R.string.linkedin), "com.linkedin.android", getContext());
//            }
//        });
        return rv;
    }
}
