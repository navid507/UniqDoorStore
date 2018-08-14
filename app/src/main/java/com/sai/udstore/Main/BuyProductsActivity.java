package com.sai.udstore.Main;

import android.support.v7.app.AppCompatActivity;

public class BuyProductsActivity extends AppCompatActivity {
//
//    private SefareshTask mAuthTask = null;
//    private Button ok;
//    private View mProgressView;
//    private EditText desc;
//    private EB_Preference prefrence;
//    private User userp;
//    private Typeface vazir;
//    private String product_id = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_buy_products);
//
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            if (extras.containsKey("abp_pid_product")) {
//                product_id = extras.getString("abp_pid_product");
//            }
//        }
//
//        vazir = Typeface.createFromAsset(getAssets(), "font/vazir.ttf");
//        prefrence = new EB_Preference(getApplicationContext());
//        userp = prefrence.User();
//
//        ok = (Button) findViewById(R.id.abp_btn_ok_buy);
//        desc = (EditText) findViewById(R.id.abp_tv_comment_buy);
//        mProgressView = findViewById(R.id.apb_pb_register);
//
//        ok.setTypeface(vazir);
//        desc.setTypeface(vazir);
//
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                attemptSefaresh();
//            }
//        });
//    }
//
//    private void attemptSefaresh() {
//        if (mAuthTask != null) {
//            return;
//        }
//
//        if (UF.Check_internet(getApplicationContext())) {
//            showProgress(true);
//            String url = String.format(Settings.Urls.Sefaresh, userp.getUserID(), userp.getDeviceID(),
//                    product_id, desc.getText().toString());
//            mAuthTask = new SefareshTask(url);
//            mAuthTask.execute((Void) null);
//        } else {
//            UF.showwifidlg(Settings.Activity.Register, this, getApplicationContext());
//        }
//    }
//
//    public class SefareshTask extends AsyncTask<Void, Void, Boolean> {
//        private final String url;
//
//        SefareshTask(String url) {
//            this.url = url;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            try {
//                URL obj = new URL(url);
//                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//                con.setRequestMethod("GET");
//                int responseCode = con.getResponseCode();
//                BufferedReader in = new BufferedReader(
//                        new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//                return UF.Update_Sefaresh(response.toString(), getApplicationContext(), "");
//            } catch (Exception err) {
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//            if (success) {
//                Toast.makeText(BuyProductsActivity.this, "سفارش با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
//                finish();
//            } else {
//                Snackbar trans_sb;
//                trans_sb = Snackbar.make(getCurrentFocus(), getResources().getString(R.string.server_connection_problem), Snackbar.LENGTH_INDEFINITE);
//                trans_sb.setActionTextColor(getResources().getColor(R.color.lightwhite));
//                trans_sb.setAction(getResources().getString(R.string.send_agian), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        attemptSefaresh();
//                    }
//                });
//                trans_sb.show();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case Settings.Activity.SendOrder:
//                if (resultCode == RESULT_OK) {
//                    String res = data.getStringExtra("factor");
//                    showSuccessDialog(res);
//                }
//                break;
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }
//
//    private void showSuccessDialog(String fish) {
//        Context context = getApplicationContext();
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        String mainmsg = context.getString(R.string.factor_registered, fish);
//        String Ordersstr = context.getString(R.string.see_orders);
//        String OKlstr = context.getString(R.string.OK);
//
//        builder.setMessage(mainmsg).setPositiveButton(Ordersstr, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                setResult(RESULT_OK);
//                finish();
//            }
//        }).setNegativeButton(OKlstr, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//
//                finish();
//            }
//        });
//        // Create the AlertDialog object and return it
//        builder.create().show();
//    }
//
//    private void deleteOrder() {
//
//        DataBase db = new DataBase(getApplicationContext());
//        db.open();
//        Sefaresh_Dao sefaresh_dao = db.getSefaresh_Dao();
//        sefaresh_dao.DeleteAllOrder();
//        db.close();
//    }
//
//    private void sendOrder() {
//        Intent getAddr = new Intent(BuyProductsActivity.this, SendOrderActivity.class);
//        startActivityForResult(getAddr, Settings.Activity.SendOrder);
//
//    }
}

