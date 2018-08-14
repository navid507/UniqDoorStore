package com.sai.udstore.Main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sai.udstore.DataBase.DataBase;
import com.sai.udstore.Main.Register.RegisterActivity;
import com.sai.udstore.Prefrence.Daos.LastUpdate;
import com.sai.udstore.Prefrence.Daos.User;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.excomponents.Web;
import com.sai.udstore.sai.UF;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    private GetNewsTask mFinderNewsTask = null;
    private GetCatsTask mCatTask = null;
    private GetOffersTask mOffersTask = null;
    private GetProductsTask mProductTask = null;
    private EB_Preference prefrence;
    private LastUpdate lastUpdate;
    private ProgressBar mProgressView;
    private Typeface hamase;
    private TextView message;
    private DataBase db;
    private LoginUserTask mAuthTask = null;
    User userp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        hamase = Typeface.createFromAsset(getAssets(), "font/brb.ttf");
        prefrence = new EB_Preference(getApplicationContext());
        lastUpdate = prefrence.getLastUpdate();
        // TODO: 07/04/2018  remove this 
//        lastUpdate.reset();
        db = new DataBase(getApplicationContext());
        userp = prefrence.User();
//        userp.setRegState(Settings.Register_State.OK);
//        message = (TextView) findViewById(R.id.as_tv_message_splash);
        mProgressView = (ProgressBar) findViewById(R.id.as_pb_splash);

//        message.setTypeface(hamase);
        doLogin();
//        updateNews();

        //********************************Notification********************************
//
//        Intent alarmIntent = new Intent(SplashActivity.this, NotificationPublisher.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(SplashActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        manager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), pendingIntent);

//        Intent alarmIntent = new Intent(SplashActivity.this, NotificationPublisher.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(SplashActivity.this, 0, alarmIntent, 0);
//        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, Settings.HOUR);
//        calendar.set(Calendar.MINUTE, Settings.MINUTE);
//        calendar.set(Calendar.SECOND, Settings.Second);
//        //manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    boolean isNews = false, isCats = true, isOffers = true, isTeam = true, isLogin = false, isCerts = true, isProduxts;

    private void doLogin() {
        if (mAuthTask == null) {
            int regstate = userp.getRegState();
            String uniq = android.provider.Settings.Secure.getString(this.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
            if (regstate == Settings.Register_State.OK) {
                mAuthTask = new LoginUserTask(userp.getUsername(), userp.getPassword(), uniq, Settings.Urls.Login);
                mAuthTask.execute((Void) null);
            } else {
                isLogin = true;
                showsliderontime();
                updateNews();
            }
        }
    }

    private void updateNews() {


        showProgress(true);
        if (UF.Check_internet(getApplicationContext())) {

            if (mFinderNewsTask == null) {
                mFinderNewsTask = new GetNewsTask();
                mFinderNewsTask.execute((Void) null);

            }
            if (mCatTask == null) {
                mCatTask = new GetCatsTask();
                mCatTask.execute((Void) null);
            }
//            if (mOffersTask == null) {
//                mOffersTask = new GetOffersTask();
//                mOffersTask.execute((Void) null);
//            }

            if (mProductTask == null) {
                mProductTask = new GetProductsTask();
                mProductTask.execute((Void) null);
            }

        } else {
            isNews = true;
            isCats = true;
            isProduxts = true;
            isOffers = true;
            isTeam = true;
            isCerts = true;
            isLogin = true;
            showsliderontime();
        }
    }

    public void showsliderontime() {
        Log.d("show Main", "isCats: " + isCats + " isLogin: " + isLogin + " isCerts: " + isCerts + " isNews: " + isNews + " isProduct: " + isProduxts + " isTeam: " + isTeam + " isOffers: " + isOffers);
        if (isCats && isLogin && isNews && isProduxts && isOffers) {
            int delay = 0;
            Timer tm = new Timer();
            tm.schedule(new TimerTask() {

                @Override
                public void run() {
                    SplashActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            showProgress(false);
                            startApp();
                        }
                    });
                }
            }, delay);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Settings.Activity.Register) {
            if (resultCode == RESULT_OK) {
                doLogin();

            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    public void startApp() {
        // TODO: 15/07/2018  oncomment followings
        if (App.userProfile != null) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            startActivityForResult(new Intent(SplashActivity.this, RegisterActivity.class), Settings.Activity.Register);
        }
    }

//    public class FindNewsTask extends AsyncTask<Void, Void, Boolean> {
//
//        FindNewsTask() {
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            try {
//                String response = Web.send(Settings.Urls.News, "");
//                return UF.UpdateNews(response);
//            } catch (Exception err) {
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mFinderNewsTask = null;
//            if (success) {
//                isNews = true;
//
//            }
//            showsliderontime();
//
//        }
//
//        @Override
//        protected void onCancelled() {
//            mFinderNewsTask = null;
//            showProgress(false);
//        }
//    }

    public class GetCatsTask extends AsyncTask<Void, Void, Boolean> {

        GetCatsTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
//                String response = Web.send(Settings.Urls.Category, "");
                return UF.CreateCategory(getApplicationContext());
            } catch (Exception err) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mCatTask = null;
            if (success) {
                isCats = true;
            }
            showsliderontime();
        }

        @Override
        protected void onCancelled() {
            mCatTask = null;
            showProgress(false);
        }
    }

    public class GetOffersTask extends AsyncTask<Void, Void, Boolean> {

        GetOffersTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                String uniq = android.provider.Settings.Secure.getString(getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);

                String url = String.format(Settings.Urls.Offers);
                JSONObject js = new JSONObject();
//                js.put("token", App.userProfile.getToken());
                js.put("uniq", uniq);

                String response = Web.send(url, js.toString());

                return UF.Update_Offers(response, getApplicationContext());
            } catch (Exception err) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mOffersTask = null;
            if (success) {
                isOffers = true;
            }
            showsliderontime();

        }

        @Override
        protected void onCancelled() {
            mOffersTask = null;
            showProgress(false);
        }
    }


    public class LoginUserTask extends AsyncTask<Void, Void, Boolean> {
        private final String m_Phone, m_Code, m_Uniq, url;

        LoginUserTask(String phone, String code, String unig, String url) {
            m_Phone = phone;
            m_Code = code;
            m_Uniq = unig;
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                JSONObject js = new JSONObject();
                js.put(Settings.Jsons.Login.username, m_Phone);
                js.put(Settings.Jsons.Login.password, m_Code);
                js.put(Settings.Jsons.Login.uniq, m_Uniq);

                String response = Web.send(url, js.toString());
                return UF.Update_Login(response);
            } catch (Exception err) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (!success) {

            }
            mAuthTask = null;
            isLogin = true;
            showsliderontime();
            updateNews();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    public class GetProductsTask extends AsyncTask<Void, Void, Boolean> {

        GetProductsTask() {
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {

                String response = Web.send(Settings.Urls.GetAllProducts, "");
                App.products = UF.Update_Products(response);

            } catch (Exception err) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mProductTask = null;
            if (success) {
                isProduxts = true;
            }
            showsliderontime();

            super.onPostExecute(success);
        }
    }

    public class GetNewsTask extends AsyncTask<Void, Void, Boolean> {

        GetNewsTask() {
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {

                String response = Web.send(Settings.Urls.GetNews, "");
                UF.UpdateNews(response);

            } catch (Exception err) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mFinderNewsTask = null;
            if (success) {
                isNews = true;
            }
            showsliderontime();

            super.onPostExecute(success);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (this == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
