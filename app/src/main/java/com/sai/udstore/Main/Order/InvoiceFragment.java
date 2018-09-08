package com.sai.udstore.Main.Order;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sai.udstore.DataBase.Daos.Sefaresh_Dao;
import com.sai.udstore.DataBase.DataBase;
import com.sai.udstore.DataBase.Models.Sefaresh;
import com.sai.udstore.Main.Adapter.OrdersRecyclerViewAdapater;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.Prefrence.Daos.User;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.excomponents.Web;
import com.sai.udstore.sai.UF;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceFragment extends Fragment implements OrdersRecyclerViewAdapater.IOrderCaller {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private List<Sefaresh> orders;
    RecyclerView ordersRV;
    TextView total_priceTV, total_taxTV;
    Button send;
    private OrdersRecyclerViewAdapater ordersRecyclerViewAdapater;
    ProgressBar loadingPB;
    LoginUserTask mAuthTask;

    public InvoiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoiceFragment.
     */
    public static InvoiceFragment newInstance(String param1, String param2) {
        InvoiceFragment fragment = new InvoiceFragment();
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
        View root;
        root = inflater.inflate(R.layout.fragment_order, container, false);
        LinearLayout mll = root.findViewById(R.id.fo_mainll);
        UF.setAllFonts(App.appFont, mll);
        ordersRV = root.findViewById(R.id.fo_rv_orders);
        total_taxTV = root.findViewById(R.id.fo_tv_total_tax);
        total_priceTV = root.findViewById(R.id.fo_tv_total_price);
        loadingPB = root.findViewById(R.id.fp_pb_loading);

        send = root.findViewById(R.id.fo_bt_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOrder();
            }
        });

        populateOrders();
        ordersRecyclerViewAdapater = new OrdersRecyclerViewAdapater(orders, getContext(), this);
        ordersRV.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersRV.setAdapter(ordersRecyclerViewAdapater);
        ordersRecyclerViewAdapater.setList(orders);
        setValues();
        return root;
    }


    private void setValues() {
        int price = 0;
        for (Sefaresh o : orders) {
            double p = Double.valueOf(o.getPrice()) * o.getCount();
            if (o.getDiscount().equals("1")) {
                int off = Integer.valueOf(App.userProfile.getDiscount());
                p = (p * (100 - off)) / 100;
            }
//            double taxP = Double.valueOf(o.getDiscount()) / 100;
//            double taxO = p * taxP;

            price += p;
//            tax += taxO;
        }

        total_taxTV.setText(UF.getPriceFormat(App.userProfile.getCredit(), "fa"));
        total_priceTV.setText(UF.getPriceFormat(price, "fa"));

        if (orders.size() == 0) {
            send.setAlpha((float) 0.7);
        } else {
            send.setAlpha((float) 1.0);

        }

    }

    public void populateOrders() {
        DataBase db = new DataBase(getContext());
        db.open();
        Sefaresh_Dao sefaresh_dao = db.getSefaresh_Dao();
        orders = sefaresh_dao.All();
        db.close();

    }

    private void sendOrder() {
        if (orders.size() == 0) {
            Toast.makeText(getContext(), "سبد خرید خالی می باشد", Toast.LENGTH_LONG).show();
            return;
        }
        Intent getAddr = new Intent(getActivity(), SendOrderActivity.class);
        startActivityForResult(getAddr, Settings.Activity.SendOrder);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Settings.Activity.SendOrder:
                if (resultCode == RESULT_OK) {
                    emptyOrder();
                    String fish = data.getStringExtra("factor");
                    refreshProfile();
                    showSuccessDialog(fish);
                } else if (resultCode == -15) {
                    String fish = data.getStringExtra("errMSG");
                    showFailedDialog(fish);
                }
                break;
            case Settings.Activity.EditOrder:
                populateOrders();
                ordersRecyclerViewAdapater.setList(orders);
                setValues();
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onOrderClicked(int pos) {
        Intent editOrder = new Intent((MainActivity) getActivity(), InvoiceEditActivity.class);
        int oid = orders.get(pos).getId();
        editOrder.putExtra("oid", oid);
        startActivityForResult(editOrder, Settings.Activity.EditOrder);
    }


    public void emptyOrder() {
        DataBase db = new DataBase(getContext());
        db.open();
        Sefaresh_Dao sefaresh_dao = db.getSefaresh_Dao();
        sefaresh_dao.DeleteAllOrder();
        db.close();
    }

    private void showSuccessDialog(String fish) {

        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String mainmsg = context.getString(R.string.factor_registered, fish);
        String Ordersstr = context.getString(R.string.see_orders);
        String OKlstr = context.getString(R.string.OK);

        builder.setMessage(mainmsg).setPositiveButton(Ordersstr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ((MainActivity) getActivity()).displayView(Settings.Fragments.Invoices, "goforOrdersw", "", "");

            }
        }).setNegativeButton(OKlstr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ((MainActivity) getActivity()).displayView(Settings.Fragments.Product, "", "", "");

            }
        });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    AlertDialog alertDialog;

    private void showFailedDialog(String fish) {
        Context context = getContext();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String mainmsg = fish;
        String OKlstr = context.getString(R.string.OK);

        builder.setMessage(mainmsg).setNegativeButton(OKlstr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                alertDialog.dismiss();
            }
        });
        // Create the AlertDialog object and return it
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void refreshProfile() {
        loadingPB.setVisibility(View.VISIBLE);
        EB_Preference prefrence = new EB_Preference(getContext());
        User userp = prefrence.User();
        String uniq = android.provider.Settings.Secure.getString(getContext().getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        mAuthTask = new LoginUserTask(userp.getUsername(), userp.getPassword(), uniq, Settings.Urls.Login);
        mAuthTask.execute((Void) null);
    }

    public class LoginUserTask extends AsyncTask<Void, Void, Boolean> {
        private final String m_Phone, m_Code, m_Uniq, url;

        LoginUserTask(String phone, String code, String unig, String url) {
            m_Phone = phone;
            m_Code = code;
            m_Uniq = unig;
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                JSONObject js = new JSONObject();
                js.put(Settings.Jsons.Login.username, m_Phone);
                js.put(Settings.Jsons.Login.password, m_Code);
                js.put(Settings.Jsons.Login.uniq, m_Uniq);

                String response = Web.send(url, js.toString());
                if (response != null) {
                    return UF.Update_Login(response);
                } else {
                    return false;
                }
            } catch (Exception err) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            loadingPB.setVisibility(View.INVISIBLE);
            setValues();
            mAuthTask = null;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}
