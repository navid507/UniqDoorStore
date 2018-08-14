package com.sai.udstore.DataBase.Daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sai.udstore.DataBase.Models.Certificate;
import com.sai.udstore.Settings;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Certificate_Dao {
	private SQLiteDatabase db;
	public static final String Table = "cetificate";

	public class Columns {
		public static final String Id = "id";
		public static final String Styler = "styler";
		public static final String Barber = "barber";
		public static final String Certificate = "certificate";
		public static final String State = "state";
		public static final String Priority = "prio";
	}

	public Certificate_Dao(SQLiteDatabase db) {
		this.db = db;
	}

	public static JSONObject Create() throws Exception {
		JSONObject json = new JSONObject();
		JSONObject Fields = new JSONObject();

		json.put("Table", Table);

		Fields.put(Columns.Id, "INTEGER PRIMARY KEY");
		Fields.put(Columns.Styler, "INTEGER");
		Fields.put(Columns.Barber, "INTEGER");

		Fields.put(Columns.Certificate, "TEXT");
		Fields.put(Columns.State, "INTEGER");
		Fields.put(Columns.Priority, "INTEGER");

		json.put("Fields", Fields);
		return json;
	}

	private ContentValues getContent(Certificate f) {
		ContentValues c = new ContentValues();
		c.put(Columns.Id, f.getId());
		c.put(Columns.Styler, f.getStyler());
		c.put(Columns.Barber, f.getBarber());
		c.put(Columns.Certificate, f.getCertificate());
		c.put(Columns.Priority, f.getPriority());
		c.put(Columns.State, f.isState() ? 1 : 0);
		return c;
	}

	private Certificate getFacility(Cursor c) {
		Certificate f = new Certificate();
		f.setId(c.getLong(c.getColumnIndex(Columns.Id)));
		f.setStyler(c.getLong(c.getColumnIndex(Columns.Styler)));
		f.setBarber(c.getLong(c.getColumnIndex(Columns.Barber)));
		f.setPriority(c.getInt(c.getColumnIndex(Columns.Priority)));
		f.setCertificate(c.getString(c.getColumnIndex(Columns.Certificate)));
		f.setState(((c.getInt(c.getColumnIndex(Columns.State)) == 1) ? true : false));
		return f;
	}

	public long Add(Certificate f) {
		return db.insert(Table, null, getContent(f));
	}

	public long Update(Certificate f) {
		return db.update(Table, getContent(f), Columns.Id + "=?", new String[] { f.getId() + "" });
	}

	public long UpdateExist(Certificate f) {
		Cursor c = db.query(Table, null, Columns.Id + "=" + f.getId(), null, null, null, null);
		if (c.moveToFirst()) {
			return Update(f);
		} else {
			return Add(f);
		}
	}

	public List<Certificate> All(long barber, long styler) {

		List<Certificate> f = new ArrayList<Certificate>();
		String select = "SELECT * FROM " + Table + " where "
				+ Columns.Styler + " = " + styler + " AND " + Columns.State + " = " + Settings.State_Available.Exist
				+ " ORDER BY " + Columns.Priority + " DESC;";

		Cursor cursor = db.rawQuery(select, null);

		// Cursor cursor = db.query(Table, null, Columns.Hotel + "=?",
		// new String[] { hotel + "" }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				f.add(getFacility(cursor));
			} while (cursor.moveToNext());
		}

		return f;
	}

	public List<Certificate> All(long barber) {

		List<Certificate> f = new ArrayList<Certificate>();
		String select = "SELECT * FROM " + Table + " where " + Columns.Barber + " = " + barber + " AND " + Columns.State
				+ " = " + Settings.State_Available.Exist + " ORDER BY " + Columns.Priority + ";";

		Cursor cursor = db.rawQuery(select, null);

		// Cursor cursor = db.query(Table, null, Columns.Hotel + "=?",
		// new String[] { hotel + "" }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				f.add(getFacility(cursor));
			} while (cursor.moveToNext());
		}

		return f;
	}
}
