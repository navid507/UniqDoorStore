package com.sai.udstore.DataBase.Daos;

/**
 * Created by navid on 4/5/2017.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sai.udstore.DataBase.Models.Category;
import com.sai.udstore.Settings;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Category_Dao {
    private SQLiteDatabase db;
    public static final String Table = "category";

    public class Columns {
        public static final String Id = "id";
        public static final String Name = "name";
        public static final String Description = "desc";
        public static final String Banner = "banner";
        public static final String Parent_id = "parent_id";
        public static final String Type = "type";
        public static final String State = "state";
        public static final String Image = "image";
    }

    public Category_Dao(SQLiteDatabase db) {
        this.db = db;
    }

    public static JSONObject Create() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject Fields = new JSONObject();

        json.put("Table", Table);

        Fields.put(Columns.Id, "INTEGER PRIMARY KEY");
        Fields.put(Columns.Name, "TEXT");
        Fields.put(Columns.Description, "TEXT");
        Fields.put(Columns.Banner, "TEXT");
        Fields.put(Columns.Type, "INTEGER");
        Fields.put(Columns.Parent_id, "INTEGER");
        Fields.put(Columns.Parent_id, "INTEGER");
        Fields.put(Columns.Image, "TEXT");
        Fields.put(Columns.State, "INTEGER");

        json.put("Fields", Fields);
        return json;
    }

    private ContentValues getContent(Category category) {
        ContentValues c = new ContentValues();
        c.put(Columns.Id, category.getId());
        c.put(Columns.Name, category.getName());
        c.put(Columns.Description, category.getDesc());
        c.put(Columns.Banner, category.getBanner());
        c.put(Columns.Type, category.getType());
        c.put(Columns.Parent_id, category.getParent_id());
        c.put(Columns.Image, category.getImage());
        c.put(Columns.State, category.isState() ? 1 : 0);
        return c;
    }

    private Category getCategory(Cursor c) {
        Category category = new Category();
        category.setId(c.getInt(c.getColumnIndex(Columns.Id)));
        category.setName(c.getString(c.getColumnIndex(Columns.Name)));
        category.setDesc(c.getString(c.getColumnIndex(Columns.Description)));
        category.setBanner(c.getString(c.getColumnIndex(Columns.Banner)));
        category.setType(c.getInt(c.getColumnIndex(Columns.Type)));
        category.setParent_id(c.getInt(c.getColumnIndex(Columns.Parent_id)));
        category.setImage(c.getString(c.getColumnIndex(Columns.Image)));
        category.setState(((c.getInt(c.getColumnIndex(Columns.State)) == 1) ? true : false));
        return category;
    }

    public long Add(Category category) {
        return db.insert(Table, null, getContent(category));
    }

    public long Update(Category category) {
        return db.update(Table, getContent(category), Columns.Id + "=?", new String[]{category.getId() + ""});
    }

    public long UpdateExist(Category category) {
        Cursor c = db.query(Table, null, Columns.Id + "=" + category.getId(), null, null, null, null);
        if (c.moveToFirst()) {
            return Update(category);
        } else {
            return Add(category);
        }
    }

    public Category Find(long id) {
        Cursor c = db.query(Table, null, Columns.Id + "=" + id, null, null, null, null);
        if (c.moveToFirst()) {
            return getCategory(c);
        } else {
            return null;
        }
    }

    public List<Category> All(long barber, long styler) {

        List<Category> f = new ArrayList<Category>();
        String select = "SELECT * FROM " + Table + " where "
                + Columns.State + " = " + Settings.State_Available.Exist
                + " ORDER BY " + Columns.Id + ";";
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                f.add(getCategory(cursor));
            } while (cursor.moveToNext());
        }
        return f;
    }

    public List<Category> All(long soft_id) {

        List<Category> f1 = new ArrayList<Category>();
        String select1 = "SELECT * FROM " + Table + ";";

        Cursor cursor1 = db.rawQuery(select1, null);

        if (cursor1.moveToFirst()) {
            do {
                f1.add(getCategory(cursor1));
            } while (cursor1.moveToNext());
        }

        List<Category> f = new ArrayList<Category>();
        String select = "SELECT * FROM " + Table + " where " + Columns.State
                + " = " + Settings.State_Available.Exist + " ORDER BY " + Columns.Id + ";";

        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                f.add(getCategory(cursor));
            } while (cursor.moveToNext());
        }
        return f;
    }
}
