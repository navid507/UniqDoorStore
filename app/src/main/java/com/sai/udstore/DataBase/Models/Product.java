package com.sai.udstore.DataBase.Models;

import org.json.JSONObject;

public class Product {

    private int id, typeID, groupID;
    private String Name, Remark;
    private String Image;
    private boolean state, fav;

    private String Price;
    private String Dprice;


    public static Product getProduct(JSONObject qu) {
        Product cp = new Product();
        try {
            if (qu.has("id"))
            {
                cp.setId(qu.getInt("id"));
            }

            if (qu.has("name")) {
                cp.setName(qu.getString("name"));
            }
            if (qu.has("remark")) {
                cp.setRemark(qu.getString("remark"));
            }
            if (qu.has("price")) {
                cp.setPrice(qu.getString("price"));
            }
            if (qu.has("HasDiscount")) {
                cp.setDprice(qu.getString("HasDiscount"));
            }
            if (qu.has("ImageUrl")) {
                cp.setImage(qu.getString("ImageUrl"));
            }
            if (qu.has("TypeID")) {
                cp.setTypeID(qu.getInt("TypeID"));
            }
            if (qu.has("groupid")) {
                cp.setGroupID(qu.getInt("groupid"));
            }
        } catch (Exception err) {

        }
        return cp;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getDprice() {
        return Dprice;
    }

    public void setDprice(String dprice) {
        this.Dprice = dprice;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }


    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        this.Remark = remark;
    }
}
