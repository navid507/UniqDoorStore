package com.sai.udstore.DataBase.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Sefaresh {

    private int id;
    private int product_id;
    private int count;
    private String price = "0";
    private String name;
    private String image = "";
    private String date = "";
    private String code = "";
    private String subject = "";
    private String discount = "0";


    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
//    public Sefaresh(int id, int product_id, int count, int price, String name, String date, String code, String image, String subject) {
//        this.id = id;
//        this.product_id = product_id;
//        this.date = date;
//        this.code = code;
//        this.count = count;
//        this.price = price;
//        this.name = name;
//        this.image = image;
//        this.subject = subject;
//
//
//    }

    public static Sefaresh getSefaresh(JSONObject qu) {
        Sefaresh cp = new Sefaresh();
        try {

            if (qu.has("pid")) {
                cp.setProduct_id(qu.getInt("pid"));
            }

            if (qu.has("title")) {
                cp.setName(qu.getString("title"));
            }
            if (qu.has("num")) {
                cp.setCode(qu.getString("num"));
            }
            if (qu.has("price")) {
                cp.setPrice(qu.getString("price"));
            }
            if (qu.has("count")) {
                cp.setCount(qu.getInt("count"));
            }
            if (qu.has("unit")) {
                cp.setSubject(qu.getString("unit"));
            }


        } catch (Exception err) {

        }
        return cp;
    }

    public static JSONArray makeSefaresh(List<Sefaresh> sefaresh_show) {
        JSONArray orders = new JSONArray();
        try {
//            JSONObject orders;
            for (Sefaresh sefaresh : sefaresh_show) {
                JSONObject order = new JSONObject();
                order.put("id", sefaresh.product_id);
                order.put("count", sefaresh.count);
                orders.put(order);
            }

        } catch (Exception err) {

        }

        return orders;
    }

    public Sefaresh() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
