package com.sai.udstore.sai;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai.udstore.DataBase.Daos.Certificate_Dao;
import com.sai.udstore.DataBase.Daos.Team_Dao;
import com.sai.udstore.DataBase.DataBase;
import com.sai.udstore.DataBase.Models.Category;
import com.sai.udstore.DataBase.Models.Certificate;
import com.sai.udstore.DataBase.Models.Download;
import com.sai.udstore.DataBase.Models.News;
import com.sai.udstore.DataBase.Models.Product;
import com.sai.udstore.DataBase.Models.Team;
import com.sai.udstore.DataBase.Models.UserProfile;
import com.sai.udstore.Main.App;
import com.sai.udstore.Prefrence.Daos.LastUpdate;
import com.sai.udstore.Prefrence.Daos.User;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.Settings;
import com.sai.udstore.R;
import com.squareup.picasso.Callback;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class UF {


    public static void doCall(String tell, Context c) {
        String uri = "tel:" + tell;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        c.startActivity(intent);
    }

    public static void callApps(String urlPath, String packagename, Context mContext) {
        Uri uri = Uri.parse(urlPath);
        Intent pa_intent = new Intent(Intent.ACTION_VIEW, uri);
        pa_intent.setPackage(packagename);

        if (isIntentAvailable(mContext, pa_intent)) {
            mContext.startActivity(pa_intent);
        } else {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPath)));
        }
    }

    private static boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static Bitmap calwh(Bitmap bt, int w, int h, double reqWidth, double reqHeight) {
        if (reqHeight != -1 && reqWidth != -1) {
            double ws = reqWidth / w;
            double hs = reqHeight / h;
            if (ws < hs) {
                reqHeight = ws * h;
                reqWidth = ws * w;
            } else {
                reqHeight = hs * h;
                reqWidth = hs * w;
            }
        } else {
            if (reqHeight == -1) {
                double ws = reqWidth / w;
                reqHeight = ws * h;
            }
            if (reqWidth == -1) {
                double hs = reqHeight / h;
                reqWidth = hs * w;
            }
        }
        return Bitmap.createScaledBitmap(bt, (int) reqWidth, (int) reqHeight, true);
    }

    public static String MAC(Context c) {
        WifiManager manager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }


    public static void LOG(String title, String msg) {
        if (msg != null)
            Log.i(title, msg);
        else
            Log.i(title, "no msg!?");
    }

    public static String[] split(String original, String separator) {
        Vector<String> nodes = new Vector<String>();
        // Parse nodes into vector
        int index = original.indexOf(separator);
        while (index >= 0) {
            nodes.addElement(original.substring(0, index));
            original = original.substring(index + separator.length());
            index = original.indexOf(separator);
        }
        // Get the last node
        nodes.addElement(original);

        // Create split string array
        String[] result = new String[nodes.size()];
        if (nodes.size() > 0) {
            for (int loop = 0; loop < nodes.size(); loop++) {
                result[loop] = nodes.elementAt(loop);
            }
        }
        return result;
    }

    //

    public static boolean Check_internet(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
            return true;
        return false;
    }

    public static String copy(Context act, String inputPath, String inputFile, String outputPath, String outputFile)
            throws IOException {

        InputStream in = null;
        // OutputStream out = null;

        File dir = new File(inputPath);
        Boolean b = true;
        if (!dir.exists()) {
            b = dir.mkdirs();
        }

        String oldPath = inputPath + inputFile;
        String newPath = outputPath + outputFile;

        File tempFile = new File(newPath);
        if (tempFile.exists()) {
            return newPath;

        }
        in = new FileInputStream(oldPath);
        // out = new FileOutputStream(newPath);
        // out.
        FileOutputStream out = act.openFileOutput(newPath, Context.MODE_WORLD_READABLE);
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        in.close();
        in = null;

        // write the output file (You have now copied the file)
        out.flush();
        out.close();
        out = null;

        return newPath;
    }

    public static void sendmyself(Context act) {
        PackageManager pm = act.getPackageManager();
        ApplicationInfo appInfo;
        try {
            String PACKAGE_NAME = act.getPackageName();
            appInfo = pm.getApplicationInfo(PACKAGE_NAME, PackageManager.GET_META_DATA);
            Intent sendBt = new Intent(Intent.ACTION_SEND);
            sendBt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // NOT THIS!
            // sendBt.setType("application/vnd.android.package-archive");
            sendBt.setType("application/zip");
            sendBt.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + appInfo.publicSourceDir));
            // sendBt.setClassName("com.android.bluetooth",
            // "com.android.bluetooth.opp.BluetoothOppLauncherActivity");
            act.startActivity(sendBt);
        } catch (NameNotFoundException e1) {
            e1.printStackTrace();
        }
    }


    public static String getJsonString(String str) {
        String ret = str;
        try {
            ret = str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1);
        } catch (Exception e) {
        }
        return ret;
    }

    public static void showwifidlg(final int whocalled, final Activity act, Context context) {

        if (Settings.if_wifi_sw_checked) {
            return;
        }

        Settings.if_wifi_sw_checked = true;
        Builder builder = new Builder(act);
        String mainmsg = context.getString(R.string.wifi_dlg_text);
        String OKstr = context.getString(R.string.OK);
        String Cancelstr = context.getString(R.string.Cancel);

        builder.setMessage(mainmsg).setPositiveButton(OKstr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                act.startActivityForResult(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS), whocalled);
                Settings.if_wifi_sw_checked = false;
            }
        }).setNegativeButton(Cancelstr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Settings.if_wifi_sw_checked = false;
            }
        });
        // Create the AlertDialog object and return it
        builder.create().show();

    }

    public static Builder getOKdlg(Activity act, String title, String btn) {
        Builder dialog = new Builder(act);

        dialog.setTitle(title);
        dialog.setPositiveButton(btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });

        return dialog;
    }

    public static Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
