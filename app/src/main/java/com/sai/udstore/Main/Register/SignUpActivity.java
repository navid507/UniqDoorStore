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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sai.udstore.Main.App;
import com.sai.udstore.Prefrence.Daos.User;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.sai.UF;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {

    private UserRegisterTask mAuthTask = null;
    private Button register;
    private TextView title_firstName, title_lastName, title_address;
    private EditText firstName, lastName, address, tel;
    private EB_Preference prefrence;
    private View mProgressView;
    private User userp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        prefrence = new EB_Preference(getApplicationContext());
        userp = prefrence.User();

        mProgressView = findViewById(R.id.as_pb_sign_up);
        register = (Button) findViewById(R.id.as_btn_sign_up);
        title_firstName = (TextView) findViewById(R.id.as_tv_firstName_sign_up);
        title_lastName = (TextView) findViewById(R.id.as_tv_lastName_sign_up);
        title_address = (TextView) findViewById(R.id.as_tv_title_address_sign_up);
        firstName = (EditText) findViewById(R.id.as_tv_firstName_sign_up);
        lastName = (EditText) findViewById(R.id.as_tv_lastName_sign_up);
        address = (EditText) findViewById(R.id.as_tv_address_sign_up);
        tel = (EditText) findViewById(R.id.as_tv_tel_sign_up);

        title_firstName.setTypeface(App.appFont);
        title_lastName.setTypeface(App.appFont);
        title_address.setTypeface(App.appFont);
        firstName.setTypeface(App.appFont);
        lastName.setTypeface(App.appFont);
        address.setTypeface(App.appFont);
        tel.setTypeface(App.appFont);

        firstName.setText(userp.getFirstName());
        lastName.setText(userp.getLastName());
        address.setText(userp.getAddress());
        tel.setText(userp.getTel());

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
        firstName.setError(null);
        lastName.setError(null);
        address.setError(null);
        tel.setError(null);

        // Store values at the time of the login attempt.
        String firstName_value = firstName.getText().toString();
        String lastName_value = lastName.getText().toString();
        String address_value = address.getText().toString();
        String tel_value = tel.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(firstName_value)) {
            firstName.setError(getString(R.string.error_firstName));
            focusView = firstName;
            cancel = true;
        } else if (TextUtils.isEmpty(lastName_value)) {
            lastName.setError(getString(R.string.error_lastName));
            focusView = lastName;
            cancel = true;
        } else if (TextUtils.isEmpty(address_value)) {
            address.setError(getString(R.string.error_address));
            focusView = address;
            cancel = true;
        } else if (TextUtils.isEmpty(tel_value)) {
            tel.setError(getString(R.string.error_tel));
            focusView = tel;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            String uniq = android.provider.Settings.Secure.getString(this.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);

            if (UF.Check_internet(getApplicationContext())) {
                showProgress(true);

                userp.setFirstName(firstName_value);
                userp.setLastName(lastName_value);
                userp.setAddress(address_value);
                userp.setTel(tel_value);

                String url = String.format(Settings.Urls.SignUp, firstName_value, lastName_value, address_value, tel_value,
                        userp.getUsername(), userp.getDeviceID(), userp.getUsername(), userp.getPassword());

                mAuthTask = new UserRegisterTask(url);
                mAuthTask.execute((Void) null);
            } else {
                UF.showwifidlg(Settings.Activity.Register, this, getApplicationContext());
            }
        }
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
        private final String url;

        UserRegisterTask(String url) {
            this.url = url;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return UF.Update_SignUp(response.toString(), getApplicationContext());
            } catch (Exception err) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                Toast.makeText(SignUpActivity.this, "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                finish();
                setResult(RESULT_OK);
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
