package com.sai.udstore.DataBase.Daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sai.udstore.DataBase.Models.News;
import com.sai.udstore.Settings;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class News_Dao {
    private SQLiteDatabase db;
    public static final String Table = "news";

    public class Columns {
        public static final String Id = "id";
        public static final String Subject = "subject";
        public static final String Content = "content";
        public static final String State = "state";
        public static final String Priority = "Priority";
        public static final String Image = "image";
    }

    public News_Dao(SQLiteDatabase db) {
        this.db = db;
    }

    public static JSONObject Create() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject Fields = new JSONObject();

        json.put("Table", Table);

        Fields.put(Columns.Id, "INTEGER PRIMARY KEY");
        Fields.put(Columns.Subject, "TEXT");
        Fields.put(Columns.Content, "TEXT");
        Fields.put(Columns.Image, "TEXT");
        Fields.put(Columns.State, "INTEGER");
        Fields.put(Columns.Priority, "INTEGER");

        json.put("Fields", Fields);
        return json;
    }

    private ContentValues getContent(News news) {
        ContentValues c = new ContentValues();
        c.put(Columns.Id, news.getId());
        c.put(Columns.Subject, news.getSubject());
        c.put(Columns.Content, news.getContent());
        c.put(Columns.Image, news.getImage());
        c.put(Columns.Priority, news.getPriority());
        c.put(Columns.State, news.isState() ? 1 : 0);
        return c;
    }

    private News getNews(Cursor c) {
        News news = new News();
        news.setId(c.getInt(c.getColumnIndex(Columns.Id)));
        news.setSubject(c.getString(c.getColumnIndex(Columns.Subject)));
        news.setContent(c.getString(c.getColumnIndex(Columns.Content)));
        news.setPriority(c.getInt(c.getColumnIndex(Columns.Priority)));
        news.setImage(c.getString(c.getColumnIndex(Columns.Image)));
        news.setState(((c.getInt(c.getColumnIndex(Columns.State)) == 1) ? true : false));
        return news;
    }

    public long Add(News news) {
        return db.insert(Table, null, getContent(news));
    }

    public long Update(News news) {
        return db.update(Table, getContent(news), Columns.Id + "=?", new String[]{news.getId() + ""});
    }

    public long UpdateExist(News news) {
        Cursor c = db.query(Table, null, Columns.Id + "=" + news.getId(), null, null, null, null);
        if (c.moveToFirst()) {
            return Update(news);
        } else {
            return Add(news);
        }
    }

    public News Find(long id) {
        Cursor c = db.query(Table, null, Columns.Id + "=" + id, null, null, null, null);
        if (c.moveToFirst()) {
            return getNews(c);
        } else {
            return null;
        }
    }

    public List<News> All(long barber, long styler) {

        List<News> f = new ArrayList<News>();
        String select = "SELECT * FROM " + Table + " where "
                + Columns.State + " = " + Settings.State_Available.Exist
                + " ORDER BY " + Columns.Priority + ";";
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                f.add(getNews(cursor));
            } while (cursor.moveToNext());
        }
        return f;
    }

    public List<News> All(long soft_id) {

        List<News> f1 = new ArrayList<News>();
        String select1 = "SELECT * FROM " + Table + ";";

        Cursor cursor1 = db.rawQuery(select1, null);

        if (cursor1.moveToFirst()) {
            do {
                f1.add(getNews(cursor1));
            } while (cursor1.moveToNext());
        }

        List<News> f = new ArrayList<News>();
        String select = "SELECT * FROM " + Table + " where " + Columns.State
                + " = " + Settings.State_Available.Exist + " ORDER BY " + Columns.Priority + ";";

        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                f.add(getNews(cursor));
            } while (cursor.moveToNext());
        }
        return f;
    }
}
