package com.sai.udstore.DataBase.Models;

import org.json.JSONObject;

/**
 * Created by SAI_U2 on 07/05/2018.
 */

public class News {

    int id;
    String subject, content, image, date;
    int priority;
    boolean state;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * <h1>Parse News From Json</h1>
     * @param hj JsonObject which received from server
     * @return News instance
     */

    public static News getNews(JSONObject hj) {
        News up = new News();
        try {
            if (hj.has("id")) {
                up.id = hj.getInt("id");
            }
            if (hj.has("name")) {
                up.subject = hj.getString("name");
            }
            if (hj.has("text")) {
                up.content = hj.getString("text");
            }
            if (hj.has("PicAddress")) {
                up.image = hj.getString("PicAddress");
            }
            if (hj.has("date")) {
                up.date = hj.getString("date");
            }
        } catch (
                Exception err)

        {

        }
        return up;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
