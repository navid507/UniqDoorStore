package com.sai.udstore.DataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.sai.udstore.DataBase.Daos.Category_Dao;
import com.sai.udstore.DataBase.Daos.Certificate_Dao;
import com.sai.udstore.DataBase.Daos.Message_Dao;
import com.sai.udstore.DataBase.Daos.News_Dao;
import com.sai.udstore.DataBase.Daos.Product_Dao;
import com.sai.udstore.DataBase.Daos.Sefaresh_Dao;
import com.sai.udstore.DataBase.Daos.Team_Dao;
import com.sai.udstore.Settings;

public class DataBase {

    public static final String DATABASE_NAME = Settings.DataBase;
    private static final int DATABASE_VERSION = 2;
    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public DataBase(Context context) {
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        if (db != null && db.isOpen()) {
            return;
        }
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        if (db.isOpen())
            db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public News_Dao getNews_Dao() {
        return new News_Dao(db);
    }

    public Message_Dao getMessage_Dao() {
        return new Message_Dao(db);
    }

    public Product_Dao getProduct_Dao() {
        return new Product_Dao(db);
    }

    public Category_Dao getCategory_Dao() {
        return new Category_Dao(db);
    }

    public Certificate_Dao getCertificate_Dao() {
        return new Certificate_Dao(db);
    }

    public Team_Dao getTeam_Dao() {
        return new Team_Dao(db);
    }

    public Sefaresh_Dao getSefaresh_Dao() {
        return new Sefaresh_Dao(db);
    }
}
