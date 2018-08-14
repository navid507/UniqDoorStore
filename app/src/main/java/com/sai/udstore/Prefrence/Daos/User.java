package com.sai.udstore.Prefrence.Daos;

import android.content.SharedPreferences;

import com.sai.udstore.Settings;

public class User {

    private String RegState = "prg.state";
    private String Userphone = "user.phone";
    private String UserCode = "user.code";
    private String DeviceID = "device.id";
    private String UserID = "user.id";
    private String UserName = "user.name";
    private String Password = "user.pass";
    private String UserImage = "user.image";
    private String message_id_read = "message.read";
    private String FirstName = "sign.firstname";
    private String LastName = "sign.lastname";
    private String Address = "sign.address";
    private String Tel = "sign.tel";
    private SharedPreferences shp;
    private SharedPreferences.Editor shpe;

    public int getRegState() {
        return shp.getInt(RegState, Settings.Register_State.Nothing);
    }

    public void setRegState(int regState) {
        shpe.putInt(RegState, regState);
        shpe.commit();
    }

    public String getUserImage() {
        return shp.getString(UserImage, "");
    }

    public void setUserImage(String id) {
        shpe.putString(UserImage, id);
        shpe.commit();
    }

    public String getUserCode() {
        return shp.getString(UserCode, "");
    }

    public void setUserCode(String id) {
        shpe.putString(UserCode, id);
        shpe.commit();
    }

    public String getUsername() {
        return shp.getString(UserName, "");
    }

    public void setUsername(String id) {
        shpe.putString(UserName, id);
        shpe.commit();
    }

    public String getPassword() {
        return shp.getString(Password, "");
    }

    public void setPassword(String id) {
        shpe.putString(Password, id);
        shpe.commit();
    }

    public String getUserphone() {
        return shp.getString(Userphone, "");
    }

    public void setUserphone(String id) {
        shpe.putString(Userphone, id);
        shpe.commit();
    }

    public User(SharedPreferences shp) {
        this.shp = shp;
        this.shpe = shp.edit();
    }

    public long getDeviceID() {
        return shp.getLong(DeviceID, -1);
    }

    public void setDeviceID(long id) {
        shpe.putLong(DeviceID, id);
        shpe.commit();
    }

    public long getUserID() {
        return shp.getLong(UserID, -1);
    }

    public void setUserID(long id) {
        shpe.putLong(UserID, id);
        shpe.commit();
    }

    public String getFirstName() {
        return shp.getString(FirstName, "");
    }

    public void setFirstName(String id) {
        shpe.putString(FirstName, id);
        shpe.commit();
    }

    public String getLastName() {
        return shp.getString(LastName, "");
    }

    public void setLastName(String id) {
        shpe.putString(LastName, id);
        shpe.commit();
    }

    public String getAddress() {
        return shp.getString(Address, "");
    }

    public void setAddress(String id) {
        shpe.putString(Address, id);
        shpe.commit();
    }

    public String getTel() {
        return shp.getString(Tel, "");
    }

    public void setTel(String id) {
        shpe.putString(Tel, id);
        shpe.commit();
    }

    public String getMessageRead() {
        return shp.getString(message_id_read, "");
    }

    public void setMessageRead(String id) {
        shpe.putString(message_id_read, id);
        shpe.commit();
    }
}
