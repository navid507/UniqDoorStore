package com.sai.udstore.Main.Register;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.sai.udstore.excomponents.Web;
import com.sai.udstore.sai.UF;

import org.json.JSONObject;

public class VerifyActivity extends AppCompatActivity {

    private EditText code;
    private TextView title_code;
    private Button register, verify;
    private View mProgressView;
    private EB_Preference prefrence;
    private User userp;
    private VerifyUserTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        prefrence = new EB_Preference(getApplicationContext());
        userp = prefrence.User();

        title_code = (TextView) findViewById(R.id.av_tv_title_code_verify);
        code = (EditText) findViewById(R.id.av_tv_code_verify);
        mProgressView = findViewById(R.id.av_pb_verify);
        register = (Button) findViewById(R.id.av_btn_register);
        verify = (Button) findViewById(R.id.av_btn_verify);

        title_code.setTypeface(App.appFont);
        register.setTypeface(App.appFont);
        verify.setTypeface(App.appFont);
        code.setTypeface(App.appFont);

        code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.send || id == EditorInfo.IME_NULL) {
                doVerify();
                return true;
//                }
//                return false;
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doVerify();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userp.setRegState(Settings.Register_State.Nothing);
                setResult(RESULT_FIRST_USER);
                finish();
            }
        });
    }

    private void doVerify() {
        // Reset errors.
        code.setError(null);

        // Store values at the time of the login attempt.
        String cod = code.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(cod)) {
            code.setError(getString(R.string.entenr_verify));
            focusView = code;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            if (UF.Check_internet(getApplicationContext())) {
                String uniq = android.provider.Settings.Secure.getString(this.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
                mAuthTask = new VerifyUserTask(userp.getUserphone(), cod, uniq, Settings.Urls.Verify);
                mAuthTask.execute((Void) null);
            } else {
                UF.showwifidlg(Settings.Activity.Verify, this, getApplicationContext());
            }

//            if ((code.getText().toString().equals(userp.getUserCode()))) {
//                userp.setRegState(Settings.Register_State.Code);
//                setResult(RESULT_OK);
//                finish();
//            } else {
//                code.setError(getString(R.string.incorrect_verify));
//                focusView = code;
//                focusView.requestFocus();
//            }
        }
    }


    public class VerifyUserTask extends AsyncTask<Void, Void, Boolean> {
        private final String m_Phone, m_Code, m_Uniq, url;
        private ProgressDialog nDialog;

        VerifyUserTask(String phone, String code, String unig, String url) {
            m_Phone = phone;
            m_Code = code;
            m_Uniq = unig;
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(VerifyActivity.this);
            nDialog.setTitle("در حال ارسال اطلاعات");
            nDialog.setMessage("لطفاً منتظر بمانید ...");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                JSONObject js = new JSONObject();
                js.put(Settings.Jsons.Verify.phone, m_Phone);
                js.put(Settings.Jsons.Verify.code, m_Code);
                js.put(Settings.Jsons.Verify.uniq, m_Uniq);

                userp.setUserCode(m_Code);

                String response = Web.send(url, js.toString());
                return UF.Update_Login(response);
//                return UF.Verify_Phone(response, getApplicationContext());
            } catch (Exception err) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            nDialog.dismiss();
            if (success) {
                Toast.makeText(VerifyActivity.this, "با تشکر از ثبت نام شما", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Snackbar trans_sb;
                trans_sb = Snackbar.make(getCurrentFocus(), "کد تایید اشتباه می باشد", Snackbar.LENGTH_INDEFINITE);
                trans_sb.setActionTextColor(getResources().getColor(R.color.white));
                trans_sb.setAction("تایید", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
                trans_sb.show();
                code.setError("کد تایید اشتباه می باشد");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            nDialog.dismiss();
        }
    }
}