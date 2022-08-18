package edu.ucu.cite.caravanrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.ucu.cite.caravanrental.Adapters.AllRentsAdapter;
import edu.ucu.cite.caravanrental.DataModels.AllRentsDataModel;

public class HomeNotLoggedIn extends AppCompatActivity {

    private ProgressDialog progressDialog;

    RecyclerView vehiclerecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private static final String URL_VEHICLES = "https://caravan-rental-cars.online/includes/displayvehicles.php"; //Endpoint

    List<Vehicles> vehiclesList;

    RecyclerView recyclerView;
    private static final String URL_RENTS = "https://caravan-rental-cars.online/includes/allrent.php";

    //a list to store all the drivers
    List<AllRentsDataModel> rentsList;

    Button signup, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_not_logged_in);

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,Mainscreen.class));
            return;
        }
        signup = findViewById(R.id.btnsignup);
        login = findViewById(R.id.btnLogin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeNotLoggedIn.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeNotLoggedIn.this, LoginActivity.class);
                startActivity(i);
            }
        });

        vehiclerecyclerView = findViewById(R.id.carList);
        vehiclerecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(HomeNotLoggedIn.this, LinearLayoutManager.HORIZONTAL, false);
        vehiclerecyclerView.setLayoutManager(linearLayoutManager);
        vehiclesList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        loadVehiclesnotAvailable();

        recyclerView = findViewById(R.id.rentlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeNotLoggedIn.this, LinearLayoutManager.HORIZONTAL, false));

        //initializing the productlist
        rentsList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadRents();
    }

    private void loadVehiclesnotAvailable() {
        progressDialog.show();
        vehiclesList.clear();
        vehiclerecyclerView.setAdapter(null);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_VEHICLES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            //converting the string to json array object
                            JSONArray vehicles = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i<vehicles.length(); i++) {

                                //getting product object from json array
                                JSONObject vehiclesJSONObject = vehicles.getJSONObject(i);

                                int vehiclesId = vehiclesJSONObject.getInt("vehiclesId");
                                String vehiclePhoto = vehiclesJSONObject.getString("vehiclesPhoto");
                                String transmission = vehiclesJSONObject.getString("transmission");
                                String yearModel = vehiclesJSONObject.getString("yearModel");
                                String seatCapacity = vehiclesJSONObject.getString("seatCapacity");
                                String manufacturedBy = vehiclesJSONObject.getString("manufacturedBy");
                                String vehiclesName = vehiclesJSONObject.getString("vehiclesName");
                                String vehiclesPlatenum = vehiclesJSONObject.getString("vehiclesPlatenum");
                                String vehicleColor = vehiclesJSONObject.getString("vehicleColor");
                                String regExpiry = vehiclesJSONObject.getString("registrationExpiry");
                                String regular_package = vehiclesJSONObject.getString("regular_package");
                                String complete_package = vehiclesJSONObject.getString("complete_package");
                                String vehicleStatus = vehiclesJSONObject.getString("vehicleStatus");


                                Vehicles vehicle = new Vehicles(vehiclesId,
                                        vehiclePhoto,
                                        transmission,
                                        vehiclesName,
                                        yearModel,
                                        seatCapacity,
                                        manufacturedBy,
                                        vehiclesPlatenum,
                                        vehicleColor,
                                        regExpiry,
                                        regular_package,
                                        complete_package,
                                        vehicleStatus);

                                if(vehicleStatus.equals("0")){
                                    vehiclesList.add(vehicle);
                                }


                            }

                            //creating adapter object and setting it to recyclerview
                            VehicleAdapterHome adapter = new VehicleAdapterHome(HomeNotLoggedIn.this, vehiclesList);
                            vehiclerecyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadRents() {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_RENTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            //converting the string to json array object
                            JSONArray rents = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i<rents.length(); i++) {

                                //getting product object from json array
                                JSONObject jo = rents.getJSONObject(i);

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
                                String vehicle_name = jo.getString("vehicle_name");


                                AllRentsDataModel rentsDataModel = new AllRentsDataModel(
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
                                        vehicle_photo,
                                        vehicle_name

                                );
                                    rentsList.add(rentsDataModel);
                            }

                            //creating adapter object and setting it to recyclerview
                            AllRentsAdapter adapter = new AllRentsAdapter(HomeNotLoggedIn.this, rentsList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}