package com.sai.udstore.Main.Order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.Sefaresh_fact;
import com.sai.udstore.Main.App;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.excomponents.Web;

import org.json.JSONObject;

public class InvoiceDetailsActivity extends AppCompatActivity {

    Fragment fragment = null;
    public static Sefaresh_fact sefareshFact;
    int facotID;
    ProgressBar pb;
    LinearLayout llpb;
    TextView pbErr;
    Button pbRetry;
    BottomNavigationView navigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_invoice_state:
                    displayView(Settings.Fragments.Invoice_State);
                    return true;
                case R.id.navigation_invoice_goods:
                    displayView(Settings.Fragments.Invoice_Goods);
                    return true;
                case R.id.navigation_invoice_details:
                    displayView(Settings.Fragments.Invoice_Details);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_details);
        facotID = getIntent().getIntExtra("orderID", -1);
        if (facotID == -1) {
            finish();
            return;
        }
        pb = findViewById(R.id.ao_pb_main);
        llpb = findViewById(R.id.ao_ll_pb);
        pbErr = findViewById(R.id.ao_tv_err);
        pbRetry = findViewById(R.id.ao_bt_retry);
        navigationView = findViewById(R.id.aid_avigation);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.aid_avigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getFactroDetails();
    }

    private void getFactroDetails() {
        pb.setVisibility(View.VISIBLE);
        llpb.setVisibility(View.VISIBLE);
        pbErr.setVisibility(View.GONE);
        pbRetry.setVisibility(View.GONE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject order = new JSONObject();
//                    order.put("token", App.userProfile.getToken());

                    order.put("factor", facotID);
                    String result = Web.send(Settings.Urls.get_orders_details, order.toString());

                    JSONObject json = new JSONObject(result);
                    int err = json.getInt("error");
                    if (err == 0) {

                        sefareshFact = Sefaresh_fact.parseFactor(json);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pb.setVisibility(View.GONE);
                                navigationView.setVisibility(View.VISIBLE);
                                displayView(Settings.Fragments.Invoice_State);
                            }

                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pb.setVisibility(View.GONE);
                                pbErr.setVisibility(View.VISIBLE);
                                pbErr.setText("عدم وجود سفارش");
                                pbRetry.setVisibility(View.VISIBLE);

                            }
                        });
                    }
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pbErr.setVisibility(View.VISIBLE);
                            pb.setVisibility(View.GONE);
                            pbRetry.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }
        }).start();
    }

    public void doRefresh(View v) {
        getFactroDetails();
    }

    public void displayView(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (position) {
            case Settings.Fragments.Invoice_State:
                fragment = InvoiceStateFragment.newInstance("", "");
                break;
            case Settings.Fragments.Invoice_Details:
                fragment = InvoiceDetailsFragment.newInstance("", "");
                break;
            case Settings.Fragments.Invoice_Goods:
                fragment = InvoiceGoodsFragment.newInstance("", "");
                break;
            default:
                break;
        }

        try {
            if (fragment != null) {
//                lastview = position;
//                curFragment = position;
                ft.replace(R.id.ap_frame, fragment, String.valueOf(position));
//                ft.addToBackStack(null);
                ft.commit();
            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        } catch (Exception ex) {
        }
    }
}