//        ContextWrapper cw = new ContextWrapper(context);
        // final File directory = new File(imageDir);//cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(imageDir + "/" + imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (fos != null)
                                    fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
    }

    public static void loadandsave_Touch(final Context context, final String dir, final String imgaddr1, final TouchImageView img, final String pathUrl1, final int width, final int height, final int startid, final int errid) {
        final String imgaddr = width + "_" + height + imgaddr1;
        ContextWrapper cw = new ContextWrapper(context);
        File direname = cw.getDir(dir, Context.MODE_PRIVATE);
        File filename = new File(direname, imgaddr);

        // first read from file
        Picasso.with(context)
                .load(filename)
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        // next load from internet
                        // Try again online if cache failed
                        Picasso.with(context)
                                .load(pathUrl1)
                                .error(errid)
                                .into(img, new Callback() {
                                    @Override
                                    public void onSuccess() {
//                                        String path = Environment.getExternalStorageDirectory()
//                                                .toString() + dir;
                                        Log.i("image downloaded!", "going to saved");
                                        ContextWrapper cw = new ContextWrapper(context);
                                        File dirname = cw.getDir(dir, Context.MODE_PRIVATE);
//
                                        if (!dirname.exists()) {
                                            dirname.mkdirs();
                                        }

                                        String path = dirname.getPath();
                                        Target tr = UF.picassoImageTarget(context, dirname.getPath(), imgaddr);
                                        Picasso.with(context)
                                                .load(pathUrl1)
                                                .into(tr);
                                    }

                                    @Override
                                    public void onError() {
                                        // Try again online if cache failed

                                    }
                                });
                    }
                });
    }

    public static void loadandsave(final Context context, final String dir, final String imgaddr1, final ImageView img, final String pathUrl1, final int width, final int height, final int placeHolder, final int errid) {
        if (pathUrl1.length() < 4) {
            return;
        }
        Picasso.with(context)
                .load(pathUrl1)
                .placeholder(placeHolder)
                .into(img);
    }

    //        public static void loadandsave(final Context context, final String dir, final String imgaddr1, final ImageView img, final String pathUrl1, final int width, final int height, final int placeHolder, final int errid) {
