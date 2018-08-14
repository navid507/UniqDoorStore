package com.sai.udstore.Main.Order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sai.udstore.DataBase.Daos.Sefaresh_Dao;
import com.sai.udstore.DataBase.DataBase;
import com.sai.udstore.DataBase.Models.Sefaresh;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.Products.ProductActivity;
import com.sai.udstore.R;
import com.sai.udstore.sai.UF;

public class InvoiceEditActivity extends AppCompatActivity {

    EditText numET;
    Button okBT, deleteBT;
    int oid;
    Sefaresh sefaresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        LinearLayout mmll = findViewById(R.id.mmll);
        UF.setAllFonts(App.appFont, mmll);
        oid = getIntent().getIntExtra("oid", -1);
        findOrder();
        if (oid == -1) {
            finish();
            return;
        }

        TextView nameTV = findViewById(R.id.apa_tv_prd_name);
        numET = findViewById(R.id.apa_et_number);
        okBT = findViewById(R.id.apa_btn_register);
        deleteBT = findViewById(R.id.apa_btn_delete);

        okBT.setText("اعمال تغییرات");
        deleteBT.setVisibility(View.VISIBLE);
        nameTV.setText(sefaresh.getName());
        numET.setText(String.valueOf(sefaresh.getCount()));


        TextView priceTV = findViewById(R.id.apa_tv_prd_price);
        TextView offTV = findViewById(R.id.apa_tv_prd_off);
        numET = findViewById(R.id.apa_et_number);

        nameTV.setTypeface(App.appFont);


        nameTV.setText(sefaresh.getName());
        priceTV.setText(UF.getPriceFormat(sefaresh.getPrice(), "fa") + "ریال");
        if (sefaresh.getDiscount().equals("0")) {
            offTV.setText(UF.getPriceFormat(App.userProfile.getDiscount(), "fa") + "%");
        } else {
            offTV.setText("ندارد");

        }
        updateFinalPrice();

    }

    private void findOrder() {
        DataBase db = new DataBase(getApplicationContext());
        db.open();
        Sefaresh_Dao sefaresh_dao = db.getSefaresh_Dao();
        sefaresh = sefaresh_dao.GetOrder(oid);
        db.close();
    }

    public void deleteOrder(View v) {
        DataBase db = new DataBase(getApplicationContext());
        db.open();
        Sefaresh_Dao sefaresh_dao = db.getSefaresh_Dao();
        sefaresh_dao.DeleteOrder(oid);
        db.close();
        setResult(RESULT_OK);
        finish();
    }

    public void addToOrder(View v) {
        String num_str = numET.getText().toString();
        if (num_str.length() < 1) {
            numET.setError("تعداد وارد نشده است");
        } else {
            try {
                int num = Integer.parseInt(num_str);
                DataBase db = new DataBase(getApplicationContext());
                db.open();
                Sefaresh_Dao sefaresh_dao = db.getSefaresh_Dao();
                sefaresh.setCount(num);
                sefaresh_dao.UpdateOrder(sefaresh);
                db.close();
                setResult(RESULT_OK);
                finish();

            } catch (Exception err) {

            }
        }

    }

    private int getNum() {
        String num_str = numET.getText().toString();
        int num = 0;
        if (num_str.length() > 0) {
            num = Integer.parseInt(num_str);
        }
        return num;
    }

    public void addNumber(View v) {
        int num = getNum();
        num++;
        numET.setText(num + "");
        updateFinalPrice();

    }

    public void subNumber(View v) {
        int num = getNum();
        if (num > 0) {
            num--;
            numET.setText(num + "");
        } else {
            numET.setText("0");
        }
        updateFinalPrice();
    }

    private void updateFinalPrice() {
        TextView finalPriceTV = findViewById(R.id.apa_tv_prd_price_final);
        int num = getNum();
        int finalPrice = 0;

        try {
            if (sefaresh.getDiscount().equals("0")) {
                String num_str = numET.getText().toString();
                int off = Integer.valueOf(App.userProfile.getDiscount());
                finalPrice = Integer.valueOf(sefaresh.getPrice()) * (100 - off) / 100 * num;
            } else {
                String num_str = numET.getText().toString();
                finalPrice = Integer.valueOf(sefaresh.getPrice()) * num;

            }
        } catch (Exception err) {
            finalPrice = Integer.valueOf(sefaresh.getPrice()) * num;

        }
        finalPriceTV.setText(UF.getPriceFormat(finalPrice, "fa") + " ریال");

    }

}
