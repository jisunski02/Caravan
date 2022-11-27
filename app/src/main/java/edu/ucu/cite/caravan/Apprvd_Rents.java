package edu.ucu.cite.caravan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.ucu.cite.caravan.Adapters.CompletePackageRentsAdapter;
import edu.ucu.cite.caravan.DataModels.CompletePackageRentsDataModel;

public class Apprvd_Rents extends AppCompatActivity {


    private ProgressDialog progressDialog;
    RecyclerView apprvdrecyclerView;
    List<CompletePackageRentsDataModel> rentList;
    LinearLayout noapprovedrents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apprvd__rents);


        apprvdrecyclerView = findViewById(R.id.RentsRecyclerView);
        apprvdrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rentList = new ArrayList<>();
        noapprovedrents = findViewById(R.id.noapproverents);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Approved. . .");

        getApprovedRentData();
    }

    private void getApprovedRentData() {
        progressDialog.show();

        //kunin yung id ng naka-login
        int Temp_Customer_ID = SharedPrefManager.getInstance(this).getId();
        String Customer_IDx = new Integer(Temp_Customer_ID).toString();


        String url = "https://caravan-rental-cars.000webhostapp.com/includes/rents.php?customer_id=" + Customer_IDx;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Apprvd_Rents.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("tbl_rents");

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
                String created_at = jo.getString("created_at");

               /* RentsDataModel rentsDataModel = new RentsDataModel(
                        customer_id,
                        driver_id,
                        vehicle_id,
                        total_price,
                        rent_package,
                        pick_up_date,
                        return_date,
                        location,
                        mode_of_payment,
                        rent_status,
                        rent_days,
                        created_at
                );

                if(rent_status.equals("0")){
                    rentList.add(rentsDataModel);
                } */





            }
            CompletePackageRentsAdapter rentsAdapter = new CompletePackageRentsAdapter(Apprvd_Rents.this, rentList);
            apprvdrecyclerView.setAdapter(rentsAdapter);

            if(rentsAdapter.getItemCount()==0){
                    noapprovedrents.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}