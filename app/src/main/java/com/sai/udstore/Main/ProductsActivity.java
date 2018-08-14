package com.sai.udstore.Main;

import android.support.v7.app.AppCompatActivity;

public class ProductsActivity extends AppCompatActivity {//implements ProductRecyclerViewAdapater.IProductCaller {
//
//    private ProductRecyclerViewAdapater adapter;
//    RecyclerView recyclerView;
//    private View mProgressPrdView;
//    TextView title_prd, noresult;
//    private GetProductsTask mdeleOrderTask = null;
//    private DataBase db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_products);
//
//        db = new DataBase(getApplicationContext());
//
//        noresult = (TextView) findViewById(R.id.ap_tv_noresult);
//        title_prd = (TextView) findViewById(R.id.ap_tv_title);
//        recyclerView = (RecyclerView) findViewById(R.id.ap_lv_list);
//        mProgressPrdView = findViewById(R.id.ap_pv_main);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        adapter = new ProductRecyclerViewAdapater("", Settings.product, getApplicationContext(), this);
//        recyclerView.setAdapter(adapter);
//
//        title_prd.setText(String.format(getString(R.string.productsofcat), CategoryFragment.selectedCat.getName()));
//
//        populateProd();
//    }
//
//    private void populateProd() {
//        if (mdeleOrderTask != null) {
//            return;
//        }
//        showPrdProgress(true, false);
//        try {
//            mdeleOrderTask = new GetProductsTask();
//            mdeleOrderTask.execute((Void) null);
//        } catch (Exception er) {
//
//        }
//    }
//
//    private void setListPrd() {
//        if (Settings.product != null && Settings.product.size() > 0) {
//            adapter.setList(Settings.product);
//            noresult.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//        } else {
//            recyclerView.setVisibility(View.VISIBLE);
////            noresult.setVisibility(View.VISIBLE);
////            recyclerView.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void onProductClicked(int pos) {
//        Intent intent = new Intent(ProductsActivity.this, Products_moreActivity.class);
//        intent.putExtra("apm_page_product", String.valueOf(pos));
//        startActivity(intent);
//    }
//
//    public class GetProductsTask extends AsyncTask<Void, Void, Boolean> {
//
//        GetProductsTask() {
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            try {
//                Settings.product.clear();
//                db.open();
//                Product_Dao product_dao = db.getProduct_Dao();
//                Settings.product = product_dao.All(CategoryFragment.selectedCat.getId());
//                db.close();
//
//                if (Settings.product == null)
//                    return false;
//                else return true;
//            } catch (Exception err) {
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mdeleOrderTask = null;
//            showPrdProgress(false, true);
//            setListPrd();
//        }
//
//        @Override
//        protected void onCancelled() {
//            mdeleOrderTask = null;
//            showPrdProgress(false, true);
//        }
//    }
//
//    /**
//     * Shows the progress UI and hides the login form.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showPrdProgress(final boolean show, boolean hideList) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            if (hideList) {
//                recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
//                recyclerView.animate().setDuration(shortAnimTime).alpha(
//                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
//                    }
//                });
//            }
//
//            mProgressPrdView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressPrdView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressPrdView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressPrdView.setVisibility(show ? View.VISIBLE : View.GONE);
//            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }
}
