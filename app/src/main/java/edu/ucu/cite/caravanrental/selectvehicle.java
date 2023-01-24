package edu.ucu.cite.caravanrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class selectvehicle extends AppCompatActivity {

    private ProgressDialog progressDialog;

    RecyclerView vehiclerecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private static final String URL_VEHICLES = Constants.MAIN_URL+"displayvehicles.php";

    List<Vehicles> vehiclesList;


    TextView displayLocation,displayPickdate,displayPickhour,displayReturndate,displayReturnhour,displayRentDays,displayPackage;
    String Package;

    String Drivers_Name, Drivers_ID, Drivers_Address, Drivers_Mobile;

    String Location,Pickupdate,Pickuphour,Returndate,Returnhour;
    Long Rentdays;
    String TextReturnMonth,TextPickupMonth;

    // ToolBar
    private Toolbar toolbar;
    private TextView activityTitle;

    Button all, SUV, VAN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectvehicle);

        //Toolbar Initialization..
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_activity));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectvehicle.super.onBackPressed();
            }
        });

        activityTitle = findViewById(R.id.activity_title);
        activityTitle.setText("Car List");

        all = findViewById(R.id.all);
        SUV = findViewById(R.id.SUV);
        VAN = findViewById(R.id.VAN);
        vehiclerecyclerView = findViewById(R.id.VehicleRecyclerView);
        vehiclerecyclerView.setHasFixedSize(true);
        linearLayoutManager = new GridLayoutManager(this, 2);
        vehiclerecyclerView.setLayoutManager(linearLayoutManager);
         vehiclesList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading vehicles. . .");

        loadVehiclesnotAvailable();

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadVehiclesnotAvailable();

            }
        });

        SUV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSUV();
            }
        });

        VAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               loadVan();
            }
        });

        //Testing kung naipapasa yung data
        displayLocation=findViewById(R.id.idLocation);
        displayPickdate=findViewById(R.id.idPickDate);
        displayPickhour=findViewById(R.id.idPickhour);
        displayReturndate=findViewById(R.id.idReturnDate);
        displayReturnhour=findViewById(R.id.idReturnHour);

        Location = getIntent().getStringExtra("nextlocation");
        Pickupdate = getIntent().getStringExtra("nextdate");
        Pickuphour = getIntent().getStringExtra("displaypickuptime");
        Returndate = getIntent().getStringExtra("nextreturndate");
        Returnhour = getIntent().getStringExtra("displayreturntime");
        Rentdays = getIntent().getLongExtra("nextRentdays",0);

        displayLocation.setText(GlobalVariables.F_Location);
        displayPickdate.setText(GlobalVariables.DisplayPickupDate);
        displayReturndate.setText(GlobalVariables.DisplayReturnDate);
        displayPickhour.setText(Pickuphour);
        displayReturnhour.setText(Returnhour);


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
                                String vehicle_category = vehiclesJSONObject.getString("vehicle_category");
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


                                Vehicles vehicle = new Vehicles(vehiclesId, vehicle_category,
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
                            VehicleAdapter adapter = new VehicleAdapter(selectvehicle.this, vehiclesList);
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

    private void loadVan() {
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
                                String vehicle_category = vehiclesJSONObject.getString("vehicle_category");
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


                                Vehicles vehicle = new Vehicles(vehiclesId, vehicle_category,
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

                                if(vehicleStatus.equals("0") && vehicle_category.equals("VAN")){
                                    vehiclesList.add(vehicle);
                                }


                            }

                            //creating adapter object and setting it to recyclerview
                            VehicleAdapter adapter = new VehicleAdapter(selectvehicle.this, vehiclesList);
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

    private void loadSUV() {
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
                                String vehicle_category = vehiclesJSONObject.getString("vehicle_category");
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


                                Vehicles vehicle = new Vehicles(vehiclesId, vehicle_category,
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

                                if(vehicleStatus.equals("0") && vehicle_category.equals("SUV")){
                                    vehiclesList.add(vehicle);
                                }


                            }

                            //creating adapter object and setting it to recyclerview
                            VehicleAdapter adapter = new VehicleAdapter(selectvehicle.this, vehiclesList);
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

    }


