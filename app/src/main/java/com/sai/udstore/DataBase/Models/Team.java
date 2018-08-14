package com.sai.udstore.DataBase.Models;

public class Team {

    private long id;
    private String name;
    private String desc;
    private String image;
    private int priority;
    private boolean State;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg_Address() {
        return image;
    }

    public void setImg_Address(String image) {
        this.image = image;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean state) {
        State = state;
    }
}
