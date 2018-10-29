package com.sai.udstore.Main.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sai.udstore.DataBase.Daos.Certificate_Dao;
import com.sai.udstore.DataBase.Daos.Team_Dao;
import com.sai.udstore.DataBase.DataBase;
import com.sai.udstore.DataBase.Models.Certificate;
import com.sai.udstore.DataBase.Models.Team;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.sai.UF;

import java.util.List;

public class TeamDetailFragment extends Fragment {
    ViewPager viewerpage;
    int currentPage;

    int mImageThumbSize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).curFragment = Settings.Fragments.Team;
        ((MainActivity) getActivity()).setMainTitle();
        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);
        viewerpage = (ViewPager) rootView.findViewById(R.id.pager);
        DataBase db = new DataBase(getActivity());
        db.open();
        Team_Dao hs = db.getTeam_Dao();
        List<Team> stylers = hs.All(Settings.soft_id);
        db.close();
        Styler_Adapter Adapter = new Styler_Adapter(getActivity(), stylers);
        viewerpage.setAdapter(Adapter);
        viewerpage.setCurrentItem(currentPage);
        return rootView;
    }

    private static final String ARG_PAGE = "param2"; //

    public static TeamDetailFragment newInstance(int page) {
        TeamDetailFragment fragment = new TeamDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentPage = getArguments().getInt(ARG_PAGE, -1);
        }
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.team_thumbnail_size);

        super.onCreate(savedInstanceState);
    }

    private class Styler_Adapter extends PagerAdapter {
        private List<Team> Stylers;
        private LayoutInflater inflater;
        private Context context;

        @SuppressWarnings("unused")
        public Styler_Adapter(Context c, List<Team> paths) {
            this.Stylers = paths;
            this.inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.context = c;
        }

        @Override
        public int getCount() {
            return this.Stylers.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View viewLayout = inflater.inflate(R.layout.adapter_team_detail, container, false);
            final Team cf = Stylers.get(position);

            TextView title = (TextView) viewLayout.findViewById(R.id.asd_name);
            title.setTypeface(App.appFont);
            TextView desc = (TextView) viewLayout.findViewById(R.id.asd_desc);
            desc.setTypeface(App.appFont);
            TextView certi = (TextView) viewLayout.findViewById(R.id.asd_certi_str);
            certi.setTypeface(App.appFont);
//			Typeface tf_te = Typeface.createFromAsset(getActivity().getAssets(), "font/tehran.ttf");


//			Button reserve_bt = (Button) viewLayout.findViewById(R.id.asd_bt_reserve);
//			reserve_bt.setTypeface(tf_te);

            title.setText(cf.getName());
            desc.setText(cf.getDesc());

            ListView certiListView;
            certiListView = (ListView) viewLayout.findViewById(R.id.asd_certfi_lv);

            DataBase db = new DataBase(getActivity());
            db.open();
            Certificate_Dao hs = db.getCertificate_Dao();
            List<Certificate> certlist = hs.All(Settings.soft_id, cf.getId());
            db.close();

            CertficationListAdapter adapter = new CertficationListAdapter(context, certlist);
            certiListView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(certiListView);
            ImageView img = (ImageView) viewLayout.findViewById(R.id.asd_image);
            String pathUrlst = cf.getImg_Address();
            UF.loadandsave(getContext(), Settings.Path.Certificate, cf.getImg_Address(), img, pathUrlst, mImageThumbSize, mImageThumbSize, R.drawable.logo, R.drawable.logo);

//			reserve_bt.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//
//					showRserve(cf.getId());
//				}
//			});
            ((ViewPager) container).addView(viewLayout);
            return viewLayout;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((LinearLayout) arg1);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(
                        new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public class CertficationListAdapter extends BaseAdapter {

        private Context context;
        private List<Certificate> certi_Items;

        public CertficationListAdapter(Context context, List<Certificate> certlist) {
            this.context = context;
            this.certi_Items = certlist;
        }

        @Override
        public int getCount() {
            return certi_Items.size();
        }

        @Override
        public Object getItem(int position) {
            return certi_Items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.adapter_team_certificate, null);
            }

            TextView txtDesc = (TextView) convertView.findViewById(R.id.asc_desc);
            txtDesc.setTypeface(App.appFont);

            txtDesc.setText(certi_Items.get(position).getCertificate());

            return convertView;
        }
    }
}
