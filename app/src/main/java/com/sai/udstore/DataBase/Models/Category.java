package com.sai.udstore.DataBase.Models;

import android.content.Context;
import android.util.Log;

import com.sai.udstore.R;
import com.sai.udstore.Settings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by navid on 8/15/2016.
 */
public class Category {
    private String name, desc;
    private String image, banner;
    private int id;
    private int parent_id, type;
    private boolean state;

    int PU_Type, PU_Gruop;

    public static Category getCategory(JSONObject qu) {
        Category cp = new Category();
        try {

            if (qu.has(Settings.Jsons.Categories.id)) {
                cp.setId(qu.getInt(Settings.Jsons.Categories.id));
            }

            if (qu.has(Settings.Jsons.Categories.name)) {
                cp.setName(qu.getString(Settings.Jsons.Categories.name));
            }

            if (qu.has(Settings.Jsons.Categories.image)) {
                cp.setImage(qu.getString(Settings.Jsons.Categories.image));
            }

            if (qu.has(Settings.Jsons.Categories.pid)) {
                cp.setParent_id(qu.getInt(Settings.Jsons.Categories.pid));
            }

        } catch (Exception err) {

        }
        return cp;
    }

    public int getPU_Type() {
        return PU_Type;
    }

    public void setPU_Type(int PU_Type) {
        this.PU_Type = PU_Type;
    }

    public int getPU_Gruop() {
        return PU_Gruop;
    }

    public void setPU_Gruop(int PU_Gruop) {
        this.PU_Gruop = PU_Gruop;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public static ArrayList<Category> createGroup(Context context) {
        ArrayList<Category> cats = new ArrayList<>();
        String catsStr = context.getString(R.string.json_cat);
        try {
            JSONArray gs = new JSONArray(context.getString(R.string.json_cat));
            for (int i = 0; i < gs.length(); i++) {
                Category cp = new Category();
                JSONObject qu = gs.getJSONObject(i);
                if (qu.has(Settings.Jsons.Categories.id)) {
                    cp.setPU_Type(qu.getInt(Settings.Jsons.Categories.id));
                }

                if (qu.has("name")) {
                    cp.setName(qu.getString("name"));
                }
                cp.setPU_Gruop(2);
                cp.setType(0);
                cp.setId(10 + i);
                cp.setParent_id(1);
                cats.add(cp);

            }
        } catch (Exception err) {
            Log.d("in Create Groups: ", err.getMessage());

        }

        return cats;
    }
}