//        final String imgaddr = width + "_" + height + imgaddr1;
//        ContextWrapper cw = new ContextWrapper(context);
//        File direname = cw.getDir(dir, Context.MODE_PRIVATE);
//        File filename = new File(direname, imgaddr);
//
//        // first read from file
//        Picasso.with(context)
//                .load(filename)
//                .placeholder(placeHolder)
//                .into(img, new Callback() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onError() {
//                        // next load from internet
//                        // Try again online if cache failed
//                        Picasso.with(context)
//                                .load(pathUrl1)
//                                .error(errid)
//                                .into(img, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
////                                        String path = Environment.getExternalStorageDirectory()
////                                                .toString() + dir;
//                                        Log.i("image downloaded!", "going to saved");
//                                        ContextWrapper cw = new ContextWrapper(context);
//                                        File dirname = cw.getDir(dir, Context.MODE_PRIVATE);
////
//                                        if (!dirname.exists()) {
//                                            dirname.mkdirs();
//                                        }
//
//                                        String path = dirname.getPath();
//                                        Target tr = UF.picassoImageTarget(context, dirname.getPath(), imgaddr);
////                                        Picasso.with(context)
////                                                .load(pathUrl1)
////                                                .into(tr);
//                                    }
//
//                                    @Override
//                                    public void onError() {
//                                        // Try again online if cache failed
//
//                                    }
//                                });
//                    }
//                });
//    }
    public static boolean UpdateNews(String rret) {
        boolean retVal = false;
        long status = 0;
        try {
            JSONObject json = new JSONObject(rret);
            status = json.getLong(Settings.Jsons.error);
            if (status == 0) {
                retVal = true;
                if (json.has("Result")) {
                    JSONArray arr = json.getJSONArray("Result");
                    for (int i = 0; i < arr.length(); i++) {
                        App.news.add(News.getNews(arr.getJSONObject(i)));
                    }
                }
            }
        } catch (Exception e) {
            UF.LOG("uf_updateNews_catch", e.getMessage());
            return retVal;
        } finally {
        }
        return true;
    }

    public static void lineOverText(TextView price) {
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static ArrayList<Product> UpdateFavoriteList(String rret) {
        long status = 0;
        ArrayList<Product> aps = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(rret);
            status = json.getLong(Settings.Jsons.error);
            if (status == 0) {
                if (json.has(Settings.Jsons.Products_of_Cat.favorite)) {
                    JSONArray arr = json.getJSONArray(Settings.Jsons.Products_of_Cat.favorite);
                    for (int i = 0; i < arr.length(); i++) {
                        aps.add(Product.getProduct(arr.getJSONObject(i)));
                    }
                }
            }
        } catch (Exception e) {
            UF.LOG("uf_updateFavList_catch", e.getMessage());
        } finally {
        }
        return aps;
    }

    public static boolean UpdateFavorite(String rret) {
        boolean retVal = false;
        long status = 0;
        try {
            JSONObject json = new JSONObject(rret);
            status = json.getLong(Settings.Jsons.error);
            if (status == 0) {
                retVal = true;
            }
        } catch (Exception e) {
            UF.LOG("uf_updateFav_catch", e.getMessage());
            return retVal;
        } finally {
        }
        return retVal;
    }

    public static boolean CreateCategory(Context context) {
        boolean retVal = false;
        long status = 0;
        try {
            Category s_cat = new Category();
            s_cat.setName("سیستم");
            s_cat.setPU_Type(-1);
            s_cat.setPU_Gruop(2);
            s_cat.setId(1);
            s_cat.setParent_id(-1);
            s_cat.setType(1);
            App.cats.add(s_cat);

            Category cat = new Category();
            cat.setName("لوازم یدکی");
            cat.setPU_Type(6);
            cat.setPU_Gruop(3);
            cat.setId(2);
            cat.setParent_id(-1);
            cat.setType(0);
            App.cats.add(cat);

            App.cats.addAll(Category.createGroup(context));

        } catch (Exception e) {
            UF.LOG("uf_updateCat_catch", e.getMessage());
            return retVal;
        } finally {
        }
        return true;
    }

    public static boolean UpdateCategory(String rret) {
        boolean retVal = false;
        long status = 0;
        try {
            JSONObject json = new JSONObject(rret);
            status = json.getLong(Settings.Jsons.error);
            if (status == 0) {
                retVal = true;
                if (json.has(Settings.Jsons.Categories.array)) {
                    JSONArray arr = json.getJSONArray(Settings.Jsons.Categories.array);
                    for (int i = 0; i < arr.length(); i++) {
                        App.cats.add(Category.getCategory(arr.getJSONObject(i)));
                    }
                }
            }
        } catch (Exception e) {
            UF.LOG("uf_updateCat_catch", e.getMessage());
            return retVal;
        } finally {
        }
        return true;
    }

    public static ArrayList<Product> Update_ProductsTemp(String rret) {
        ArrayList<Product> aps = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(rret);
            for (int i = 0; i < json.length(); i++) {
                aps.add(Product.getProduct(json.getJSONObject(i)));
            }
        } catch (Exception e) {
            UF.LOG("uf_updateProduct_catch", e.getMessage());
        } finally {
        }
        return aps;
    }

    public static ArrayList<News> Update_Slider(String rret) {
        long status = 0;
        ArrayList<News> aps = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(rret);
            status = json.getLong(Settings.Jsons.error);
            if (status == 0) {
                if (json.has("Result")) {
                    JSONArray arr = json.getJSONArray("Result");
                    for (int i = 0; i < arr.length(); i++) {
                        aps.add(News.getNews(arr.getJSONObject(i)));
                    }
                }
            }
        } catch (Exception e) {
            UF.LOG("uf_updateProduct_catch", e.getMessage());
        } finally {
        }
        return aps;
    }

    public static ArrayList<Download> Update_Downloads(String rret) {
        long status = 0;
        ArrayList<Download> aps = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(rret);
            status = json.getLong(Settings.Jsons.error);
            if (status == 0) {
                if (json.has("Result")) {
                    JSONArray arr = json.getJSONArray("Result");
                    for (int i = 0; i < arr.length(); i++) {
                        aps.add(Download.getDownload(arr.getJSONObject(i)));
                    }
                }
            }
        } catch (Exception e) {
            UF.LOG("uf_updateProduct_catch", e.getMessage());
        } finally {
        }
        return aps;
    }


    public static String getVersionInfo(Context c) {
        String strVersion = "Version:";

        PackageInfo packageInfo;
        try {
            packageInfo = c
                    .getPackageManager()
                    .getPackageInfo(
                            c.getPackageName(),
                            0
                    );
            strVersion += packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            strVersion += "Unknown";
        }

        return strVersion;
    }

    public static void setAllFonts(Typeface tf, ViewGroup vg) {

        try {
            for (int i = 0; i < vg.getChildCount(); i++) {
                View uv = vg.getChildAt(i);
                if (uv instanceof ViewGroup) {
                    setAllFonts(tf, (ViewGroup) uv);
                } else if (uv instanceof TextView) {
                    ((TextView) uv).setTypeface(tf);
                } else if (uv instanceof Button) {
                    ((Button) uv).setTypeface(tf);
                } else if (uv instanceof EditText) {
                    ((EditText) uv).setTypeface(tf);
                } else if (uv instanceof TextInputLayout) {
                    ((TextInputLayout) uv).setTypeface(tf);
                } else if (uv instanceof CheckBox) {
                    ((CheckBox) uv).setTypeface(tf);
                }

//                for (int j = 0; j < rl.getChildCount(); j++) {
//
//                    try {
//                        TextView tv = (TextView) rl.getChildAt(j);
//                        tv.setTypeface(iranSans);
//                    } catch (Exception er) {
//
//                    }
//                }
            }
        } catch (Exception er) {

        }

    }

    public static String digitsToArabic(String str) {
        //NumberFormat nf = NumberFormat.getInstance(new Locale("fa", "IR"));
        //nf.format(i);
        //char[] arabicChars = {'۰','۱','۲','۳','۴','۵','۶','۷','۸','۹'};
        char[] arabicChars = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                builder.append(arabicChars[(int) (str.charAt(i)) - 48]);
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    public static String persianFontCheck(String value, String appLang) {
        if ("fa".equals(appLang)) {
            return digitsToArabic(value);
        } else {
            return value;
        }
    }

    public static String getPriceFormat(String price, String appLang) {

        StringBuilder builder = new StringBuilder(String.valueOf(price));
        if (price.length() > 9) {
            builder.insert(builder.length() - 9, ',');
        }
        if ((price.length() > 6)) {
            builder.insert(builder.length() - 6, ',');
        }
        if ((price.length() > 3)) {
            builder.insert(builder.length() - 3, ',');
        }


        return persianFontCheck(builder.toString(), appLang);

    }

    public static String getPriceFormat(int price, String appLang) {

        StringBuilder builder = new StringBuilder(String.valueOf(price));
        if (price > 999999999) {
            builder.insert(builder.length() - 9, ',');
        }
        if (price > 999999) {
            builder.insert(builder.length() - 6, ',');
        }
        if (price > 999) {
            builder.insert(builder.length() - 3, ',');
        }


        return persianFontCheck(builder.toString(), appLang);

    }

    public static ArrayList<Product> Update_Products(String rret) {
        long status = 0;
        ArrayList<Product> aps = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(rret);
            status = json.getLong(Settings.Jsons.error);
            if (status == 0) {
                if (json.has("Result")) {
                    JSONArray arr = json.getJSONArray("Result");
                    for (int i = 0; i < arr.length(); i++) {
                        aps.add(Product.getProduct(arr.getJSONObject(i)));
                    }
                }
            }
        } catch (Exception e) {
            UF.LOG("uf_updateProduct_catch", e.getMessage());
        } finally {
        }
        return aps;
    }

    public static boolean Update_Offers(String rret, Context c) {
        boolean retVal = false;
        long status = 0;
        try {
            JSONObject json = new JSONObject(rret);
            status = json.getLong(Settings.Jsons.error);
            if (status == 0) {
                retVal = true;
                if (json.has(Settings.Jsons.Products_of_Cat.offers)) {
                    JSONArray arr = json.getJSONArray(Settings.Jsons.Products_of_Cat.offers);
                    for (int i = 0; i < arr.length(); i++) {
                        App.offers.add(Product.getProduct(arr.getJSONObject(i)));
                    }
                }
            }
        } catch (Exception e) {
            UF.LOG("uf_updateProduct_catch", e.getMessage());
            return retVal;
        } finally {
        }
        return true;
    }

    public static int Update_Register(String rret, Context c, String phone, String pass) {
        int res = 0;
        try {
            JSONObject json = new JSONObject(rret);
            if (json.has("error")) {
                res = json.getInt("error");
                if (res == Settings.Result.Success) {


                }
            } else {
                return 1;
            }
        } catch (Exception e) {
        }
        return res;
    }


    public static boolean Update_Login(String rret) {
        boolean success = false;
        try {
            JSONObject json = new JSONObject(rret);
            if (json.has("ErrorCode")) {
                int res = json.getInt("ErrorCode");
                if (res != Settings.Result.Success) {
                    success = false;
                    App.userProfile = null;

                } else {

//                    if (json.has("UserInfo")) {

                    App.userProfile = UserProfile.getUserProfile(json);

//                        EB_Preference prefrence = new EB_Preference(c);
//                        User user;
//                        user = prefrence.User();
//                        user.setDeviceID(Long.valueOf(up.getDevice_id()));
//                        user.setUserphone(up.getMobile());
//                        user.setRegState(Settings.Register_State.OK);
                    success = true;
//                    }
                }
            } else {
                success = false;
            }
        } catch (Exception e) {
            success = false;
        }
        return success;
    }

    public static boolean Update_SignUp(String rret, Context c) {
        boolean success = false;
        try {
            JSONObject json = new JSONObject(rret);
            if (json.has(Settings.Jsons.SignUp.id)) {
                int id = json.getInt(Settings.Jsons.SignUp.id);
                if (id == Settings.Result.NoAuth) {
                    success = false;
                } else {
                    int uid = json.getInt(Settings.Jsons.SignUp.id);
                    EB_Preference prefrence = new EB_Preference(c);
                    User user;
                    user = prefrence.User();
                    user.setUserID(uid);
                    user.setRegState(Settings.Register_State.OK);
                    success = true;
                }
            } else {
                success = false;
            }
        } catch (Exception e) {
            success = false;
        }
        return success;
    }

    public static boolean Update_team(String rret, Context c) {
        long update = 0;
        boolean retVal = false;
        DataBase db = new DataBase(c);
        db.open();
        Team_Dao team_dao = db.getTeam_Dao();
        try {
            ArrayList<Team> brands = new ArrayList<Team>();
            JSONObject json = new JSONObject(rret);
            update = json.getLong(Settings.Jsons.lastTime);
            retVal = true;
            if (json.has(Settings.Jsons.Team.array)) {
                JSONArray Add = json.getJSONArray(Settings.Jsons.Team.array);
                for (int i = 0; i < Add.length(); i++) {
                    JSONObject qu = Add.getJSONObject(i);
                    Team cp = new Team();
                    if (qu.has(Settings.Jsons.Team.id)) {
                        cp.setId(qu.getLong(Settings.Jsons.Team.id));
                    }
                    if (qu.has(Settings.Jsons.Team.name)) {
                        cp.setName(qu.getString(Settings.Jsons.Team.name));
                    }
                    if (qu.has(Settings.Jsons.Team.image)) {
                        cp.setImg_Address(qu.getString(Settings.Jsons.Team.image));
                    }
                    if (qu.has(Settings.Jsons.Team.desc)) {
                        cp.setDesc(qu.getString(Settings.Jsons.Team.desc));
                    }
                    if (qu.has(Settings.Jsons.Team.prio)) {
                        cp.setPriority(qu.getInt(Settings.Jsons.Team.prio));
                    }
                    if (qu.has(Settings.Jsons.Team.state)) {
                        int a = qu.getInt(Settings.Jsons.Team.state);
                        if (a == 1) {
                            cp.setState(true);
                        } else {
                            cp.setState(false);
                        }
                    }
                    team_dao.UpdateExist(cp);
                }
            }
            EB_Preference prefrence = new EB_Preference(c);
            LastUpdate laup = prefrence.getLastUpdate();
            laup.setTeam(update);
        } catch (Exception e) {
            db.close();
            UF.LOG("uf_updateTeam_catch", e.getMessage());
            return retVal;
        } finally {
            db.close();
        }
        return true;
    }

    public static boolean Update_certificate(String rret, Context c) {
        boolean retVal = false;
        DataBase db = new DataBase(c);
        db.open();
        Certificate_Dao plda = db.getCertificate_Dao();
        String ret = UF.getJsonString(rret);
        // long time = 1;
        boolean success = false;
        try {
            JSONObject json = new JSONObject(ret);

            if (json.has(Settings.Jsons.Certificate.array)) {
                long update = json.getLong(Settings.Jsons.lastTime);
                retVal = true;
                JSONArray Add = json.getJSONArray(Settings.Jsons.Certificate.array);
                for (int i = 0; i < Add.length(); i++) {
                    JSONObject qu = Add.getJSONObject(i);
                    Certificate cp = new Certificate();
                    if (qu.has(Settings.Jsons.Certificate.id)) {
                        cp.setId(qu.getLong(Settings.Jsons.Certificate.id));
                    }
                    if (qu.has(Settings.Jsons.Certificate.desc)) {
                        cp.setCertificate(qu.getString(Settings.Jsons.Certificate.desc));
                    }
                    if (qu.has(Settings.Jsons.Certificate.prio)) {
                        cp.setPriority(qu.getInt(Settings.Jsons.Certificate.prio));
                    }
                    if (qu.has(Settings.Jsons.Certificate.stylist_id)) {
                        cp.setStyler(qu.getInt(Settings.Jsons.Certificate.stylist_id));
                    }
                    if (qu.has(Settings.Jsons.Certificate.state)) {
                        int a = qu.getInt(Settings.Jsons.Certificate.state);
                        if (a == 1) {
                            cp.setState(true);
                        } else {
                            cp.setState(false);
                        }
                    }
                    plda.UpdateExist(cp);
                }

                EB_Preference prefrence = new EB_Preference(c);
                LastUpdate laup = prefrence.getLastUpdate();
                laup = prefrence.getLastUpdate();
                laup.setCertificate(update);
            }
            success = true;
        } catch (Exception e) {
            success = retVal;
            UF.LOG("uf_updatefood_catch", e.getMessage());
        } finally {
            List<Certificate> rau = plda.All(Settings.soft_id);
            db.close();
        }
        return success;
    }

    public static boolean Update_Sefaresh(String rret, Context c, String phone) {
        boolean success = false;
        try {
            if (rret.equals("Success")) {
                success = true;
            } else {
                success = false;
            }
        } catch (Exception e) {
            success = false;
        }
        return success;
    }

//    public static String getMessagePath(String name, int w, int h) {
//        String p = Settings.Urls.image + Settings.Urls.message_image + name +
//                "&w=" + w + "&h=" + h;
//        return p;
//    }
}
