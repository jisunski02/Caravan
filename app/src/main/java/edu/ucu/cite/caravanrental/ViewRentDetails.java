package edu.ucu.cite.caravanrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.ucu.cite.caravanrental.Adapters.PaymentAdapter;
import edu.ucu.cite.caravanrental.Adapters.VehiclePhotoAdapter;
import edu.ucu.cite.caravanrental.DataModels.PaymentModel;
import edu.ucu.cite.caravanrental.DataModels.VehiclePhotoModel;

public class ViewRentDetails extends AppCompatActivity {

    // ToolBar
    private Toolbar toolbar;
    private TextView activityTitle;

    private TextView pick_up_date, return_date, rent_package, rent_days, total_price, vehicle_name, payment_method, rent_status, transmission;
    private TextView year_model, seat_capacity, manufactured_by, plate_number, vehicle_color, registration_expiry, price_perday;
    private TextView driver_name, contact_no, birthdate, license_no, license_exp, total_exp, address, date_joining, tvReason;
    CardView cardViewCancelled;
    ImageView driver_photo;
    private String vehiclePhotoUrl = "https://caravan-rental-cars.000webhostapp.com/vehicles-photo/";
    private String driverPhotoUrl = "https://caravan-rental-cars.000webhostapp.com/drivers-photo/";
    String pickUpDate, returnDate, rentPackage, rentDays, totalPrice, vehicleName, paymentMethod, rentStatus, vehicleTransmission,
            yearModel, seatCapacity, manufacturedBy, plateNumber, vehicleColor, registrationExpiry, pricePerDay, driverName,
            contactNo, birthDate, licenseNo, licenseExp, totalExp, driverAddress, dateJoining, vehiclePhoto, driverPhoto, reason, package_amount;

    String item_price = "";
    double price;
    String convertedprice = "";


    String item_price_ = "";
    double price_;
    String convertedprice_ = "";

    RecyclerView vehicle_rent_photo;
    private LinearLayoutManager linearLayoutManager;
    private static final String URL_VEHICLES_PHOTO = "https://caravan-rental-cars.online/includes/displayvehiclePhoto.php";

    List<VehiclePhotoModel> vehiclesPhoto;

