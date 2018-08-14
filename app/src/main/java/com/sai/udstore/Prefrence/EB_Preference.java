package com.sai.udstore.Prefrence;

import android.content.Context;
import android.content.SharedPreferences;

import com.sai.udstore.Prefrence.Daos.LastUpdate;
import com.sai.udstore.Prefrence.Daos.User;

public class EB_Preference {

	public final static String PREF_STATISTICS = "statistics";
	public SharedPreferences shp;

	public EB_Preference(Context context) {
		shp = context.getSharedPreferences(PREF_STATISTICS, Context.MODE_PRIVATE);
	}

	public LastUpdate getLastUpdate() {
		return new LastUpdate(shp);
	}

	public User User() {
		return new User(shp);
	}
}
