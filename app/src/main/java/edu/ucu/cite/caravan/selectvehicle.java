package edu.ucu.cite.caravan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

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

    TabLayout tabStatus;

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

        vehiclerecyclerView = findViewById(R.id.VehicleRecyclerView);
        vehiclerecyclerView.setHasFixedSize(true);
        linearLayoutManager = new GridLayoutManager(this, 2);
        vehiclerecyclerView.setLayoutManager(linearLayoutManager);
         vehiclesList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading vehicles. . .");

        tabStatus = findViewById(R.id.tabStatus);

        loadVehiclesnotAvailable();

        tabStatus.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        break;
                    case 1:

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


        //Testing kung naipapasa yung data
        displayLocation=findViewById(R.id.idLocation);
        displayPickdate=findViewById(R.id.idPickDate);
        displayPickhour=findViewById(R.id.idPickhour);
        displayReturndate=findViewById(R.id.idReturnDate);
        displayReturnhour=findViewById(R.id.idReturnHour);
        //displayRentDays=findViewById(R.id.idRentdays);

        //displayReturnhour.setText(Returnhour);
        //displayRentDays.setText(String.valueOf(Rentdays));

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


    /*    String SplitPickupDate = Pickupdate.trim();
        String pickupDateArray[] = SplitPickupDate.split("-");
        int PickYear = Integer.parseInt(pickupDateArray[2]);
        int PickMonth = Integer.parseInt(pickupDateArray[1]);
        int PickDay = Integer.parseInt(pickupDateArray[0]);

        if (PickMonth==1){
            TextPickupMonth = "Jan.";

        }else if (PickMonth==2){
            TextPickupMonth = "Feb.";

        }else if (PickMonth==3){
            TextPickupMonth = "March";

        }else if (PickMonth==4){
            TextPickupMonth = "April";

        }else if (PickMonth==5){
            TextPickupMonth = "May";

        }else if (PickMonth==6){
            TextPickupMonth = "June";

        }else if (PickMonth==7){
            TextPickupMonth = "July";

        }else if (PickMonth==8){
            TextPickupMonth = "Aug.";

        }else if (PickMonth==9){
            TextPickupMonth = "Sept.";

        }
        else if (PickMonth==10){
            TextPickupMonth = "Oct.";

        }else if (PickMonth==11){
            TextPickupMonth = "Nov.";

        }
        else if (PickMonth==12){
            TextPickupMonth = "Dec.";

        }else {
            TextPickupMonth = "HAKDOG";
        }

     */

        /*
        String SplitReturnDate = Returndate.trim();
        String returnDateArray[] = SplitReturnDate.split("-");
        int ReturnYear = Integer.parseInt(returnDateArray[2]);
        int ReturnMonth = Integer.parseInt(returnDateArray[1]);
        int ReturnDay = Integer.parseInt(returnDateArray[0]);

        if (ReturnMonth==1){
            TextReturnMonth = "Jan.";

        }else if (ReturnMonth==2){
            TextReturnMonth = "Feb.";

        }else if (ReturnMonth==3){
            TextReturnMonth = "March";

        }else if (ReturnMonth==4){
            TextReturnMonth = "April";

        }else if (ReturnMonth==5){
            TextReturnMonth = "May";

        }else if (ReturnMonth==6){
            TextReturnMonth = "June";

        }else if (ReturnMonth==7){
            TextReturnMonth = "July";

        }else if (ReturnMonth==8){
            TextReturnMonth = "Aug.";

        }else if (ReturnMonth==9){
            TextReturnMonth = "Sept.";

        }
        else if (ReturnMonth==10){
            TextReturnMonth = "Oct.";

        }else if (ReturnMonth==11){
            TextReturnMonth = "Nov.";

        }
        else if (ReturnMonth==12){
            TextReturnMonth = "Dec.";

        }else {
            TextReturnMonth = "HAKDOG";
        }

        //GlobalVariables.DisplayPickupDate = (TextPickupMonth+" "+PickDay+", "+PickYear);

        //To Determine the pickup time if it's am or pm
        String SplitPickupTime = Pickuphour.trim();
        String pickupHourArray[] = SplitPickupTime.split(":");
        int Pickup_hour = Integer.parseInt(pickupHourArray[0]);
        int Pickup_min = Integer.parseInt(pickupHourArray[1]);

        if(Pickup_hour>=12){
            GlobalVariables.DisplayPickupTime = ("0"+(Pickup_hour-12)+":"+Pickup_min+" pm");
            displayPickhour.setText(GlobalVariables.DisplayPickupTime);
        }else{
            GlobalVariables.DisplayPickupTime = ("0"+Pickup_hour+":"+Pickup_min+" am");
            displayPickhour.setText(GlobalVariables.DisplayPickupTime);
        }


        //FOR RETURN DATE AND TIME
        GlobalVariables.DisplayReturnDate = (TextReturnMonth+" "+ReturnDay+", "+ReturnYear);


        String SplitReturnTime = Returnhour.trim();
        String returnHourArray[] = SplitReturnTime.split(":");
        int Return_hour = Integer.parseInt(returnHourArray[0]);
        int Return_min = Integer.parseInt(returnHourArray[1]);

        if(Return_hour>=12){
            GlobalVariables.DisplayReturnTime = ("0"+(Return_hour-12)+":"+Return_min+" pm");
            displayReturnhour.setText(GlobalVariables.DisplayReturnTime);
        }else{
            GlobalVariables.DisplayReturnTime = ("0"+Return_hour+":"+Return_min+" am");
            displayReturnhour.setText(GlobalVariables.DisplayReturnTime);
        } */

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


