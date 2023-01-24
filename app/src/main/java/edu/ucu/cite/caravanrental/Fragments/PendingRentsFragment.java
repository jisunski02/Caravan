package edu.ucu.cite.caravanrental.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.ucu.cite.caravanrental.Adapters.UserRentsAdapter;
import edu.ucu.cite.caravanrental.Constants;
import edu.ucu.cite.caravanrental.DataModels.RegularPackageRentsDataModel;
import edu.ucu.cite.caravanrental.DataModels.UserRentsDataModel;
import edu.ucu.cite.caravanrental.R;
import edu.ucu.cite.caravanrental.SharedPrefManager;


public class PendingRentsFragment extends Fragment {

    private ProgressBar progressBar;
    RecyclerView pndngrecyclerView;
    List<UserRentsDataModel> rentList;
    List<RegularPackageRentsDataModel> rentListRegularPackage;
    LinearLayout nopendingrents;

    TabLayout tabPackages;
    TabItem completePackage, regularPackage;
    Button test;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pending_rents, container, false);

        pndngrecyclerView = rootView.findViewById(R.id.RentsRecyclerView);
        progressBar = rootView.findViewById(R.id.progressbarLoading);
        pndngrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rentList = new ArrayList<UserRentsDataModel>();
        rentListRegularPackage = new ArrayList<>();
        nopendingrents = rootView.findViewById(R.id.nopendingrents);

        tabPackages = rootView.findViewById(R.id.tab_packages);


        tabPackages.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        //getPendingRentData();
                        break;
                    case 1:
                        //getPendingRentDataNoDriver();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //progressDialog = new ProgressDialog(getActivity());
        //progressDialog.setMessage("Loading Pending Rents. . .");

        getPendingRentData();

        //getPendingRentDataNoDriver();

        return rootView;
    }

    private void getPendingRentData() {
        progressBar.setVisibility(View.VISIBLE);
        rentList.clear();
        pndngrecyclerView.setAdapter(null);
        nopendingrents.setVisibility(View.GONE);
        //kunin yung id ng naka-login
        int Temp_Customer_ID = SharedPrefManager.getInstance(getActivity()).getId();
        String Customer_IDx = new Integer(Temp_Customer_ID).toString();

        String url = Constants.MAIN_URL+ "userrentswithcardetails.php?customer_id=" + Customer_IDx;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                rentList.clear();
                progressBar.setVisibility(View.GONE);

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void getPendingRentDataNoDriver() {
        //pndngrecyclerView.setAdapter(null);
        rentListRegularPackage.clear();
        pndngrecyclerView.setAdapter(null);
        progressBar.setVisibility(View.VISIBLE);
        nopendingrents.setVisibility(View.GONE);
        //kunin yung id ng naka-login
        int Temp_Customer_ID = SharedPrefManager.getInstance(getActivity()).getId();
        String Customer_IDx = new Integer(Temp_Customer_ID).toString();

        String url = "https://caravan-rental-cars.000webhostapp.com/includes/userrentswithcardetails.php?customer_id=" + Customer_IDx;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);

                //showNoDriverJson(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("tbl_rent");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString("id");
                String booking_number = jo.getString("booking_number");
                String invoice_number = jo.getString("invoice_number");
                String transaction_no = jo.getString("transaction_no");
                String customer_id = jo.getString("customer_id");
                String driver_id = jo.getString("driver_id");
                String vehicle_id = jo.getString("vehicle_id");
                String package_amount = jo.getString("package_amount");
                String rent_days = jo.getString("rent_days");
                String total_amount = jo.getString("total_amount");
                String rent_package = jo.getString("package_type");
                String booking_date = jo.getString("booking_date");
                String pick_up_date = jo.getString("pick_up_date");
                String return_date = jo.getString("return_date");
                String approved_date = jo.getString("approved_date");
                String location = jo.getString("location");
                String mode_of_payment = jo.getString("mode_of_payment");
                String rent_status = jo.getString("rent_status");
                String reason = jo.getString("reason");
                String cancelledBy = jo.getString("cancelledBy");
                String vehicle_photo = jo.getString("vehicle_photo");
                String vehicle_transmission = jo.getString("vehicle_transmission");
                String vehicle_name = jo.getString("vehicle_name");
                String year_model = jo.getString("year_model");
                String seat_capacity = jo.getString("seat_capacity");
                String manufactured_by = jo.getString("manufactured_by");
                String plate_number = jo.getString("plate_number");
                String vehicle_color = jo.getString("vehicle_color");
                String registration_expiry = jo.getString("registration_expiry");
                String complete_package = jo.getString("complete_package");
                String regular_package = jo.getString("regular_package");
                String vehicle_status = jo.getString("vehicle_status");

                UserRentsDataModel rentsDataModel = new UserRentsDataModel(
                        id,
                        booking_number, invoice_number,
                        transaction_no, customer_id,
                        driver_id, vehicle_id,
                        package_amount, rent_days,
                        total_amount, rent_package,
                        booking_date, pick_up_date,
                        return_date, approved_date,
                        location, mode_of_payment,
                        rent_status, reason, cancelledBy,
                        vehicle_photo, vehicle_transmission,
                        vehicle_name, year_model,
                        seat_capacity, manufactured_by,
                        plate_number, vehicle_color,
                        registration_expiry, complete_package,
                        regular_package, vehicle_status

                );
                if((rent_status.equals("0"))){
                    rentList.add(rentsDataModel);
                }

            }

            UserRentsAdapter rentsAdapter = new UserRentsAdapter(getActivity(), rentList);
            pndngrecyclerView.setAdapter(null);
            pndngrecyclerView.setAdapter(rentsAdapter);

            if(rentsAdapter.getItemCount()==0){
                nopendingrents.setVisibility(View.VISIBLE);
            }
            else {
                nopendingrents.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

   /* private void showNoDriverJson(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("tbl_rent");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);

                String customer_id = jo.getString("customer_id");
                String driver_id = jo.getString("driver_id");
                String vehicle_id = jo.getString("vehicle_id");
                String total_price = jo.getString("total_price");
                String rent_package = jo.getString("package");
                String pick_up_date = jo.getString("pick_up_date");
                String return_date = jo.getString("return_date");
                String location = jo.getString("location");
                String mode_of_payment = jo.getString("mode_of_payment");
                String rent_status = jo.getString("rent_status");
                String rent_days = jo.getString("rent_days");
                String approved_date = jo.getString("approved_date");
                String reason = jo.getString("reason");
                String created_at = jo.getString("created_at");
                String vehicle_photo = jo.getString("vehicle_photo");
                String vehicle_transmission = jo.getString("vehicle_transmission");
                String vehicle_name = jo.getString("vehicle_name");
                String year_model = jo.getString("year_model");
                String seat_capacity = jo.getString("seat_capacity");
                String manufactured_by = jo.getString("manufactured_by");
                String plate_number = jo.getString("plate_number");
                String vehicle_color = jo.getString("vehicle_color");
                String registration_expiry = jo.getString("registration_expiry");
                String price = jo.getString("price");
                String vehicle_status = jo.getString("vehicle_status");

                RegularPackageRentsDataModel regularPackageRentsDataModel = new RegularPackageRentsDataModel(
                        customer_id, driver_id,
                        vehicle_id, total_price,
                        rent_package, pick_up_date,
                        return_date, location,
                        mode_of_payment, rent_status,
                        rent_days, approved_date,
                        reason, created_at,
                        vehicle_photo, vehicle_transmission,
                        vehicle_name, year_model,
                        seat_capacity, manufactured_by,
                        plate_number, vehicle_color,
                        registration_expiry, price,
                        vehicle_status

                );
                if((rent_status.equals("0"))){
                    rentListRegularPackage.add(regularPackageRentsDataModel);
                }

            }

            RegularPackageRentsAdapter regularPackageRentsAdapter = new RegularPackageRentsAdapter(getActivity(), rentListRegularPackage);
            pndngrecyclerView.setAdapter(null);
            pndngrecyclerView.setAdapter(regularPackageRentsAdapter);

            if(regularPackageRentsAdapter.getItemCount()==0){
                nopendingrents.setVisibility(View.VISIBLE);
            }
            else {
                nopendingrents.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    } */
}