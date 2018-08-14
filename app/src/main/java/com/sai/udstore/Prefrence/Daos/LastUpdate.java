package com.sai.udstore.Prefrence.Daos;

import android.content.SharedPreferences;

public class LastUpdate {
    private String News = "last.news";
    private String Message = "last.message";
    private String Category = "last.category";
    private String Product = "last.product";
    private String Models = "last.model";
    private String Certificate = "last.certificate";
    private String Team = "last.team";

    SharedPreferences shp;
    SharedPreferences.Editor shpe;

    public LastUpdate(SharedPreferences shp) {
        this.shp = shp;
        this.shpe = shp.edit();
    }

    public long getMessage() {
        return shp.getLong(Message, 1);
    }

    public void setMessage(long mm) {
        shpe.putLong(Message, mm);
        shpe.commit();
    }

    public long getCategory() {
        return shp.getLong(Category, 1);
    }

    public void setCategory(long mm) {
        shpe.putLong(Category, mm);
        shpe.commit();
    }

    public long getProduct() {
        return shp.getLong(Product, 1);
    }

    public void setProduct(long mm) {
        shpe.putLong(Product, mm);
        shpe.commit();
    }

    public long getModels() {
        return shp.getLong(Models, 1);
    }

    public void setModels(long mm) {
        shpe.putLong(Models, mm);
        shpe.commit();
    }

    public long getNews() {
        return shp.getLong(News, 1);
    }

    public void setNews(long mm) {
        shpe.putLong(News, mm);
        shpe.commit();
    }

    public long getTeam() {
        return shp.getLong(Team, 1);
    }

    public void setTeam(long mm) {
        shpe.putLong(Team, mm);
        shpe.commit();
    }

    public long getCertificate() {
        return shp.getLong(Certificate, 1);
    }

    public void setCertificate(long mm) {
        shpe.putLong(Certificate, mm);
        shpe.commit();
    }

    public void reset() {
        setProduct(1);
        setModels(1);
        setNews(1);
        setCategory(1);
        setCertificate(1);
        setTeam(1);
        setMessage(1);
    }
}
