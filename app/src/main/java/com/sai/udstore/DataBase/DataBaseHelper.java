package com.sai.udstore.DataBase;

import java.util.Iterator;

import org.json.JSONObject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sai.udstore.DataBase.Daos.Category_Dao;
import com.sai.udstore.DataBase.Daos.Certificate_Dao;
import com.sai.udstore.DataBase.Daos.Message_Dao;
import com.sai.udstore.DataBase.Daos.Product_Dao;
import com.sai.udstore.DataBase.Daos.Sefaresh_Dao;
import com.sai.udstore.DataBase.Daos.Team_Dao;
import com.sai.udstore.Prefrence.Daos.LastUpdate;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.DataBase.Daos.News_Dao;

public class DataBaseHelper extends SQLiteOpenHelper {
    Context con;

    public DataBaseHelper(Context context, String name, CursorFactory factory, int version) {

        super(context, name, factory, version);
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CreateTable(News_Dao.Create()));
            db.execSQL(CreateTable(Message_Dao.Create()));
            db.execSQL(CreateTable(Product_Dao.Create()));
            db.execSQL(CreateTable(Category_Dao.Create()));
            db.execSQL(CreateTable(Certificate_Dao.Create()));
            db.execSQL(CreateTable(Team_Dao.Create()));
            db.execSQL(CreateTable(Sefaresh_Dao.Create()));
        } catch (Exception e) {
            Log.e("DataBase Create", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int ov, int nv) {

        EB_Preference prefrence = new EB_Preference(con);
        LastUpdate lau = prefrence.getLastUpdate();
        lau.reset();
        db.execSQL("DROP TABLE IF EXISTS " + News_Dao.Table);
        db.execSQL("DROP TABLE IF EXISTS " + Message_Dao.Table);
        db.execSQL("DROP TABLE IF EXISTS " + Product_Dao.Table);
        db.execSQL("DROP TABLE IF EXISTS " + Category_Dao.Table);
        db.execSQL("DROP TABLE IF EXISTS " + Certificate_Dao.Table);
        db.execSQL("DROP TABLE IF EXISTS " + Team_Dao.Table);
        db.execSQL("DROP TABLE IF EXISTS " + Sefaresh_Dao.Table);
        this.onCreate(db);
    }

    private String CreateTable(JSONObject json) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE ");
        sb.append(json.getString("Table")).append("(");

        JSONObject Fields = json.getJSONObject("Fields");
        Iterator<String> keys = Fields.keys();

        String key = keys.next();
        sb.append(key).append(" ").append(Fields.getString(key));

        while (keys.hasNext()) {
            key = keys.next();
            sb.append(", ").append(key).append(" ").append(Fields.getString(key));
        }
        sb.append(");");
        return sb.toString();
    }
}
