package com.sai.udstore.Main.Register;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sai.udstore.Main.App;
import com.sai.udstore.Prefrence.Daos.User;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.R;
import com.sai.udstore.Settings;

public class RegisterAlertActivity extends AppCompatActivity {

    private Button ok, cancel;
    private TextView message;
    private EB_Preference prefrence;
    private User userp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_alert);

        prefrence = new EB_Preference(getApplicationContext());
        userp = prefrence.User();

        ok = (Button) findViewById(R.id.ar_btn_yes_register);
        cancel = (Button) findViewById(R.id.ar_btn_no_register);
        message = (TextView) findViewById(R.id.ar_tv_message_register);

        ok.setTypeface(App.appFont);
        cancel.setTypeface(App.appFont);
        message.setTypeface(App.appFont);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userp.setRegState(Settings.Register_State.Nothing);
                setResult(RESULT_OK);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}