package com.sai.udstore.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sai.udstore.Main.Fragment.AboutFragment;
import com.sai.udstore.Main.Fragment.CategoryFragment;
import com.sai.udstore.Main.Fragment.ContactUsFragment;
import com.sai.udstore.Main.Fragment.DownloadFragment;
import com.sai.udstore.Main.Fragment.FavoriteFragment;
import com.sai.udstore.Main.Fragment.HomeFragment;
import com.sai.udstore.Main.Fragment.InvoicesHistoryFragment;
import com.sai.udstore.Main.Fragment.NewsMoreFragment;
import com.sai.udstore.Main.Order.InvoiceFragment;
import com.sai.udstore.Main.Fragment.ProfileFragment;
import com.sai.udstore.Main.Register.RegisterActivity;
import com.sai.udstore.Main.Register.RegisterAlertActivity;
import com.sai.udstore.Main.Register.SignUpActivity;
import com.sai.udstore.Main.Register.VerifyActivity;
import com.sai.udstore.Prefrence.Daos.User;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.Settings;
import com.sai.udstore.sai.UF;
import com.sai.udstore.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //    public int lastview = 0;
    private int time_interval = 2000;
    private long oldCurrentTimeMillis;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private EB_Preference prefrence;
    private User userp;
    public int curFragment = Settings.Fragments.Home;
    Fragment fragment = null;
    FrameLayout fll;
    TextView titleMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fll = findViewById(R.id.frame_container);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        titleMain = findViewById(R.id.main_nav_title);

        final ImageView toggleNavigation = findViewById(R.id.main_toggleNavigation);
        toggleNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleNavigation(view);
            }
        });

        final ImageView orders = findViewById(R.id.main_orders);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(Settings.Fragments.Order, "", "", "");
            }
        });

        titleMain.setTypeface(App.appFont);
//        final ImageView backNavigation = findViewById(R.id.main_back);
//        backNavigation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//
//            }
//        });

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        prefrence = new EB_Preference(getApplicationContext());
        userp = prefrence.User();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

