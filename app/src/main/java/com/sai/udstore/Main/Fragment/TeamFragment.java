package com.sai.udstore.Main.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai.udstore.DataBase.Daos.Team_Dao;
import com.sai.udstore.DataBase.DataBase;
import com.sai.udstore.DataBase.Models.Team;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.sai.UF;

import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {
    GridView gridView;
    MainActivity ma;
    Typeface tf;

    // private static final String IMAGE_CACHE_DIR_Styler = Settings.ImageCachDir.Styler_Full;
    int mImageThumbSize;

    public TeamFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).curFragment = Settings.Fragments.TeamDetail;
        ((MainActivity) getActivity()).setMainTitle();
        ma = (MainActivity) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_team, container, false);
        gridView = (GridView) rootView.findViewById(R.id.ft_grid_team);
        TextView title = (TextView) rootView.findViewById(R.id.ft_tv_title_team);
        DataBase db = new DataBase(getActivity());
        db.open();
        Team_Dao plda = db.getTeam_Dao();
        List<Team> allModels = plda.All(Settings.soft_id);
        db.close();
        GridViewImageAdapter Adapter = new GridViewImageAdapter(getActivity(), allModels);
        gridView.setAdapter(Adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ma.setTeamDetails(position);

            }
        });
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // setup imagefetcher for stylers
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.team_thumbnail_size);
        super.onCreate(savedInstanceState);
    }

    public class GridViewImageAdapter extends BaseAdapter {

        private List<Team> teams = new ArrayList<Team>();
        private Context context;

        public GridViewImageAdapter(Context c, List<Team> filePaths) {
            this.teams = filePaths;
            this.context = c;
        }

        @Override
        public int getCount() {
            return this.teams.size();
        }

        @Override
        public Object getItem(int position) {
            return this.teams.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            ImageView img;
            LayoutInflater inf;
            if (convertView == null) {
                inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inf.inflate(R.layout.adapter_team_grid, parent, false);
            }

            img = (ImageView) convertView.findViewById(R.id.asg_image);
            TextView name = (TextView) convertView.findViewById(R.id.asg_name);
            Team cf = teams.get(pos);
            name.setText(cf.getName());

            String pathUrlst = cf.getImg_Address();
            UF.loadandsave(getContext(), Settings.Path.Team, cf.getImg_Address(), img, pathUrlst, mImageThumbSize, mImageThumbSize, R.drawable.logo, R.drawable.logo);

            return convertView;
        }
    }
}
