package com.sai.udstore.Main.Products;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sai.udstore.DataBase.DataBase;
import com.sai.udstore.DataBase.Models.Product;
import com.sai.udstore.Main.Adapter.ProductRecyclerViewAdapater;
import com.sai.udstore.Main.Adapter.ProductRecyclerViewAdapater.IProductCaller;
import com.sai.udstore.Main.App;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.excomponents.Web;
import com.sai.udstore.sai.UF;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductsListActivity extends AppCompatActivity implements IProductCaller {
    private ProductRecyclerViewAdapater adapter;

    //    private GetCatsTask mCatTask = null;
    RecyclerView recyclerView;
    static List<Product> prds = new ArrayList<>();
    private View mProgressCatView;
    private DataBase db;
    private int parentID, groupID, typeID;
    String catName;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        title = findViewById(R.id.ap_tv_title);
        LinearLayout mmll = findViewById(R.id.mmll);
        UF.setAllFonts(App.appFont, mmll);
        setHeader();
        Intent data = getIntent();
        parentID = data.getIntExtra("cat_id", -1);
        groupID = data.getIntExtra("groupID", -1);
        typeID = data.getIntExtra("typeID", -1);
        catName = data.getStringExtra("catName");


        if (parentID == -1) {
            return;
        }
        title.setText(catName);
        recyclerView = findViewById(R.id.ap_lv_prds);
        mProgressCatView = findViewById(R.id.ap_pv_cat);
        recyclerView.hasFixedSize();
//      recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ProductRecyclerViewAdapater(prds, getApplicationContext(), this);
        db = new DataBase(getApplicationContext());

        populateCats();

    }

    private void setHeader() {
        (findViewById(R.id.main_orders)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
        (findViewById(R.id.main_toggleNavigation)).setVisibility(View.GONE);
    }

    private void populateCats() {
//        if (mCatTask != null) {
//            return;
//        }
        prds.clear();
        showProgress(true, true);
//        mCatTask = new GetCatsTask();
//        mCatTask.execute((Void) null);
        for (Product p : App.products) {
            if (p.getGroupID() == groupID && p.getTypeID() == typeID) {
                prds.add(p);
            }
        }

        setCatList();
    }

    private void setCatList() {
        showProgress(false, true);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);
        adapter.setList(prds);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show, boolean hideList) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            if (hideList) {
                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                recyclerView.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });
            }


            mProgressCatView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressCatView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressCatView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressCatView.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onProductClicked(int pos) {
//        Intent intent = new Intent(ProductsListActivity.this, ProductActivity.class);
//        intent.putExtra("pos", prds.get(pos).getId());
//        intent.putExtra("from", Settings.From.ProductList);
//        startActivity(intent);

        ProductActivity.prd = prds.get(pos);
        Intent intent = new Intent(ProductsListActivity.this, ProductAddActivity.class);
        intent.putExtra("pos", prds.get(pos).getId());
//        intent.putExtra("code", prd.getCode());
        intent.putExtra("name", prds.get(pos).getName());
//        intent.putExtra("unit", prd.getUnit());
        intent.putExtra("image", prds.get(pos).getImage());


        startActivityForResult(intent, Settings.Activity.BuyProductActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(getApplicationContext(), "با موفقیت به سبد اضافه گردید", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GetCatsTask extends AsyncTask<Void, Void, Boolean> {

        GetCatsTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                JSONObject js = new JSONObject();
//                js.put("token", App.userProfile.getToken());
                js.put("cid", parentID);

                String response = Web.send(Settings.Urls.ProductOfCat, js.toString());

                prds = UF.Update_Products(response);

            } catch (Exception err) {
                db.close();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
//            mCatTask = null;
            showProgress(false, true);
            if (success) {
                setCatList();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
//            mCatTask = null;
            showProgress(false, true);
        }
    }

}
