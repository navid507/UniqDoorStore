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
public class Download {
    private String FileUrl, remark, FileType;
    int id;

    public static Download getDownload(JSONObject qu) {
        Download cp = new Download();
        try {
            if (qu.has("id")) {
                cp.setId(qu.getInt("id"));
            }
            if (qu.has("extention")) {
                cp.setFileType(qu.getString("extention"));
            }
            if (qu.has("FileUrl")) {
                cp.setFileUrl(qu.getString("FileUrl"));
            }
            if (qu.has("remark")) {
                cp.setRemark(qu.getString("remark"));
            }
        } catch (Exception err) {

        }
        return cp;
    }

    public String getFileType() {
        return FileType;
    }

    public void setFileType(String fileType) {
        FileType = fileType;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
