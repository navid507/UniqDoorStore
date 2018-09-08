package com.sai.udstore.Main.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sai.udstore.DataBase.Models.Category;
import com.sai.udstore.Main.Adapter.CategoryRecyclerViewAdapater;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.Main.Products.ProductsListActivity;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.excomponents.Web;
import com.sai.udstore.sai.UF;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CategoryFragment extends Fragment {

    private MainActivity activity = null;
    private Resources resources = null;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private CategoryRecyclerViewAdapater adapter;
    //    private GetCatsTask mCatTask = null;
    RecyclerView recyclerView;
    static List<Category> cats;
    private View mProgressCatView;
    public Category currentCat;// = new Category();
    public static Category selectedCat = new Category();
    ArrayList<Category> filtered_cats = new ArrayList<Category>();
//    TextView title;

    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).curFragment = Settings.Fragments.Product;
        ((MainActivity) getActivity()).setMainTitle();
        activity = (MainActivity) getActivity();
        resources = getResources();

        View v = inflater.inflate(R.layout.fragment_category, container, false);

        currentCat = new Category();
        currentCat.setId(-1);
        currentCat.setName(getString(R.string.cats));
        cats = new ArrayList<>();

//        title = (TextView) v.findViewById(R.id.ac_tv_cat);
        recyclerView = (RecyclerView) v.findViewById(R.id.ac_lv_cat);
        mProgressCatView = v.findViewById(R.id.ac_pv_cat);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CategoryRecyclerViewAdapater(cats, getContext(), this);

        populateCats();

        return v;
    }

    public void click_item_list(int position) {
        if (position >= filtered_cats.size())
            return;
//        if (currentCat.getParent_id() == -1) {
//
//        } else {
        currentCat = filtered_cats.get(position);

//        }
        if (currentCat.getType() == 1) {
            setCatList();
        } else {
            selectedCat = currentCat;
            currentCat = findCat(currentCat.getParent_id());
            showCatWithProduct();
        }
    }

    public boolean goBack() {
        currentCat = findCat(currentCat.getParent_id());
        if (currentCat != null) {
            setCatList();
            return true;
        }
        return false;
    }


    private Category findCat(int id) {
        int size = cats.size();
        for (int i = 0; i < size; i++) {
            if (cats.get(i).getId() == id) {
                return cats.get(i);
            }
        }
        return null;
    }

    private void populateCats() {
        cats.clear();
        cats = App.cats;
        currentCat.setParent_id(0);
        cats.add(currentCat);
        showProgress(false, true);
        setCatList();
    }

    private void setCatList() {
        filtered_cats.clear();


        int size = cats.size();
        for (int i = 0; i < size; i++) {
            if (cats.get(i).getParent_id() == currentCat.getId()) {
                filtered_cats.add(cats.get(i));
            }
        }
//        if (filtered_cats.size() > 0) {
//        if (currentCat.getParent_id() != 0) {
        ((MainActivity) getActivity()).setMainTitle(R.string.main_text_products);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);
        adapter.setList(filtered_cats);
//        } else {
//            ((MainActivity) getActivity()).setMainTitle(R.string.main_text_brands);
//
//            recyclerView.setAdapter(adapterBrands);
//            adapterBrands.setList(filtered_cats);
//        }
//        }
//        else {
//            recyclerView.setVisibility(View.GONE);
//
//            Intent posi = new Intent(activity, ProductsActivity.class);
//            activity.startActivityForResult(posi, Settings.Activity.Products);
//        }
//        if (currentCat.getId() == -1) {
//            title.setText(currentCat.getName());
//
//        } else {
//            title.setText(String.format(getString(R.string.subcatsof), currentCat.getName()));
//        }
    }


    public boolean checkBack() {
        if (currentCat.getId() != -1) {
            if (!goBack()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
//    public class GetCatsTask extends AsyncTask<Void, Void, Boolean> {
//
//        GetCatsTask() {
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            try {
//                App.cats.clear();
////                db.open();
////                Category_Dao category_dao = db.getCategory_Dao();
////                Settings.category = category_dao.All(Settings.soft_id);
////                db.close();
//
//                String response = Web.send(Settings.Urls.Category, "");
//
//                UF.UpdateCategory(response);
//
//                cats = App.cats;
//                currentCat.setParent_id(0);
//                cats.add(currentCat);
//                if (cats == null)
//                    return false;
//                else return true;
//            } catch (Exception err) {
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mCatTask = null;
//            showProgress(false, true);
//            if (success) {
//                setCatList();
//            } else {
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mCatTask = null;
//            showProgress(false, true);
//        }
//    }
    private void showCatWithProduct() {
        Intent posi = new Intent(activity, ProductsListActivity.class);
        posi.putExtra("cat_id", selectedCat.getId());
        posi.putExtra("groupID", selectedCat.getPU_Gruop());
        posi.putExtra("typeID", selectedCat.getPU_Type());
        posi.putExtra("catName", selectedCat.getName());
        activity.startActivityForResult(posi, Settings.Activity.ProductList);

    }


}
