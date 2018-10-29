package com.sai.udstore.Main.Fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.sai.JustifiedTextView;
import com.sai.udstore.sai.UF;


public class AboutFragment extends Fragment {

    private TextView title_program,title_abaut;//, site_program;
    private JustifiedTextView comment;
    private ImageView instagram, telegram, facebook;//, linkedin;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
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
        ((MainActivity) getActivity()).curFragment = Settings.Fragments.AboutUs;
        ((MainActivity) getActivity()).setMainTitle();
        View rv = inflater.inflate(R.layout.fragment_about, container, false);

        ((MainActivity) getActivity()).setMainTitle(R.string.main_text_about_us);
        title_abaut=rv.findViewById(R.id.fa_tv_title_about);
        title_abaut.setTypeface(App.appFont);

        title_program = (TextView) rv.findViewById(R.id.fh_tv_title_program);
//        site_program = (TextView) rv.findViewById(R.id.fh_tv_site_program);
        comment = rv.findViewById(R.id.fh_tv_comment_about);
        comment.setTypeFace(App.appFont);
        comment.setLineSpacing(14);
        comment.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        comment.setText(R.string.contact_us);

        title_program.setTypeface(App.appFont);
//        site_program.setTypeface(bhoma);

//        comment.setText(getResources().getString(R.string.about));
//        comment.setLineSpacing(16);
//        comment.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        title_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.site_info)));
                startActivity(browserIntent);
            }
        });

//        site_program.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.site_info)));
//                startActivity(browserIntent);
//            }
//        });
        instagram = (ImageView) rv.findViewById(R.id.fc_img_instagram);
        telegram = (ImageView) rv.findViewById(R.id.fc_img_telegram);
        facebook = (ImageView) rv.findViewById(R.id.fc_img_facebook);
        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UF.callApps(getString(R.string.telegram), "org.telegram.messenger", getContext());
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UF.callApps(getString(R.string.instagram), "com.instagram.android", getContext());
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UF.callApps(getString(R.string.facebook), "com.facebook.katana", getContext());

            }
        });
        return rv;
    }
}
