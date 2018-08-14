package com.sai.udstore.DataBase.Daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.sai.udstore.DataBase.Models.Sefaresh_fact;
import com.sai.udstore.Settings;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sefaresh_fact_Dao {
	private SQLiteDatabase db;
	public static final String Table = "sefaresh_fact";
	private int soft_id = Settings.soft_id;

	public class Columns {
		public static final String ID = "_id";
		public static final String FISH_ID = "fishid";
		public static final String FISH_DAILY = "fishdaily";
		public static final String RES_ID = "resid";
		public static final String ORDER_TIME = "order_time";
		public static final String CACHIER_TIME = "cachier_time";
		public static final String ESTIMATE_TIME = "estimate_time";
		public static final String PREPARED_TIME = "prepared_time";
		public static final String GARSON_TIME = "garson_time";
		public static final String STATE = "stat";
		public static final String ADDR = "addr";
		public static final String PHONE = "phone";
		public static final String TABLE_NUM = "table_num";
		public static final String TOTAL_PRICE = "total_price";
	}

	public Sefaresh_fact_Dao(SQLiteDatabase db) {
		this.db = db;
	}

	public static JSONObject Create() throws Exception {
		JSONObject json = new JSONObject();
		JSONObject Fields = new JSONObject();

		json.put("Table", Table);

		Fields.put(Columns.ID, "INTEGER PRIMARY KEY autoincrement");
		Fields.put(Columns.FISH_ID, "INTEGER");
		Fields.put(Columns.FISH_DAILY, "INTEGER");
		Fields.put(Columns.RES_ID, "INTEGER");
		Fields.put(Columns.ORDER_TIME, "TEXT");
		Fields.put(Columns.CACHIER_TIME, "TEXT");
		Fields.put(Columns.ESTIMATE_TIME, "TEXT");
		Fields.put(Columns.PREPARED_TIME, "TEXT");
		Fields.put(Columns.GARSON_TIME, "TEXT");
		Fields.put(Columns.STATE, "INTEGER");
		Fields.put(Columns.ADDR, "TEXT");
		Fields.put(Columns.PHONE, "TEXT");
		Fields.put(Columns.TABLE_NUM, "TEXT");
		Fields.put(Columns.TOTAL_PRICE, "INTEGER");

		json.put("Fields", Fields);
		return json;
	}

	private ContentValues getContent(Sefaresh_fact a) {
		ContentValues c = new ContentValues();

//		c.put(Columns.FISH_ID, a.getFish_id());
//		c.put(Columns.FISH_DAILY, a.getFish_daily());
//		c.put(Columns.RES_ID, a.getRes_id());
//		c.put(Columns.ORDER_TIME, a.getOrder_time());
//		c.put(Columns.CACHIER_TIME, a.getCachier_time());
//		c.put(Columns.ESTIMATE_TIME, a.getEstimate_time());
//		c.put(Columns.PREPARED_TIME, a.getPrepared_time());
//		c.put(Columns.GARSON_TIME, a.getGarson_time());
//		c.put(Columns.STATE, a.getState());
//		c.put(Columns.ADDR, a.getAddress());
//		c.put(Columns.PHONE, a.getPhone());
//		c.put(Columns.TABLE_NUM, a.getTable_num());
//		c.put(Columns.TOTAL_PRICE, a.getTotal_price());

		return c;
	}

	private Sefaresh_fact get(Cursor c) {
		Sefaresh_fact a = new Sefaresh_fact();
		a.setId(c.getInt(c.getColumnIndex(Columns.ID)));
//		a.setFish_id(c.getInt(c.getColumnIndex(Columns.FISH_ID)));
//		a.setFish_daily(c.getInt(c.getColumnIndex(Columns.FISH_DAILY)));
//		a.setRes_id(c.getInt(c.getColumnIndex(Columns.RES_ID)));
//		a.setOrder_time(c.getInt(c.getColumnIndex(Columns.ORDER_TIME)));
//		a.setCachier_time(c.getInt(c.getColumnIndex(Columns.CACHIER_TIME)));
//		a.setEstimate_time(c.getInt(c.getColumnIndex(Columns.ESTIMATE_TIME)));
//		a.setPrepared_time(c.getInt(c.getColumnIndex(Columns.PREPARED_TIME)));
//		a.setGarson_time(c.getInt(c.getColumnIndex(Columns.GARSON_TIME)));
//		a.setAddress(c.getString(c.getColumnIndex(Columns.ADDR)));
//		a.setPhone(c.getString(c.getColumnIndex(Columns.PHONE)));
//		a.setTable_num(c.getString(c.getColumnIndex(Columns.TABLE_NUM)));
//		a.setTotal_price(c.getInt(c.getColumnIndex(Columns.TOTAL_PRICE)));

		return a;
	}

	public long AddReg(Sefaresh_fact Reg) {
		return db.insertWithOnConflict(Table, null, getContent(Reg),
				SQLiteDatabase.CONFLICT_REPLACE);
	}

	public void addRegs(List<Sefaresh_fact> Regs) {
		for (int i = 0; i <= Regs.size(); i++) {
			AddReg(Regs.get(i));
		}
	}

	public long DeleteReg(int id) {
		return db.delete(Table, Columns.ID + " = " + id, null);
	}

	public long UpdateReg(Sefaresh_fact f) {
		if (GetReg(f.getId()) != null)
			return db.update(Table, getContent(f),
					Columns.ID + " = " + f.getId(), null);
		return -1;
	}

	public void UpdateRegs(List<Sefaresh_fact> Regs) {
		for (int i = 0; i < Regs.size(); i++) {
			UpdateReg(Regs.get(i));
		}
	}

	public Sefaresh_fact GetReg(int id) {
		Cursor c = db.query(Table, null, Columns.ID + " = " + id, null, null,
				null, null);
		if (c.moveToFirst())
			return get(c);
		return null;
	}

	public List<Sefaresh_fact> AllReg() {
		List<Sefaresh_fact> Regs = new ArrayList<Sefaresh_fact>();
		Cursor c = db.query(Table, null, null, null, null, null, null);
		if (c.moveToFirst()) {
			do {
				Regs.add(get(c));
			} while (c.moveToNext());
		}
		return Regs;
	}

	public boolean ExistReg(int id) {
		Cursor cursor;
		if (id != -1) {
			cursor = db.query(Table, null, Columns.ID + "=" + id, null, null,
					null, null);
		} else {
			cursor = db.query(Table, null, null, null, null, null, null);
		}

		if (cursor.getCount() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Sefaresh_fact> getAll_End_Reg(int resid) {
		String select;
		if (resid == -1) {
			select = "SELECT * FROM " + Table + " WHERE " + Columns.STATE
					+ " >= " + soft_id + " ;";

		} else {
			select = "SELECT * FROM " + Table + " WHERE " + Columns.STATE
					+ " = " + resid + " AND " + Columns.STATE + " >= "
					+ soft_id + " ;";
		}

		// String select = "SELECT *FROM " + REG_TABLE + " ;";
		Cursor cursor = db.rawQuery(select, null);
		if (cursor.getCount() > 0) {
			List<Sefaresh_fact> Regs = new ArrayList<Sefaresh_fact>();
			if (cursor.moveToFirst()) {
				do {
					Regs.add(get(cursor));
				} while (cursor.moveToNext());
			}
			return Regs;
		} else {
			return new ArrayList<Sefaresh_fact>();
		}
	}

	public List<Sefaresh_fact> getAll_notEnd_Reg() {
		// String select = "SELECT *FROM " + REG_TABLE + " WHERE " + REG_STATE
		// + " >= " + Constant.REG_STATE_SERVER + " AND " + REG_STATE
		// + " <= " + Constant.REG_STATE_KITCHEN + " ;";
		String select = "SELECT * FROM " + Table;
		Cursor cursor = db.rawQuery(select, null);
		if (cursor.getCount() > 0) {
			List<Sefaresh_fact> Regs = new ArrayList<Sefaresh_fact>();
			if (cursor.moveToFirst()) {
				do {
					Regs.add(get(cursor));
				} while (cursor.moveToNext());
			}
			return Regs;
		} else {
			return null;
		}
	}

	public void Change_state_reg(int id, int state) {
		String where = Columns.ID + "=" + id;
		ContentValues contentValues = new ContentValues();
		contentValues.put(Columns.STATE, state);
		db.update(Table, contentValues, where, null);
	}
}
