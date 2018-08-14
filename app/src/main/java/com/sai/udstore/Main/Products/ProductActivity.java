package com.sai.udstore.Main.Products;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.Product;
import com.sai.udstore.Main.App;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.excomponents.Web;
import com.sai.udstore.sai.UF;

import org.json.JSONObject;

public class ProductActivity extends AppCompatActivity {

    public static Product prd;
    int productID, from;
    CheckBox favCB;
    TextView priceTV, nameTV, commentTV, unitTV, codeTV, discountTV;
    ImageView imageIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent data = getIntent();
        productID = data.getIntExtra("pos", -1);
        from = data.getIntExtra("from", -1);
        if (productID == -1) {
            finish();
            return;
        }
        findProduct();
        if (prd == null) {
            finish();
            return;
        }
        findViews();
        setValues();
    }

    private void findProduct() {
        if (from == Settings.From.OfferList) {
            for (Product p : App.products) {
                if (p.getId() == productID) {
                    prd = p;
                    return;
                }
            }
        } else {
            for (Product p : ProductsListActivity.prds) {
                if (p.getId() == productID) {
                    prd = p;
                    return;
                }
            }
        }

    }


    private void findViews() {
        favCB = findViewById(R.id.ap_cb_fav);
        priceTV = findViewById(R.id.apm_tv_price);
        commentTV = findViewById(R.id.apm_tv_comment);
        nameTV = findViewById(R.id.apm_tv_name);
        discountTV = findViewById(R.id.apm_tv_discount);

        unitTV = findViewById(R.id.apm_tv_unit);
        codeTV = findViewById(R.id.apm_tv_code);

        imageIV = findViewById(R.id.apm_img_products);
    }

    private void setValues() {

        favCB.setChecked(prd.isFav());
        priceTV.setText(prd.getPrice());
        commentTV.setText(prd.getRemark());
        nameTV.setText(prd.getName());
        discountTV.setText(prd.getDprice());
//        unitTV.setText(prd.getUnit());
//        codeTV.setText(getString(R.string.article_number) + prd.getCode());
        UF.loadandsave(getApplicationContext(), "", "", imageIV, prd.getImage(), 0, 0, R.drawable.logo, R.drawable.logo);

    }

    public void doOrder(View v) {

        Intent intent = new Intent(ProductActivity.this, ProductAddActivity.class);
        intent.putExtra("pos", productID);
//        intent.putExtra("code", prd.getCode());
        intent.putExtra("name", prd.getName());
//        intent.putExtra("unit", prd.getUnit());
        intent.putExtra("image", prd.getImage());


        startActivity(intent);
    }

    public void doFav(View v) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    JSONObject js = new JSONObject();
//                    String uniq = android.provider.Settings.Secure.getString(getContentResolver(),
//                            android.provider.Settings.Secure.ANDROID_ID);
//                    js.put("uniq", uniq);
//                    js.put("pid", prd.getId());
////                    js.put("token", App.userProfile.getToken());
//                    if (!prd.isFav()) {
//                        js.put("fav", Settings.Bool.TRUE);
//                    } else {
//                        js.put("fav", Settings.Bool.FALSE);
//                    }
//                    String response = Web.send(Settings.Urls.Favorite, js.toString());
//                    if (UF.UpdateFavorite(response)) {
//                        prd.setFav(!prd.isFav());
//
//                    }
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            favCB.setChecked(prd.isFav());
//                        }
//                    });
//                } catch (Exception errr) {
//
//                }
//
//
//            }
//        }).start();
    }
}
