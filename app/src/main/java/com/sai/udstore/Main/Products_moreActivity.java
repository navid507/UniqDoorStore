package com.sai.udstore.Main;

import android.support.v7.app.AppCompatActivity;

public class Products_moreActivity extends AppCompatActivity {
//
//    private int mImageThumbSize;
//    private ViewPager viewerpage;
//    private ProductsMoreAdapter adapter;
//    private String position = "";
//    private EB_Preference prefrence;
//    private User userp;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_pager);
//
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            position = extras.getString("apm_page_product");
//        }
//
//        viewerpage = (ViewPager) findViewById(R.id.pager);
//        prefrence = new EB_Preference(getApplicationContext());
//        userp = prefrence.User();
//
//        final DisplayMetrics displayMetrics = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail);
//
//        adapter = new ProductsMoreAdapter(this, Settings.product, this);
//        viewerpage.setAdapter(adapter);
//        viewerpage.setCurrentItem(Integer.valueOf(position));
//    }
//
//    public void doRegister(String pid) {
//        int regstate = userp.getRegState();
//        switch (regstate) {
//            case Settings.Register_State.Nothing:
//                showRegisterForm();
//                break;
//            case Settings.Register_State.Phone:
//                showVerifyForm();
//                break;
//            case Settings.Register_State.Code:
//                showSignUpForm();
//                break;
//            case Settings.Register_State.OK:
//                showSefareshForm(pid);
//                break;
//        }
//    }
//
//    private void showVerifyForm() {
//        Intent i = new Intent(Products_moreActivity.this, VerifyActivity.class);
//        startActivityForResult(i, Settings.Activity.Verify);
//    }
//
//    private void showRegisterForm() {
//        Intent i = new Intent(Products_moreActivity.this, RegisterActivity.class);
//        startActivityForResult(i, Settings.Activity.Register);
//    }
//
//    private void showSignUpForm() {
//        Intent i = new Intent(Products_moreActivity.this, SignUpActivity.class);
//        startActivityForResult(i, Settings.Activity.SignUp);
//    }
//
//    private void showSefareshForm(String pid) {
//        Intent intent = new Intent(Products_moreActivity.this, BuyProductsActivity.class);
//        intent.putExtra("abp_pid_product", pid);
//        startActivityForResult(intent, Settings.Activity.BuyProductActivity);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case Settings.Activity.Register:
//                if (resultCode == RESULT_OK) {
//                    doRegister("");
//                }
//                break;
//            case Settings.Activity.Verify:
//                if (resultCode == RESULT_OK) {
//                    doRegister("");
//                }
//                break;
//            case Settings.Activity.SignUp:
//                if (resultCode == RESULT_OK) {
//                    doRegister("");
//                }
//                break;
//            case Settings.Activity.BuyProductActivity:
//                if (resultCode == RESULT_OK)
//                {
//
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
