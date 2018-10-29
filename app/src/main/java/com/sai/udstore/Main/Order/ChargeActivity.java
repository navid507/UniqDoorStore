package com.sai.udstore.Main.Order;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sai.udstore.Main.App;
import com.sai.udstore.Prefrence.Daos.User;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.R;
import com.sai.udstore.Settings;

public class ChargeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);

    }

    public void gotoBank(View view) {
        EditText chargeAmount = findViewById(R.id.ac_et_charge);
        String amount = chargeAmount.getText().toString();

        EB_Preference preference = new EB_Preference(getApplicationContext());
        User user = preference.User();
        String userStr = user.getUsername();
        String url = String.format(Settings.Urls.Charge, userStr, amount);
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }
}
