package com.sai.udstore.DataBase.Models;

import org.json.JSONObject;

/**
 * Created by SAI_U2 on 10/01/2018.
 */

public class UserProfile {

    //    String personalId = "", username = "", password = ""/*, smscode = ""*/, device_id = "";
//    String u_fname = "", u_lname = "", u_company = "", u_country = "",
//            u_gender = "", ubirthdate = "", u_indate = "", ncode = "",
//            phone = "", mobile = "", cart_code = "", position = "",
//            email = "", image = "", nezam = "", address = "", work_phone = "", token ="";
    String DisplayName = "", Email = "", Mobile = "", PicContentType = "", Address = "";
    int Credit, Discount;

    public static UserProfile getUserProfile(JSONObject pi) {
        UserProfile up = new UserProfile();
        try {


            if (pi.has("DisplayName")) {
                up.setDisplayName(pi.getString("DisplayName"));
            }
            if (pi.has("Email")) {
                up.setEmail(pi.getString("Email"));
            }
            if (pi.has("Mobile")) {
                up.setMobile(pi.getString("Mobile"));
            }
            if (pi.has("Discount")) {
                up.setDiscount(pi.getInt("Discount"));
            }
            if (pi.has("credit")) {
                up.setCredit(pi.getInt("credit"));
            }
            if (pi.has("PicContentType")) {
                up.setPicContentType(pi.getString("PicContentType"));
            }
            if (pi.has("Address")) {
                up.setAddress(pi.getString("Address"));
            }

//                if (pi.has("xxx")) {
//                    up.setxxx(pi.getString("xxx"));
//                }
        } catch (Exception err) {

        }
        return up;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public int getCredit() {
        return Credit;
    }

    public void setCredit(int credit) {
        Credit = credit;
    }

    public String getPicContentType() {
        return PicContentType;
    }

    public void setPicContentType(String picContentType) {
        PicContentType = picContentType;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
