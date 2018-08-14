package com.sai.udstore.Main.Fragment;


import android.content.Intent;
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

import com.sai.udstore.DataBase.Models.Sefaresh_fact;
import com.sai.udstore.Main.Adapter.FactorsRecyclerViewAdapater;
import com.sai.udstore.Main.App;
import com.sai.udstore.Main.MainActivity;
import com.sai.udstore.Main.Order.InvoiceDetailsActivity;
import com.sai.udstore.Prefrence.Daos.User;
import com.sai.udstore.Prefrence.EB_Preference;
import com.sai.udstore.R;
import com.sai.udstore.Settings;
import com.sai.udstore.excomponents.Web;
import com.sai.udstore.sai.UF;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoicesHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoicesHistoryFragment extends Fragment implements FactorsRecyclerViewAdapater.IInvoiceCaller {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    ArrayList<Sefaresh_fact> sefaresh_facts;
    FactorsRecyclerViewAdapater ordersRecyclerViewAdapater;
    RecyclerView ordersRV;
    ProgressBar pb;
    LinearLayout llpb;
    TextView pbErr;
    Button pbRetry;

    public InvoicesHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoicesHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoicesHistoryFragment newInstance(String param1, String param2) {
        InvoicesHistoryFragment fragment = new InvoicesHistoryFragment();
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
        ((MainActivity) getActivity()).curFragment = Settings.Fragments.Invoices;
        ((MainActivity) getActivity()).setMainTitle();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invoices_history, container, false);
        pb = view.findViewById(R.id.ao_pb_main);
        llpb = view.findViewById(R.id.ao_ll_pb);
        pbErr = view.findViewById(R.id.ao_tv_err);
        pbRetry = view.findViewById(R.id.ao_bt_retry);
        sefaresh_facts = new ArrayList<>();
        ordersRecyclerViewAdapater = new FactorsRecyclerViewAdapater(sefaresh_facts, getContext(), this);
        ordersRV = view.findViewById(R.id.ao_rv_main);
        ordersRV.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersRV.setAdapter(ordersRecyclerViewAdapater);
        LinearLayout mmll = view.findViewById(R.id.mmll);
        UF.setAllFonts(App.appFont, mmll);
        populateOrders();
//        setTopActionBar();

        return view;
    }

    public void doRefresh(View v) {
        populateOrders();
    }

    private void populateOrders() {
        pb.setVisibility(View.VISIBLE);
        llpb.setVisibility(View.VISIBLE);
        pbErr.setVisibility(View.GONE);
        pbRetry.setVisibility(View.GONE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject order = new JSONObject();
                    EB_Preference preference = new EB_Preference(getContext());
                    User userP = preference.User();
                    order.put("username", userP.getUsername());
                    String result = Web.send(Settings.Urls.GetOrders, order.toString());
//                    String result = Web.send("http://ws.atitravel.ir/ATIOperation.svc/api/package/ListPackage", "");

                    JSONObject json = new JSONObject(result);
                    int err = json.getInt("ErrorCode");
                    if (err == 0) {
                        JSONArray jsonArray = json.getJSONArray("Result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject sef = jsonArray.getJSONObject(i);
                            Sefaresh_fact sefareshFact = Sefaresh_fact.parseFactor(sef);
                            sefaresh_facts.add(sefareshFact);

                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ordersRecyclerViewAdapater.setList(sefaresh_facts);
                                llpb.setVisibility(View.GONE);
                            }
                        });
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pb.setVisibility(View.GONE);
                                pbErr.setVisibility(View.VISIBLE);
                                pbErr.setText("عدم وجود سفارش");
                                pbRetry.setVisibility(View.VISIBLE);

                            }
                        });
                    }
                } catch (Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ordersRecyclerViewAdapater.setList(sefaresh_facts);
                            pbErr.setVisibility(View.VISIBLE);
                            pb.setVisibility(View.GONE);
                            pbRetry.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }
        }).start();
    }

    @Override
    public void onOrderClicked(int pos) {
        Intent intent = new Intent(getActivity(), InvoiceDetailsActivity.class);
        intent.putExtra("orderID", sefaresh_facts.get(pos).getId());
        getActivity().startActivity(intent);
    }
}
