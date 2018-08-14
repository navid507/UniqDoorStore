package com.sai.udstore.DataBase.Daos;

/**
 * Created by navid on 4/5/2017.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sai.udstore.DataBase.Models.Product;
import com.sai.udstore.Settings;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Product_Dao {
    private SQLiteDatabase db;
    public static final String Table = "product";

    public class Columns {
        public static final String Id = "id";
        public static final String Name = "name";
        public static final String Comment = "comment";
        public static final String Brand = "brand";
        public static final String Unit = "unit";
        public static final String Brandid = "brandid";
        public static final String Image = "image";
        public static final String Cat_id = "cat_id";
        public static final String State = "state";
        public static final String Parent = "parent";
    }

    public Product_Dao(SQLiteDatabase db) {
        this.db = db;
    }

    public static JSONObject Create() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject Fields = new JSONObject();

        json.put("Table", Table);

        Fields.put(Columns.Id, "INTEGER PRIMARY KEY");
        Fields.put(Columns.Name, "TEXT");
        Fields.put(Columns.Comment, "TEXT");
        Fields.put(Columns.Image, "TEXT");
        Fields.put(Columns.State, "INTEGER");
        Fields.put(Columns.Cat_id, "INTEGER");
        Fields.put(Columns.Brand, "TEXT");
        Fields.put(Columns.Brandid, "INTEGER");
        Fields.put(Columns.Unit, "TEXT");
        Fields.put(Columns.Parent, "TEXT");

        json.put("Fields", Fields);
        return json;
    }

    private ContentValues getContent(Product product) {
        ContentValues c = new ContentValues();
        c.put(Columns.Id, product.getId());
        c.put(Columns.Name, product.getName());
//        c.put(Columns.Comment, product.getComment());
        c.put(Columns.Image, product.getImage());
//        c.put(Columns.Unit, product.getUnit());
        c.put(Columns.State, product.isState() ? 1 : 0);
//        c.put(Columns.Cat_id, product.getCat_id());
//        c.put(Columns.Brand, product.getBrand());
//        c.put(Columns.Brandid, product.getBrandid());
//        c.put(Columns.Parent, product.getParent());
        return c;
    }

    private Product getProduct(Cursor c) {
        Product product = new Product();
        product.setId(c.getInt(c.getColumnIndex(Columns.Id)));
        product.setName(c.getString(c.getColumnIndex(Columns.Name)));
//        product.setComment(c.getString(c.getColumnIndex(Columns.Comment)));
//        product.setBrand(c.getString(c.getColumnIndex(Columns.Brand)));
        product.setImage(c.getString(c.getColumnIndex(Columns.Image)));
        product.setState(((c.getInt(c.getColumnIndex(Columns.State)) == 1) ? true : false));
//        product.setBrandid(c.getInt(c.getColumnIndex(Columns.Brandid)));
//        product.setCat_id(c.getInt(c.getColumnIndex(Columns.Cat_id)));
//        product.setUnit(c.getString(c.getColumnIndex(Columns.Unit)));
//        product.setParent(c.getString(c.getColumnIndex(Columns.Parent)));

        return product;
    }

    public long Add(Product product) {
        return db.insert(Table, null, getContent(product));
    }

    public long Update(Product product) {
        return db.update(Table, getContent(product), Columns.Id + "=?", new String[]{product.getId() + ""});
    }

    public long UpdateExist(Product product) {
        Cursor c = db.query(Table, null, Columns.Id + "=" + product.getId(), null, null, null, null);
        if (c.moveToFirst()) {
            return Update(product);
        } else {
            return Add(product);
        }
    }

    public Product Find(int id) {
        Cursor c = db.query(Table, null, Columns.Parent + "=" + id, null, null, null, null);
        if (c.moveToFirst()) {
            return getProduct(c);
        } else {
            return null;
        }
    }

    public List<Product> All(long barber, long styler) {

        List<Product> f = new ArrayList<Product>();
        String select = "SELECT * FROM " + Table + " where "
                + Columns.State + " = " + Settings.State_Available.Exist
                + " ORDER BY " + Columns.Id + ";";
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                f.add(getProduct(cursor));
            } while (cursor.moveToNext());
        }
        return f;
    }

    public List<Product> All(long parent_id) {

        List<Product> f1 = new ArrayList<Product>();
        String select1 = "SELECT * FROM " + Table + " where "
                + Columns.State + " = " + Settings.State_Available.Exist +" and "
                + Columns.Parent + " = " + parent_id
                + " ORDER BY " + Columns.Id + ";";

        Cursor cursor1 = db.rawQuery(select1, null);

        if (cursor1.moveToFirst()) {
            do {
                f1.add(getProduct(cursor1));
            } while (cursor1.moveToNext());
        }
        return f1;
    }
}