    RecyclerView recyclerPayment;
    private LinearLayoutManager linearLayoutManager2;
    List<PaymentModel> paymentModels;
    LinearLayout linearPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_rent_layout);

        //Toolbar Initialization..
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_activity));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewRentDetails.super.onBackPressed();
            }
        });

        linearPayment = findViewById(R.id.linearPayment);
        vehicle_rent_photo = findViewById(R.id.vehicle_rent_photo);
        vehicle_rent_photo.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        vehicle_rent_photo.setLayoutManager(linearLayoutManager);
        vehiclesPhoto = new ArrayList<>();

        recyclerPayment = findViewById(R.id.recyclerPayment);
        recyclerPayment.setHasFixedSize(true);
        linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerPayment.setLayoutManager(linearLayoutManager2);
        paymentModels = new ArrayList<>();

        getPayments();
        loadVehiclePhoto(Integer.parseInt(Constants.vehicle_id));

        activityTitle = findViewById(R.id.activity_title);

        pick_up_date = findViewById(R.id.pick_up_date);
        return_date = findViewById(R.id.return_date);
        rent_package = findViewById(R.id.packagedetails);
        rent_days = findViewById(R.id.rent_days);
        total_price = findViewById(R.id.total_price);
        vehicle_name = findViewById(R.id.vehicle_name);
        payment_method = findViewById(R.id.mode_of_payment);
        rent_status = findViewById(R.id.rent_status);
        transmission = findViewById(R.id.vehicle_transmission);
        year_model = findViewById(R.id.year_model);
        seat_capacity = findViewById(R.id.seat_capacity);
        manufactured_by = findViewById(R.id.manufactured_by);
        plate_number = findViewById(R.id.plate_number);
        vehicle_color = findViewById(R.id.vehicle_color);
        registration_expiry = findViewById(R.id.registration_expiry);
        price_perday = findViewById(R.id.price_per_day);
        driver_photo = findViewById(R.id.driver_photo);
        driver_name = findViewById(R.id.driver_name);
        contact_no = findViewById(R.id.contact_no);
        birthdate = findViewById(R.id.birth_date);
        license_no = findViewById(R.id.license_no);
        license_exp = findViewById(R.id.license_expiry);
        total_exp = findViewById(R.id.total_exp);
        address = findViewById(R.id.address);
        date_joining = findViewById(R.id.date_joining);

        cardViewCancelled = findViewById(R.id.cvCancelled);
        tvReason = findViewById(R.id.tvReason);

        //getting values from Rents Adapter
        package_amount = getIntent().getStringExtra("package_amount");
        pickUpDate = getIntent().getStringExtra("pick_up_date");
        returnDate = getIntent().getStringExtra("return_date");
        rentPackage = getIntent().getStringExtra("rent_package");
        rentDays = getIntent().getStringExtra("rent_days");
        totalPrice = getIntent().getStringExtra("total_price");
        vehicleName = getIntent().getStringExtra("vehicle_name");
        paymentMethod = getIntent().getStringExtra("mode_of_payment");
        rentStatus = getIntent().getStringExtra("rent_status");
        vehiclePhoto = getIntent().getStringExtra("vehicle_photo");
        vehicleTransmission = getIntent().getStringExtra("transmission");
        vehicleName = getIntent().getStringExtra("vehicle_name");
        yearModel = getIntent().getStringExtra("year_model");
        seatCapacity = getIntent().getStringExtra("seat_capacity");
        manufacturedBy = getIntent().getStringExtra("manufactured_by");
        plateNumber = getIntent().getStringExtra("plate_number");
        vehicleColor = getIntent().getStringExtra("vehicle_color");
        registrationExpiry = getIntent().getStringExtra("registration_expiry");
        pricePerDay = getIntent().getStringExtra("price");
        driverPhoto = getIntent().getStringExtra("driver_photo");
        driverName = getIntent().getStringExtra("driver_name");
        contactNo = getIntent().getStringExtra("contact_no");
        birthDate = getIntent().getStringExtra("birthdate");
        licenseNo = getIntent().getStringExtra("license_no");
        licenseExp = getIntent().getStringExtra("license_expiry");
        totalExp = getIntent().getStringExtra("total_exp");
        driverAddress = getIntent().getStringExtra("address");
        dateJoining = getIntent().getStringExtra("date_joining");
        reason = getIntent().getStringExtra("reason");

        Glide.with(driver_photo).load(driverPhotoUrl+driverPhoto)
                .thumbnail(0.5f)
                .error(R.drawable.ic_account_box)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(driver_photo);

        pick_up_date.setText(pickUpDate);
        return_date.setText(returnDate);
        rent_package.setText(rentPackage);

       if(rentDays.equals("1")){
            rent_days.setText(rentDays+" day");
        }

        else{
            rent_days.setText(rentDays+" days");
        }

        item_price = totalPrice;
        convertedprice = item_price.replaceAll("[^\\d.]", "");

        price = Double.parseDouble(convertedprice);

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(price);

        //price per day
        item_price_ = package_amount;
        convertedprice_ = item_price_.replaceAll("[^\\d.]", "");

        price_ = Double.parseDouble(convertedprice_);

        DecimalFormat formatter_ = new DecimalFormat("#,###,###");
        String formattedPrice_ = formatter_.format(price_);

        total_price.setText("₱"+formattedPrice+".00");
        vehicle_name.setText(vehicleName);
        payment_method.setText(paymentMethod);

        if(rentStatus.equals("0")){
            cardViewCancelled.setVisibility(View.GONE);
            rent_status.setText("Pending");
            activityTitle.setText("Pending Rent Overview");
        }

        if(rentStatus.equals("1")){
            linearPayment.setVisibility(View.VISIBLE);
            cardViewCancelled.setVisibility(View.GONE);
            rent_status.setText("Approved");
            activityTitle.setText("Approved Rent Overview");
        }

        if(rentStatus.equals("2")){
            if(reason.isEmpty()){
                tvReason.setText("No Reason added");
            }

            else{
                tvReason.setText(reason);
            }
            rent_status.setText("Cancelled");
            activityTitle.setText("Cancelled Rent Overview");
        }

        if(rentStatus.equals("3")){
            cardViewCancelled.setVisibility(View.GONE);
            rent_status.setText("Completed");
            activityTitle.setText("Completed Rent Overview");
        }
        transmission.setText(vehicleTransmission);
        vehicle_name.setText(vehicleName);
        year_model.setText(yearModel);
        seat_capacity.setText(seatCapacity+" persons");
        manufactured_by.setText(manufacturedBy);
        plate_number.setText(plateNumber);
        vehicle_color.setText(vehicleColor);
        registration_expiry.setText(registrationExpiry);
        price_perday.setText("₱"+formattedPrice_+".00");

        if(rentPackage.toLowerCase().trim().equals("regular package")){
            driver_name.setText("Not Available");
            contact_no.setText("Not Available");
            birthdate.setText("Not Available");
            license_no.setText("Not Available");
            license_exp.setText("Not Available");
            total_exp.setText("Not Available");
            address.setText("Not Available");
            date_joining.setText("Not Available");
        }
        else {
            driver_name.setText(driverName);
            contact_no.setText(contactNo);
            birthdate.setText(birthDate);
            license_no.setText(licenseNo);
            license_exp.setText(licenseExp);
            total_exp.setText(totalExp);
            address.setText(driverAddress);
            date_joining.setText(dateJoining);
        }


    }

    private void loadVehiclePhoto(int vehicle_photo_id) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_VEHICLES_PHOTO,
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

                                String id = vehiclesJSONObject.getString("id");
                                int vehicle_id = vehiclesJSONObject.getInt("vehicle_id");
                                String vehicle_name = vehiclesJSONObject.getString("vehicle_name");
                                String created_at = vehiclesJSONObject.getString("created_at");


                                VehiclePhotoModel vehicle = new VehiclePhotoModel(id,
                                        vehicle_id,
                                        vehicle_name,
                                        created_at);

                                if(vehicle_photo_id == vehicle_id){
                                    vehiclesPhoto.add(vehicle);
                                }


                            }

                            //creating adapter object and setting it to recyclerview
                            VehiclePhotoAdapter adapter = new VehiclePhotoAdapter(ViewRentDetails.this, vehiclesPhoto);
                            vehicle_rent_photo.setAdapter(adapter);

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

    private void getPayments() {
        //kunin yung id ng naka-login
        int Temp_Customer_ID = SharedPrefManager.getInstance(this).getId();
        String Customer_IDx = new Integer(Temp_Customer_ID).toString();

        String url = Constants.MAIN_URL+ "uploadedpayment.php?vehicle_id="+Constants.vehicle_id+"&customer_id=" + Customer_IDx;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("tbl_payment");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);

                String id = jo.getString("id");
                String transaction_no = jo.getString("transaction_no");
                String payment_type = jo.getString("payment_type");
                String amount = jo.getString("amount");
                String confirmation_date = jo.getString("confirmation_date");
                String created_at = jo.getString("created_at");


                PaymentModel paymentModel = new PaymentModel(
                        id, transaction_no,
                        payment_type, amount,
                        confirmation_date, created_at

                );

                paymentModels.add(paymentModel);


            }

            PaymentAdapter paymentAdapter = new PaymentAdapter(this, paymentModels);
            recyclerPayment.setAdapter(null);
            recyclerPayment.setAdapter(paymentAdapter);

            /*if(paymentAdapter.getItemCount()==0){
                noReqPhoto.setVisibility(View.VISIBLE);
            }
            else {
                noReqPhoto.setVisibility(View.GONE);
            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}