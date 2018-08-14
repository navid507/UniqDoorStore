package com.sai.udstore.DataBase.Daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sai.udstore.DataBase.Models.Product;
import com.sai.udstore.DataBase.Models.Sefaresh;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sefaresh_Dao {
    private SQLiteDatabase db;
    public static final String Table = "sefaresh";

    public class Columns {
        public static final String KEY_ID = "id";
        public static final String Product_ID = "pid";
        public static final String DATE = "date";
        public static final String DESC = "desc";
        public static final String COUNT = "count";
        public static final String PRICE = "price";
        public static final String NAME = "name";
        public static final String Image = "image";
        public static final String Subject = "subject";
        public static final String Discount = "discount";
    }

    public Sefaresh_Dao(SQLiteDatabase db) {
        this.db = db;
    }

    public static JSONObject Create() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject Fields = new JSONObject();

        json.put("Table", Table);

        Fields.put(Columns.KEY_ID, "INTEGER PRIMARY KEY autoincrement");
        Fields.put(Columns.Product_ID, "INTEGER");
        Fields.put(Columns.DATE, "TEXT");
        Fields.put(Columns.DESC, "TEXT");
        Fields.put(Columns.COUNT, "INTEGER");
        Fields.put(Columns.PRICE, "TEXT");
        Fields.put(Columns.NAME, "TEXT");
        Fields.put(Columns.Image, "TEXT");
        Fields.put(Columns.Subject, "TEXT");
        Fields.put(Columns.Discount, "TEXT");

        json.put("Fields", Fields);
        return json;
    }

    private ContentValues getContent(Sefaresh a) {
        ContentValues c = new ContentValues();

        c.put(Columns.Product_ID, a.getProduct_id());
        c.put(Columns.DATE, a.getDate());
        c.put(Columns.DESC, a.getCode());
        c.put(Columns.COUNT, a.getCount());
        c.put(Columns.PRICE, a.getPrice());
        c.put(Columns.NAME, a.getName());
        c.put(Columns.Image, a.getImage());
        c.put(Columns.Subject, a.getSubject());
        c.put(Columns.Discount, a.getDiscount());
        return c;
    }

    private Sefaresh get(Cursor c) {
        Sefaresh a = new Sefaresh();
        a.setId(c.getInt(c.getColumnIndex(Columns.KEY_ID)));
        a.setProduct_id(c.getInt(c.getColumnIndex(Columns.Product_ID)));
        a.setDate(c.getString(c.getColumnIndex(Columns.DATE)));
        a.setCode(c.getString(c.getColumnIndex(Columns.DESC)));
        a.setCount(c.getInt(c.getColumnIndex(Columns.COUNT)));
        a.setPrice(c.getString(c.getColumnIndex(Columns.PRICE)));
        a.setName(c.getString(c.getColumnIndex(Columns.NAME)));
        a.setImage(c.getString(c.getColumnIndex(Columns.Image)));
        a.setSubject(c.getString(c.getColumnIndex(Columns.Subject)));
        a.setDiscount(c.getString(c.getColumnIndex(Columns.Discount)));

        return a;
    }

    public long AddOrder(Sefaresh Order) {
        return db.insertWithOnConflict(Table, null, getContent(Order),
                SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void addOrders(List<Sefaresh> Orders) {
        for (int i = 0; i <= Orders.size(); i++) {
            AddOrder(Orders.get(i));
        }
    }

    public long DeleteOrder(int id) {
        return db.delete(Table, Columns.KEY_ID + " = " + id, null);
    }

    public long DeleteAllOrder() {
        return db.delete(Table, null, null);
    }

    public long UpdateOrder(Sefaresh f) {
        if (f.getCount() == 0) {
            return DeleteOrder(f.getId());
        } else {
            if (GetOrder(f.getId()) != null)
                return db.update(Table, getContent(f),
                        Columns.KEY_ID + " = " + f.getId(), null);
        }
        return -1;
    }

    public void UpdateOrders(List<Sefaresh> Orders) {
        for (int i = 0; i < Orders.size(); i++) {
            UpdateOrder(Orders.get(i));
        }
    }

    public Sefaresh GetOrderOfProduct(int pid) {
        Cursor c = db.query(Table, null, Columns.Product_ID + " = " + pid, null,
                null, null, null);
        if (c.moveToFirst())
            return get(c);
        return null;
    }

    public Sefaresh GetOrder(int id) {
        Cursor c = db.query(Table, null, Columns.KEY_ID + " = " + id, null,
                null, null, null);
        if (c.moveToFirst())
            return get(c);
        return null;
    }

    public String GetCount(int id) {
        Cursor c = db.query(Table, null, Columns.Product_ID + " = " + id, null,
                null, null, null);
        if (c.moveToFirst()) {
            Sefaresh o = get(c);
            return String.valueOf(o.getCount());
        }
        return "0";
    }

    public List<Sefaresh> AllOrder() {
        List<Sefaresh> Orders = new ArrayList<Sefaresh>();
        Cursor c = db.query(Table, null, null, null, null,
                null, null);
        if (c.moveToFirst()) {
            do {
                Orders.add(get(c));
            } while (c.moveToNext());
        }
        return Orders;
    }

    public List<Sefaresh> AllOrder(int reg) {
        List<Sefaresh> Orders = new ArrayList<Sefaresh>();
        Cursor c = db.query(Table, null, null, null, null,
                null, null);
        if (c.moveToFirst()) {
            do {
                Orders.add(get(c));
            } while (c.moveToNext());
        }
        return Orders;
    }

    public boolean ExistOrder(int id) {
        Cursor cursor;
        if (id != -1) {
            cursor = db.query(Table, null, Columns.KEY_ID + "=" + id, null,
                    null, null, null);
        } else {
            cursor = db.query(Table, null, null, null, null, null, null);
        }

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int AddCount(Product book) {
        Cursor c = db.query(Table, null, Columns.Product_ID + "=" + book.getId(), null, null, null, null,
                null);
        if (c.moveToFirst()) {
            Sefaresh o = get(c);
            o.setCount(o.getCount() + 1);
            UpdateOrder(o);
            return o.getCount();
        } else {
//            PersianCalendar persianCalendar = new PersianCalendar();
            Sefaresh o = new Sefaresh();
            o.setCount(1);
//            try {
//                o.setPrice(Integer.valueOf(book.getPrice()));
//            } catch (Exception ex) {
//                o.setPrice(0);
//            }
//            o.setDate(persianCalendar.getPersianShortDate());
//            o.setCode(book.getContent());
            o.setProduct_id(book.getId());
            o.setName(book.getName());
            o.setImage(book.getImage());
//            o.setSubject(book.getSubject());
//            o.setPrice(Integer.valueOf(book.getPrice()));
            AddOrder(o);
            return o.getCount();
        }
    }
    public int SetCount(Sefaresh s) {
        Cursor c = db.query(Table, null, Columns.Product_ID + "=" + s.getProduct_id(), null, null, null, null,
                null);
        if (c.moveToFirst()) {
            Sefaresh o = get(c);
            o.setCount(s.getCount());
            UpdateOrder(o);
            return o.getCount();
        } else {
//            PersianCalendar persianCalendar = new PersianCalendar();
//            Sefaresh o = new Sefaresh();
//            o.setCount(count);
//            o.setCode(desc);
//            o.setPrice(price);
//            o.setSubject(unit);
//            o.setProduct_id(id);
//            o.setName(name);
//            o.setImage(img);
//            o.setDiscount(tax);
            AddOrder(s);
            return s.getCount();
        }
    }
//    public int SetCount(int id, String name, String price, String tax, String desc, String unit, String img, int count) {
//        Cursor c = db.query(Table, null, Columns.Product_ID + "=" + id, null, null, null, null,
//                null);
//        if (c.moveToFirst()) {
//            Sefaresh o = get(c);
//            o.setCount(count);
//            UpdateOrder(o);
//            return o.getCount();
//        } else {
////            PersianCalendar persianCalendar = new PersianCalendar();
//            Sefaresh o = new Sefaresh();
//            o.setCount(count);
//            o.setCode(desc);
//            o.setPrice(price);
//            o.setSubject(unit);
//            o.setProduct_id(id);
//            o.setName(name);
//            o.setImage(img);
//            o.setDiscount(tax);
//            AddOrder(o);
//            return o.getCount();
//        }
//    }

    public int AddCount(int id, String price, String name) {
        Cursor c = db.query(Table, null, Columns.Product_ID + "=" + id, null, null, null, null,
                null);
        if (c.moveToFirst()) {
            Sefaresh o = get(c);
            o.setCount(o.getCount() + 1);
            UpdateOrder(o);
            return o.getCount();
        } else {
//            PersianCalendar persianCalendar = new PersianCalendar();
            Sefaresh o = new Sefaresh();
            o.setCount(1);
            try {
                o.setPrice(price);
            } catch (Exception ex) {
                o.setPrice("0");
            }
//            o.setDate(persianCalendar.getPersianShortDate());
            o.setCode("");
            o.setProduct_id(id);
            o.setName(name);
            AddOrder(o);
            return o.getCount();
        }
    }

    public int SubCount(int id) {
        Cursor c = db.query(Table, null, Columns.Product_ID + "=" + id, null, null, null, null, null);
        if (c.moveToFirst()) {
            Sefaresh o = get(c);
            o.setCount(o.getCount() - 1);
            if (o.getCount() == 0) {
                DeleteOrder(o.getId());
            } else {
                UpdateOrder(o);
            }

            return o.getCount();
        } else {
            return 0;
        }
    }

    public List<Sefaresh> All() {

        List<Sefaresh> f = new ArrayList<Sefaresh>();
        String select = "SELECT * FROM " + Table;
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                f.add(get(cursor));
            } while (cursor.moveToNext());
        }
        return f;
    }

//    private Sefaresh get_show(Cursor c) {
//        Sefaresh a = new Sefaresh();
//        a.setProduct_id(c.getInt(c.getColumnIndex("pid")));
//        a.setId(c.getInt(c.getColumnIndex("id")));
//        a.setCount(c.getInt(c.getColumnIndex("count")));
//        a.setName(c.getString(c.getColumnIndex("name")));
//        a.setSubject(c.getString(c.getColumnIndex("subject")));
//        a.setImage(c.getString(c.getColumnIndex("image")));
//        a.setPrice(c.getInt(c.getColumnIndex("price")));
//        return a;
//    }
}