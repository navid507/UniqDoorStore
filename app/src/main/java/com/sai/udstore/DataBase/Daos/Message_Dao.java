package com.sai.udstore.DataBase.Daos;

/**
 * Created by DANIAL on 27/03/2017.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sai.udstore.DataBase.Models.Message;
import com.sai.udstore.Settings;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Message_Dao {
    private SQLiteDatabase db;
    public static final String Table = "message";

    public class Columns {
        public static final String Id = "id";
        public static final String Subject = "subject";
        public static final String Content = "content";
        public static final String Image = "image";
        public static final String State = "state";
    }

    public Message_Dao(SQLiteDatabase db) {
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

        json.put("Fields", Fields);
        return json;
    }

    private ContentValues getContent(Message message) {
        ContentValues c = new ContentValues();
        c.put(Columns.Id, message.getId());
        c.put(Columns.Subject, message.getSubject());
        c.put(Columns.Content, message.getContent());
        c.put(Columns.Image, message.getImage());
        c.put(Columns.State, message.isState() ? 1 : 0);

        return c;
    }

    private Message getMessage(Cursor c) {
        Message message = new Message();
        message.setId(c.getLong(c.getColumnIndex(Columns.Id)));
        message.setSubject(c.getString(c.getColumnIndex(Columns.Subject)));
        message.setContent(c.getString(c.getColumnIndex(Columns.Content)));
        message.setImage(c.getString(c.getColumnIndex(Columns.Image)));
        message.setState(((c.getInt(c.getColumnIndex(Columns.State)) == 1) ? true : false));
        return message;
    }

    public long Add(Message message) {
        return db.insert(Table, null, getContent(message));
    }

    public long Update(Message message) {
        return db.update(Table, getContent(message), Columns.Id + "=?", new String[]{message.getId() + ""});
    }

    public long UpdateExist(Message message) {
        Cursor c = db.query(Table, null, Columns.Id + "=" + message.getId(), null, null, null, null);
        if (c.moveToFirst()) {
            return Update(message);
        } else {
            return Add(message);
        }
    }

    public Message Find(long id) {
        Cursor c = db.query(Table, null, Columns.Id + "=" + id, null, null, null, null);
        if (c.moveToFirst()) {
            return getMessage(c);
        } else {
            return null;
        }
    }

    public List<Message> All(long barber, long styler) {

        List<Message> f = new ArrayList<Message>();
        String select = "SELECT * FROM " + Table + " where "
                + Columns.State + " = " + Settings.State_Available.Exist + ";";
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                f.add(getMessage(cursor));
            } while (cursor.moveToNext());
        }
        return f;
    }

    public List<Message> All(long soft_id) {

        List<Message> f1 = new ArrayList<Message>();
        String select1 = "SELECT * FROM " + Table + ";";

        Cursor cursor1 = db.rawQuery(select1, null);

        if (cursor1.moveToFirst()) {
            do {
                f1.add(getMessage(cursor1));
            } while (cursor1.moveToNext());
        }

        List<Message> f = new ArrayList<Message>();
        String select = "SELECT * FROM " + Table + " where " + Columns.State
                + " = " + Settings.State_Available.Exist + ";";

        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                f.add(getMessage(cursor));
            } while (cursor.moveToNext());
        }
        return f;
    }
}
