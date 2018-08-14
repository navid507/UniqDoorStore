package com.sai.udstore.DataBase.Models;

/**
 * Created by DANIAL on 27/03/2017.
 */

public class Message {

    private long id;
    private String image;
    private String subject;
    private String content;
    private boolean state;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
