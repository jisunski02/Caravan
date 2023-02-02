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
import android.widget.Toast;

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

import edu.ucu.cite.caravanrental.DataModels.DateRangeModel;
import edu.ucu.cite.caravanrental.DataModels.RescodeDataModel;

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

    private static final String URL_RESCODE = Constants.MAIN_URL+"displaydrivers2.php";

    List<RescodeDataModel> rescodeDataModels;

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

        rescodeDataModels = new ArrayList<>();
        loadRescode();
        //initializing the productlist
        driversList = new ArrayList<>();


        //this method will fetch and parse json
        //to display it in recyclerview
        loadDrivers( );

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
                                String driversFirstName = driversObject.getString("First");
                                String driversLastName = driversObject.getString("Last");
                                String driversAddress = driversObject.getString("Address");
                                String driversMobile = driversObject.getString("Mobile");
                                String driversPhoto = driversObject.getString("drivers_photo");
                                String driversExp = driversObject.getString("drivers_exp");
                                String driversLicense = driversObject.getString("drivers_license");

                                Drivers driver = new Drivers(driversID,driversFirstName,driversLastName, driversAddress,driversMobile, driversPhoto, driversExp, driversLicense);

                                for (int x=0; x <rescodeDataModels.size(); x++) {
                                    RescodeDataModel model = rescodeDataModels.get(x);
                                    if (model.getDriver_id().equals(driversID) && Constants.rescodes.contains(model.getRescode())) {
                                        boolean isExist = isExist(driver.getDrivers_id());
                                        if(!isExist) {
                                            driversList.add(driver);
                                        }

                                    }

                                }


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


    public boolean isExist(String strID) {

        for (int i = 0; i < driversList.size(); i++) {
            Drivers model = driversList.get(i);
            if (model.getDrivers_id().equals(strID)) {
                return true;
            }
        }

        return false;
    }

    private void loadRescode() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_RESCODE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray vehicles = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i<vehicles.length(); i++) {

                                //getting product object from json array
                                JSONObject vehiclesJSONObject = vehicles.getJSONObject(i);

                                String driverID = vehiclesJSONObject.getString("driverID");
                                String driverRes = vehiclesJSONObject.getString("driverRes");

                                if(driverRes.equals("1")){
                                    driverRes = "one";
                                }
                                if(Constants.rescodes.equals("BB1B2") && driverRes.equals("2")){
                                    driverRes = "one";
                                }

                                RescodeDataModel res = new RescodeDataModel(driverID, driverRes);

                                    rescodeDataModels.add(res);




                            }


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