//        doRegister();
        displayView(curFragment, "", "", "");

        setNavigationUserProfile();

    }

    private void setNavigationUserProfile() {
        if (App.userProfile != null) {
            View headerLayout = navigationView.getHeaderView(0);
            TextView unameTV = headerLayout.findViewById(R.id.nhm_title);
            TextView uphoneTV = headerLayout.findViewById(R.id.nhm_phone);
            unameTV.setText(App.userProfile.getDisplayName());
            uphoneTV.setText(App.userProfile.getMobile());

        }
    }

    public void showProductsOnTime(int delay) {
        Timer tm = new Timer();
        tm.schedule(new TimerTask() {

            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        displayView(Settings.Fragments.Product, "0", "", "");

                    }
                });
            }
        }, delay);
    }

    public void showsliderontime(int delay) {
        Timer tm = new Timer();
        tm.schedule(new TimerTask() {

            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (drawer.isDrawerOpen(GravityCompat.END)) {
                        } else {
                            drawer.openDrawer(GravityCompat.END);
                        }
                    }
                });
            }
        }, delay);
    }

    public void toggleNavigation(View v) {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);
        }
    }

    public void setTimeInterval(int value) {
        time_interval = value;
    }

    public void onFirstBackPressed() {
        Toast.makeText(getBaseContext(), "برای خروج دوباره کلیک کنید", Toast.LENGTH_SHORT).show();
        showsliderontime(0);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
//            if (curFragment == Settings.Fragments.Product) {
            if (fragment instanceof CategoryFragment) {
                if (((CategoryFragment) fragment).checkBack()) {
                    return;
                }
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() == 1) {
                if (oldCurrentTimeMillis + time_interval > System.currentTimeMillis()) {
                    System.exit(0);
                } else {
                    onFirstBackPressed();
                }
                oldCurrentTimeMillis = System.currentTimeMillis();
            } else {
                super.onBackPressed();
// change fragment by hand!
                fragment = fragmentManager.getFragments().get(0);
//                fragmentManager.popBackStack();
//                int fbc = fragmentManager.getBackStackEntryCount() - 1;
//                List<Fragment> fragments = fragmentManager.getFragments();
//                if (fbc < fragmentManager.getFragments().size()) {
//                    fragment = fragmentManager.getBackStackEntryAt(fbc);
//                }
            }
//            setMainTitle();

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                displayView(Settings.Fragments.Home, "0", "", "");
                break;
            case R.id.nav_products:
                displayView(Settings.Fragments.Product, "0", "", "");
                break;
            case R.id.nav_order:
                displayView(Settings.Fragments.Order, "", "", "");
                break;
            case R.id.nav_history:
                displayView(Settings.Fragments.Invoices, "", "", "");
                break;
            case R.id.nav_downloads:
                displayView(Settings.Fragments.Download, "", "", "");
                break;
            case R.id.nav_about_us:
                displayView(Settings.Fragments.AboutUs, "", "", "");
                break;

//            case R.id.nav_share:
//                displayView(Settings.Fragments.Share, "", "", "");
//                break;
            case R.id.nav_profile:
                displayView(Settings.Fragments.Profile, "", "", "");
                break;
            case R.id.nav_contact_us:
                displayView(Settings.Fragments.ContactUs, "", "", "");
                break;
            case R.id.nav_exit:
                displayView(Settings.Fragments.Exit, "", "", "");
                break;


        }
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    public void setMainTitle(int title) {
        titleMain.setText(title);

    }

    public void setMainTitle() {
//        if (fragment instanceof  )
        switch (curFragment) {
            case Settings.Fragments.Home:
                titleMain.setText(R.string.main_text_home);
                break;
            case Settings.Fragments.Product:
                titleMain.setText(R.string.main_text_categories);
                break;
            case Settings.Fragments.Order:
                titleMain.setText(R.string.main_text_orders);
                break;
            case Settings.Fragments.Favorites:
                titleMain.setText(R.string.main_text_favorites);
                break;
            case Settings.Fragments.Invoices:
                titleMain.setText(R.string.main_text_invoices);
                break;
            case Settings.Fragments.AboutUs:
                titleMain.setText(R.string.main_text_about_us);
                break;
            case Settings.Fragments.Profile:
                titleMain.setText(R.string.main_text_profile);
                break;
            case Settings.Fragments.ContactUs:
                titleMain.setText(R.string.main_text_contact_us);
                break;
            case Settings.Fragments.News:
                titleMain.setText(R.string.main_text_news);
                break;
            case Settings.Fragments.Share:
//                titleMain.setText(R.string.main_text_);
                break;
            case Settings.Fragments.Exit:
//                titleMain.setText(R.string.main_text_);
                break;
            default:
                break;
        }
    }

    public void displayView(int position, String param1, String param2, String param3) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (position) {
            case Settings.Fragments.News:
                fragment = NewsMoreFragment.newInstance(param1, param2);
                break;
            case Settings.Fragments.Home:
                fragment = HomeFragment.newInstance(param1, param2);
                break;
            case Settings.Fragments.Product:
                fragment = CategoryFragment.newInstance(param1, param2);
                break;
            case Settings.Fragments.Download:
                fragment = DownloadFragment.newInstance(param1, param2);
                break;
            case Settings.Fragments.Order:
                fragment = InvoiceFragment.newInstance(param1, param2);
                break;
            case Settings.Fragments.Favorites:
                fragment = FavoriteFragment.newInstance(param1, param2);
                break;
            case Settings.Fragments.Invoices:
                fragment = InvoicesHistoryFragment.newInstance(param1, param2);
                break;
            case Settings.Fragments.AboutUs:
                fragment = AboutFragment.newInstance("", "");
                break;
            case Settings.Fragments.Profile:
                fragment = ProfileFragment.newInstance(param1, param2);
                //                doRegister();
                break;

            case Settings.Fragments.ContactUs:
                //fragment = new ContactUs1Fragment();
                fragment = new ContactUsFragment();
                break;
            case Settings.Fragments.Share:
                UF.sendmyself(getApplicationContext());
                break;
            case Settings.Fragments.Exit:
                System.exit(0);
                break;

            case Settings.Fragments.Register:
                int regstate = userp.getRegState();
                if ((regstate == Settings.Register_State.OK)) {
                    showDialog();
                    fragment = ProfileFragment.newInstance("", "");
//                    showTryRegister();
                } else {
                    doRegister();
                }
                break;
            default:
                break;


        }

        try {
            if (fragment != null) {
//                lastview = position;
                curFragment = position;
                setMainTitle();
                ft.replace(R.id.frame_container, fragment, String.valueOf(position));
                ft.addToBackStack(null);
                ft.commit();
            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        } catch (Exception ex) {
        }
    }

    public void doRegister() {
        if (App.userProfile != null) {
            showProductsOnTime(150);
            showsliderontime(1400);
            return;
        }
//        userp.setRegState(Settings.Register_State.Phone);
        int regstate = userp.getRegState();

        switch (regstate) {
            case Settings.Register_State.Nothing:
                showRegisterForm();
                break;
            case Settings.Register_State.Phone:
                showVerifyForm();
                break;
            case Settings.Register_State.Code:
//                showSignUpForm();
//                break;
            case Settings.Register_State.OK:
//                showSignUpForm();
                displayView(Settings.Fragments.Product, "0", "", "");
                showsliderontime(1400);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Settings.Activity.Register:

                if (resultCode == RESULT_OK) {
                    doRegister();
                } else {
                    finish();
                }
                break;
            case Settings.Activity.Verify:
                if (resultCode == RESULT_OK) {
                    showSuccessfulVerify();
                    doRegister();
                } else if (resultCode == RESULT_FIRST_USER) {

                    doRegister();
                }
                break;
            case Settings.Activity.Register_alert:
                if (resultCode == RESULT_OK) {
                    doRegister();
                }
                break;
        }
        if (curFragment == Settings.Fragments.Product) {
            if (requestCode == Settings.Activity.Products) {
                if (UF.Check_internet(getApplicationContext())) {
//                populateCats();
                    ((CategoryFragment) fragment).goBack();
                } else {
                    ((CategoryFragment) fragment).goBack();
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showSuccessfulVerify() {
        final Snackbar sb = Snackbar.make(fll, "با تشکر از ثبت نام شما", Snackbar.LENGTH_INDEFINITE);
        sb.setAction(getString(R.string.OK), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sb.dismiss();
            }
        });
        sb.show();
    }


    private void showInvoicesHistory() {
        Intent i = new Intent(MainActivity.this, VerifyActivity.class);
        startActivityForResult(i, Settings.Activity.Verify);
    }

    private void showVerifyForm() {
        Intent i = new Intent(MainActivity.this, VerifyActivity.class);
        startActivityForResult(i, Settings.Activity.Verify);
    }

    private void showRegisterForm() {
        Intent i = new Intent(MainActivity.this, RegisterActivity.class);
        startActivityForResult(i, Settings.Activity.Register);
    }

    private void showSignUpForm() {
        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
        startActivityForResult(i, Settings.Activity.SignUp);
    }

    private void showTryRegister() {
        Intent i = new Intent(MainActivity.this, RegisterAlertActivity.class);
        startActivityForResult(i, Settings.Activity.Register_alert);
    }

    public void setTeamDetails(int id) {
//        displayView(Settings.Fragments.TeamDetail, String.valueOf(id), "", "");
    }

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("آیا مایل به ثبت نام مجدد می باشید؟");
//        dialog.setMessage("آیا مایل به ثبت نام مجدد می باشید؟");
//        dialog.setIcon(R.drawable.ic_launcher);

        dialog.setPositiveButton("بله", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                userp.setRegState(Settings.Register_State.Nothing);
                doRegister();
            }
        });

        dialog.setNegativeButton("خیر", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
