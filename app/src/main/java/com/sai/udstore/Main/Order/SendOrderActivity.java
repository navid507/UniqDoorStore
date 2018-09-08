package com.sai.udstore.Main.Order;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.sai.udstore.DataBase.Daos.Sefaresh_Dao;
import com.sai.udstore.DataBase.DataBase;
import com.sai.udstore.DataBase.Models.Sefaresh;
import com.sai.udstore.Main.App;
import com.sai.udstore.Prefrence.Daos.User;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.excomponents.Web;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class SendOrderActivity extends AppCompatActivity {

    //    CheckBox emerCB;
    EditText addrET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);

        addrET = findViewById(R.id.aso_et_addr);
        addrET.setText(App.userProfile.getAddress());

    }

    public void sendByCredit(View v) {
        sendOrder(1);
    }

    public void sendByBank(View v) {
        sendOrder(0);
    }

    private void sendOrder(final int credit) {

        String comment = "", type = "";
        comment = addrET.getText().toString();
//        if (comment.length() < 1)
//
//        {
//            addrET.setError("لطفا آدرس را وارد نمایید.");
//            return;
//        }

        final String fcomment = comment, ftype = type;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject order = new JSONObject();
                    JSONArray orders = Sefaresh.makeSefaresh(populateOrders());
                    order.put("productIDs", orders);
                    EB_Preference preference = new EB_Preference(getApplicationContext());
                    User userP = preference.User();
                    order.put("username", userP.getUsername());
                    order.put("password", userP.getPassword());
                    order.put("UseCredit", credit);
                    order.put("remark", "");
                    String result = Web.send(Settings.Urls.RegOrder, order.toString());

                    //{"ErrorCode":0,"Result":187,"ResultMessage":"http:\/\/unidoor.co\/foo.aspx?pre_factorno=187"}
                    //{"ErrorCode":0,"Result":188,"ResultMessage":"http:\/\/unidoor.co\/foo.aspx?pre_factorno=188"}
                    //{"ErrorCode":0,"Result":189,"ResultMessage":"http:\/\/unidoor.co\/foo.aspx?pre_factorno=189"}

                    if (result != null) {
                        JSONObject resObj = new JSONObject(result);
//                        String url = resObj.getString("url");
                        int err = resObj.getInt("ErrorCode");
                        if (err == 0) {
                            String fish = resObj.getString("Result");
                            if (credit != 1) {
                                String url = resObj.getString("ResultMessage");
                                if (url.length() > 10) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(browserIntent);
                                }
                            }

                            Intent res = new Intent();
                            res.putExtra("factor", fish);
                            setResult(RESULT_OK, res);
                            finish();
                        }else
                        {
                            Intent res = new Intent();
                            String errMSG = resObj.getString("ResultMessage");
                            res.putExtra("errMSG", errMSG);
                            setResult(-15, res);
                            finish();
                        }

                    }
                } catch (Exception err) {
                }
            }
        }).start();
    }

    public List<Sefaresh> populateOrders() {
        DataBase db = new DataBase(getApplicationContext());
        db.open();
        Sefaresh_Dao sefaresh_dao = db.getSefaresh_Dao();
        List<Sefaresh> orders = sefaresh_dao.All();
        db.close();
        return orders;
    }
}
