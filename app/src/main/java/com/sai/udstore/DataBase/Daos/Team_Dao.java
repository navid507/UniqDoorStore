package com.sai.udstore.DataBase.Daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sai.udstore.DataBase.Models.Team;
import com.sai.udstore.Settings;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Team_Dao {
    private SQLiteDatabase db;
    public static final String Table = "team";

    public class Columns {
        public static final String Id = "id";
        public static final String Name = "name";
        public static final String Decs = "desc";
        public static final String Img_Address = "image";
        public static final String State = "state";
        public static final String Priority = "prio";
    }

    public Team_Dao(SQLiteDatabase db) {
        this.db = db;
    }

    public static JSONObject Create() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject Fields = new JSONObject();

        json.put("Table", Table);

        Fields.put(Columns.Id, "INTEGER PRIMARY KEY");
        Fields.put(Columns.Name, "TEXT");
        Fields.put(Columns.Decs, "TEXT");
        Fields.put(Columns.Img_Address, "TEXT");
        Fields.put(Columns.State, "INTEGER");
        Fields.put(Columns.Priority, "INTEGER");

        json.put("Fields", Fields);
        return json;
    }

    private ContentValues getContent(Team f) {
        ContentValues c = new ContentValues();
        c.put(Columns.Id, f.getId());
        c.put(Columns.Name, f.getName());
        c.put(Columns.Decs, f.getDesc());
        c.put(Columns.Img_Address, f.getImg_Address());
        c.put(Columns.Priority, f.getPriority());
        c.put(Columns.State, f.isState() ? 1 : 0);
        return c;
    }

    private Team getTeam(Cursor c) {
        Team f = new Team();
        f.setId(c.getLong(c.getColumnIndex(Columns.Id)));
        f.setPriority(c.getInt(c.getColumnIndex(Columns.Priority)));
        f.setName(c.getString(c.getColumnIndex(Columns.Name)));
        f.setDesc(c.getString(c.getColumnIndex(Columns.Decs)));
        f.setImg_Address(c.getString(c.getColumnIndex(Columns.Img_Address)));
        f.setState(((c.getInt(c.getColumnIndex(Columns.State)) == 1) ? true : false));
        return f;
    }

    public long Add(Team f) {
        return db.insert(Table, null, getContent(f));
    }

    public long Update(Team f) {
        return db.update(Table, getContent(f), Columns.Id + "=?", new String[]{f.getId() + ""});
    }

    public Team find(long id) {
        Cursor c = db.query(Table, null, Columns.Id + "=" + id, null, null, null, null);
        if (c.moveToFirst()) {
            return getTeam(c);
        } else {
            return null;
        }
    }

    public long UpdateExist(Team f) {
        Cursor c = db.query(Table, null, Columns.Id + "=" + f.getId(), null, null, null, null);
        if (c.moveToFirst()) {
            return Update(f);
        } else {
            return Add(f);
        }
    }

    public List<Team> All(long barber) {

        List<Team> f = new ArrayList<Team>();
//        String select = "SELECT * FROM " + Table + " where " + Columns.Barber + " = " + barber + " AND " + Columns.State
//                + " = " + Settings.State_Available.Exist + " ORDER BY " + Columns.Priority + ";";

        String select = "SELECT * FROM " + Table + " where "  + Columns.State
                + " = " + Settings.State_Available.Exist + " ORDER BY " + Columns.Priority + ";";


        Cursor cursor = db.rawQuery(select, null);

        // Cursor cursor = db.query(Table, null, Columns.Hotel + "=?",
        // new String[] { hotel + "" }, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                f.add(getTeam(cursor));
            } while (cursor.moveToNext());
        }

        return f;
    }
}
