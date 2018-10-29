package com.sai.udstore.Main.Register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sai.udstore.Main.App;
import com.sai.udstore.Prefrence.Daos.User;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.excomponents.Web;
import com.sai.udstore.sai.UF;

import org.json.JSONObject;

import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;
    private EditText usernameET, passwordET;
    private Button register;
    private TextView title_phoneTV, title_passTV;
    private View mProgressView, pb;
    private EB_Preference prefrence;
    private User userp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        prefrence = new EB_Preference(getApplicationContext());
        userp = prefrence.User();

        usernameET = findViewById(R.id.ar_et_username);
        passwordET = findViewById(R.id.ar_et_pass_register);

        title_phoneTV = (TextView) findViewById(R.id.ar_tv_username_register);
        title_passTV = (TextView) findViewById(R.id.ar_tv_username_register);
        register = (Button) findViewById(R.id.ar_btn_register);
        mProgressView = findViewById(R.id.ar_pb_register);
        pb = findViewById(R.id.ar_pb);

        usernameET.setTypeface(App.appFont);
        title_phoneTV.setTypeface(App.appFont);
        passwordET.setTypeface(App.appFont);
        title_passTV.setTypeface(App.appFont);
        register.setTypeface(App.appFont);

        usernameET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                // TODO: 13/05/2018  coorrect following command
                if (/*id == R.id.register ||*/ id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        usernameET.setError(null);
        passwordET.setError(null);

        // Store values at the time of the login attempt.
        String phone_value = usernameET.getText().toString();
        String pass_value = passwordET.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(phone_value)) {
            usernameET.setError(getString(R.string.error_mobile));
            focusView = usernameET;
            cancel = true;
        } else if (TextUtils.isEmpty(pass_value)) {
            passwordET.setError(getString(R.string.error_password));
            focusView = passwordET;
            cancel = true;
        } else if (isPassNotValid(pass_value)) {
            passwordET.setError(getString(R.string.error_password_valid));
            focusView = passwordET;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            String uniq = android.provider.Settings.Secure.getString(this.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);

            if (UF.Check_internet(getApplicationContext())) {
                showProgress(true);
//                String url = String.format(Settings.Urls.Register, phone_value, uniq);
                mAuthTask = new UserLoginTask(Settings.Urls.Login, phone_value, pass_value, uniq);
                mAuthTask.execute((Void) null);
            } else {
                UF.showwifidlg(Settings.Activity.Register, this, getApplicationContext());
            }
        }
    }

    private boolean isPhoneNotValid(String phone) {
        if (phone.length() != 11) {
            return true;
        } else if (!phone.startsWith("09")) {
            return true;
        }

        return false;
    }

    private boolean isPassNotValid(String pass) {
        if (pass.length() < 1) {
            return true;
        } else {
            return false;
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {
        private final String url, username, uniq, pass;

        UserLoginTask(String url, String phone, String pass, String uniq) {
            this.url = url;
            this.username = phone;
            this.pass = pass;
            this.uniq = uniq;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                JSONObject js = new JSONObject();
                js.put(Settings.Jsons.Login.username, username);
                js.put(Settings.Jsons.Login.password, pass);
                js.put(Settings.Jsons.Login.uniq, uniq);

                URL obj = new URL(url);
                String response = Web.send(url, js.toString());
                if (UF.Update_Login(response)) {
                    return Settings.Result.Success;
                } else return Settings.Result.NoAuth;
            } catch (Exception err) {
                return 1;
            }
        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            showProgress(false);
            if (success == Settings.Result.Success) {
                EB_Preference prefrence = new EB_Preference(getApplicationContext());
                User user;
                user = prefrence.User();
                user.setUsername(username);
                user.setPassword(pass);
                user.setRegState(Settings.Register_State.OK);
                setResult(RESULT_OK);
                finish();
            } else if (success == Settings.Result.NoAuth) {
                Snackbar trans_sb;
                trans_sb = Snackbar.make(getCurrentFocus(), getResources().getString(R.string.reg_error_no_crm), Snackbar.LENGTH_INDEFINITE);
                trans_sb.setActionTextColor(getResources().getColor(R.color.lightwhite));
                trans_sb.setAction(getResources().getString(R.string.send_agian), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptRegister();
                    }
                });
                trans_sb.show();
            } else {

                Snackbar trans_sb;
                trans_sb = Snackbar.make(getCurrentFocus(), getResources().getString(R.string.server_connection_problem), Snackbar.LENGTH_INDEFINITE);
                trans_sb.setActionTextColor(getResources().getColor(R.color.lightwhite));
                trans_sb.setAction(getResources().getString(R.string.send_agian), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptRegister();
                    }
                });
                trans_sb.show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            pb.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            pb.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
