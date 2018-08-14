package com.sai.udstore.Main.Products;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sai.udstore.DataBase.Daos.Sefaresh_Dao;
import com.sai.udstore.DataBase.DataBase;
import com.sai.udstore.DataBase.Models.Sefaresh;
import com.sai.udstore.Main.App;
import com.sai.udstore.R;
import com.sai.udstore.sai.UF;

public class ProductAddActivity extends AppCompatActivity {

    EditText numET;
    Sefaresh order;
//    String code, name, unit, img;
//    int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        LinearLayout mmll = findViewById(R.id.mmll);
        UF.setAllFonts(App.appFont, mmll);

        TextView nameTV = findViewById(R.id.apa_tv_prd_name);
        TextView priceTV = findViewById(R.id.apa_tv_prd_price);
        TextView offTV = findViewById(R.id.apa_tv_prd_off);
        numET = findViewById(R.id.apa_et_number);

        nameTV.setTypeface(App.appFont);


        nameTV.setText(ProductActivity.prd.getName());
        priceTV.setText(UF.getPriceFormat(ProductActivity.prd.getPrice(), "fa") + "ریال");
        if (ProductActivity.prd.getDprice().equals("0")) {
            offTV.setText(UF.getPriceFormat(App.userProfile.getDiscount(), "fa") + "%");
        } else {
            offTV.setText("ندارد");

        }
        getOrder();
        if (order != null) {
            numET.setText(order.getCount() + "");
        } else {
            order = new Sefaresh();
            order.setCount(0);
//            order.setCode(ProductActivity.prd.getCode());
//            order.setSubject(ProductActivity.prd.getUnit());
            order.setName(ProductActivity.prd.getName());
            order.setProduct_id(ProductActivity.prd.getId());
            order.setPrice(ProductActivity.prd.getPrice());
            order.setSubject(ProductActivity.prd.getRemark());
            order.setImage(ProductActivity.prd.getImage());
            order.setDiscount(ProductActivity.prd.getDprice());

//         String price, String tax, String desc, String unit, String img, int count) {
//

        }

        updateFinalPrice();
    }


    private void getOrder() {
        DataBase db = new DataBase(getApplicationContext());
        db.open();
        Sefaresh_Dao sefaresh_dao = db.getSefaresh_Dao();
        order = sefaresh_dao.GetOrderOfProduct(ProductActivity.prd.getId());
        db.close();
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
                order.setCount(num);
                sefaresh_dao.SetCount(order);
//                sefaresh_dao.SetCount(ProductActivity.prd.getId(), ProductActivity.prd.getName(), ProductActivity.prd.getPrice(),
//                        ProductActivity.prd.getTax1(), ProductActivity.prd.getCode(), ProductActivity.prd.getUnit(),
//                        ProductActivity.prd.getImage(), num);
                db.close();
                finish();

            } catch (Exception err) {

            }
        }

    }

    public void deleteOrder(View v) {
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
            if (ProductActivity.prd.getDprice().equals("0")) {
                int off = Integer.valueOf(App.userProfile.getDiscount());
                finalPrice = Integer.valueOf(ProductActivity.prd.getPrice()) * (100 - off) / 100 * num;
            } else {
                finalPrice = Integer.valueOf(ProductActivity.prd.getPrice()) * num;

            }
        } catch (Exception err) {
            finalPrice = Integer.valueOf(ProductActivity.prd.getPrice()) * num;

        }
        finalPriceTV.setText(UF.getPriceFormat(finalPrice, "fa") + " ریال");

    }

}
