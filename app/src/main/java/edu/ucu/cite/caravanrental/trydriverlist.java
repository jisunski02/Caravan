package edu.ucu.cite.caravanrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class trydriverlist extends AppCompatActivity {

    private ProgressDialog progressDialog;
    //TextView displaypackage,displayLocation,displayPickdate,displayPickhour,displayReturndate,displayReturnhour,displayRentDays;

    RecyclerView recyclerView;
    private static final String URL_DRIVERS = Constants.MAIN_URL+"displaydrivers.php";

    //a list to store all the drivers
    List<Drivers> driversList;

    // ToolBar
    private Toolbar toolbar;
    private TextView activityTitle;


    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trydriverlist);

        //Toolbar Initialization..
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_activity));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trydriverlist.super.onBackPressed();
            }
        });

        activityTitle = findViewById(R.id.activity_title);
        activityTitle.setText("Drivers List");

        recyclerView = findViewById(R.id.recycleView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading drivers...");
        //MyAdapter = Drivers adapter
        //MyAdapter myAdapter = new MyAdapter(trydriverlist.this,driversList );
        //recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        driversList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadDrivers( );

       /* displayLocation=findViewById(R.id.idLocation);
        displayPickdate=findViewById(R.id.idPickDate);
        displayPickhour=findViewById(R.id.idPickhour);
        displayReturndate=findViewById(R.id.idReturnDate);
        displayReturnhour=findViewById(R.id.idReturnHour);
        displayRentDays=findViewById(R.id.idRentdays);
        displaypackage=findViewById(R.id.idPackage);*/

        String Location = getIntent().getStringExtra("nextlocation");
        String Pickupdate = getIntent().getStringExtra("nextdate");
        String Pickuphour = getIntent().getStringExtra("nextpickuphour");
        String Returndate = getIntent().getStringExtra("nextreturndate");
        String Returnhour = getIntent().getStringExtra("nextreturntime");
        Long Rentdays = getIntent().getLongExtra("nextRentdays",0);
        String Package = getIntent().getStringExtra("nextPackage");

        //GlobalVariables.F_Location = Location;

        //setText
     /*   displayLocation.setText(Location);
        displayPickdate.setText(Pickupdate);
        displayPickhour.setText(Pickuphour);
        displayReturndate.setText(Returndate);
        displayReturnhour.setText(Returnhour);
        displayRentDays.setText(String.valueOf(Rentdays));
        displaypackage.setText(Package);*/
    }



    private void loadDrivers() {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DRIVERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            //converting the string to json array object
                            JSONArray drivers = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i<drivers.length(); i++) {

                                //getting product object from json array
                                JSONObject driversObject = drivers.getJSONObject(i);

                                String driversID = driversObject.getString("Number");
                                String driversName = driversObject.getString("Name");
                                String driversAddress = driversObject.getString("Address");
                                String driversMobile = driversObject.getString("Mobile");
                                String driversPhoto = driversObject.getString("drivers_photo");
                                String driversExp = driversObject.getString("drivers_exp");
                                String driversLicense = driversObject.getString("drivers_license");

                                Drivers driver = new Drivers(driversID,driversName,driversAddress,driversMobile, driversPhoto, driversExp, driversLicense);
                                driversList.add(driver);

                                //adding the product to product list
                                /*driversList.add(new Drivers(
                                        product.getString("driver_id"),
                                        product.getString("driver_name"),
                                        product.getString("driver_address"),
                                        product.getString("driver_mobile")
                                ));*/
                            }

                            //creating adapter object and setting it to recyclerview
                            DriversAdapter adapter = new DriversAdapter(trydriverlist.this, driversList);
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