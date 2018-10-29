package com.sai.udstore.Main;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.sai.udstore.DataBase.Models.Category;
import com.sai.udstore.DataBase.Models.News;
import com.sai.udstore.DataBase.Models.Product;
import com.sai.udstore.DataBase.Models.UserProfile;
import com.sai.udstore.Settings;

import java.util.ArrayList;

/**
 * Created by SAI_U2 on 27/03/2018.
 */
public class App extends Application {
    public static Context context;
    public static String appLang = "fa";
    public static int appLangID = Settings.Language.Persian;
    public static ArrayList<News> news = new ArrayList<>();
    public static ArrayList<Product> offers = new ArrayList<>();
    public static ArrayList<Category> cats = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();

    public static Typeface appFont;
    public static UserProfile userProfile;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        appFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/vazir.ttf");
        super.onCreate();
    }
}
