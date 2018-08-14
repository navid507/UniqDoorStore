package com.sai.udstore.DataBase.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sefaresh_fact {
    private int PayType = 0, id;
    private String fdate = "";


    public static Sefaresh_fact parseFactor(JSONObject object) {
        Sefaresh_fact sefareshFact = new Sefaresh_fact();
        try {

            if (object.has("PayType")) {
                sefareshFact.PayType = object.getInt("PayType");
            }

            if (object.has("id")) {
                sefareshFact.id = object.getInt("id");
            }

            if (object.has("fdate")) {
                sefareshFact.fdate = object.getString("fdate");
            }
        } catch (Exception err) {

        }
        return sefareshFact;
    }

    public Sefaresh_fact() {
    }

    public int getPayType() {
        return PayType;
    }

    public void setPayType(int payType) {
        PayType = payType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFdate() {
        return fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
    }

}
