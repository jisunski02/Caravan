package edu.ucu.cite.caravanrental;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.ucu.cite.caravanrental.Adapters.RequirementsPhotoAdapter;
import edu.ucu.cite.caravanrental.DataModels.DateRangeModel;
import edu.ucu.cite.caravanrental.DataModels.RequirementsPhotoModel;

public class review extends AppCompatActivity implements View.OnClickListener {

    String Driver_id, Driver_name, Driver_mobile, Driver_address;
    int Vehicle_id, Vehicle_capacity, Vehicle_price;
    int Total_price;
    String Vehicle_name, Vehicle_platenumber, Vehicle_transmission;
    String ModeOfPayment, Ref_Num;

    //TextView for Vehicle's info
    TextView TV_vehicles_name, TV_Vehicles_platenumber, TV_Vehicles_capacity, TV_Vehicles_transmission;
    //TextView for Driver's info
    TextView TV_driversName, TV_Drivers_mobile, TV_Drivers_address;
    //TextView for Location and Date
    TextView TV_location, TV_DateAndTime, TV_package, TV_TotalPrice;

    Button btn_confirmReservation;
    ProgressDialog progressDialog;

    // ToolBar
    private Toolbar toolbar;
    private TextView activityTitle;

    String item_price = "";
    double price;
    String convertedprice = "";

    Dialog verifynowDialog;
    Button verifyNow;
    ImageView close;

    String status = SharedPrefManager.getInstance(this).getStatus();
    String Phonenumber = SharedPrefManager.getInstance(review.this).getPhonenumber();

    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    TextView noOfDays;

    List<DateRangeModel> checkDateList;
    ScrollView scrollView;
    LinearLayout linearLayout;
    List<RequirementsPhotoModel> requirementsPhotoModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewv2);

        //Toolbar Initialization..
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_activity));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review.super.onBackPressed();
            }
        });
        requirementsPhotoModels = new ArrayList<>();
        getReqList();
        checkDateList = new ArrayList<>();
        getDateList();

        activityTitle = findViewById(R.id.activity_title);
        activityTitle.setText("Caravan");
        linearLayout = findViewById(R.id.linearLayout);

        btn_confirmReservation = findViewById(R.id.BTNconfirmReservation);
        noOfDays = findViewById(R.id.noOfDays);
        scrollView = findViewById(R.id.scrollView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading review page...");
        progressDialog.show();

        int Temp_Rent_Days = GlobalVariables.F_Rentdays;
        String Rent_Daysx = new Integer(Temp_Rent_Days).toString();

        if (Temp_Rent_Days < 1) {
            noOfDays.setText("N/A");
        } else {
            noOfDays.setText(Rent_Daysx);
        }

        TV_location = findViewById(R.id.id_Location);
        TV_DateAndTime = findViewById(R.id.id_DateAndTime);

        TV_package = findViewById(R.id.id_package);
        TV_TotalPrice = findViewById(R.id.id_price);

        TV_vehicles_name = findViewById(R.id.id_vehiclesName);
        TV_Vehicles_platenumber = findViewById(R.id.id_vehiclesPlatenum);
        TV_Vehicles_capacity = findViewById(R.id.id_vehiclesCapacity);
        TV_Vehicles_transmission = findViewById(R.id.id_vehiclesTransmission);

        item_price = String.valueOf(GlobalVariables.F_TotalPrice);
        convertedprice = item_price.replaceAll("[^\\d.]", "");

        price = Double.parseDouble(convertedprice);

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(price);

        TV_TotalPrice.setText("₱" + formattedPrice + ".00");

        TV_vehicles_name.setText("Vehicle Name: " + GlobalVariables.SelectedVehiclesName);
        TV_Vehicles_platenumber.setText("Plate # " + GlobalVariables.SelectedVehiclesPlateNumber);
        TV_Vehicles_capacity.setText("Seat Capacity: " + String.valueOf(GlobalVariables.SelectedVehiclesCapacity));
        TV_Vehicles_transmission.setText("Transmission " + GlobalVariables.SelectedVehiclesTransmission);


        TV_driversName = findViewById(R.id.id_driversName);
        TV_Drivers_address = findViewById(R.id.id_driversAddress);
        TV_Drivers_mobile = findViewById(R.id.id_driversMobile);

        Driver_id = getIntent().getStringExtra("keyDriver_id");
        Driver_name = getIntent().getStringExtra("keyDriver_name");
        Driver_address = getIntent().getStringExtra("keyDriver_address");
        Driver_mobile = getIntent().getStringExtra("keyDriver_mobile");

        TV_driversName.setText("Driver's Name: " + GlobalVariables.SelectedDriversName);
        TV_Drivers_address.setText("Driver's Address: " + GlobalVariables.SelectedDriversAddress);
        TV_Drivers_mobile.setText("Driver's Mobile #: " + GlobalVariables.SelectedDriversMobile);

        TV_location.setText(GlobalVariables.F_Location + ", Pangasinan");
        TV_DateAndTime.setText(GlobalVariables.DisplayPickupDate + ", " + GlobalVariables.pckptime + " - " + GlobalVariables.DisplayReturnDate + ", " + GlobalVariables.rtrntime);
        TV_package.setText(GlobalVariables.F_Package);


        btn_confirmReservation.setOnClickListener(this);
    }


    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onClick(View v) {


        if (v == btn_confirmReservation)
            progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Reservation Inserting...");
        progressDialog.show();

        if (status.equals("0")) {

            progressDialog.dismiss();
            verifynowDialog = new Dialog(review.this);
            verifynowDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
            verifynowDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            verifynowDialog.setContentView(R.layout.dialog_accountverify);

            close = verifynowDialog.findViewById(R.id.close);
            verifyNow = verifynowDialog.findViewById(R.id.verifyNowBtn);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifynowDialog.dismiss();
                }
            });

            verifyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+63" + Phonenumber, 60, TimeUnit.SECONDS,
                            review.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                        // Invalid request
                                        Toast.makeText(getApplicationContext(), "Invalid Request", Toast.LENGTH_SHORT).show();
                                    } else if (e instanceof FirebaseTooManyRequestsException) {
                                        // The SMS quota for the project has been exceeded
                                        Toast.makeText(getApplicationContext(), "Can't verify at the moment", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Toast.makeText(review.this, "OTP Sent...", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(review.this, SMSOtpActivityOnProfile.class);
                                    i.putExtra("mobile_no", Phonenumber);
                                    Constants.verification_Code = verificationId;
                                    startActivity(i);
                                }

                            }
                    );

                    Toast.makeText(getApplicationContext(), "OTP Sending...", Toast.LENGTH_SHORT).show();

                }
            });

            verifynowDialog.setCancelable(true);
            verifynowDialog.show();


        }
        else if (requirementsPhotoModels.size()==0){
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "You are already verified but please upload an ID first before you can proceed to booking", Toast.LENGTH_LONG).show();
        }
        else {

            if (checkDateList.size() > 0) {
                progressDialog.dismiss();


                for (int x = 0; x < checkDateList.size(); x++) {
                    DateRangeModel model = checkDateList.get(x);
                    if (model.getVehicle_id().equals(String.valueOf(GlobalVariables.SelectedVehiclesID))) {
                        Toast.makeText(this, "Car is already on rent for selected date.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

            }


            insertreservation();


        }

    }


    private void insertreservation() {

        int Temp_Customer_ID = SharedPrefManager.getInstance(this).getId();
        String Customer_IDx = new Integer(Temp_Customer_ID).toString();

        String Customer_Usernamex = SharedPrefManager.getInstance(this).getUsername();
        String Customer_FirstNamex = SharedPrefManager.getInstance(this).getFirstname();
        String Customer_LastNamex = SharedPrefManager.getInstance(this).getLastname();
        String Customer_PhoneNumberx = SharedPrefManager.getInstance(this).getPhonenumber();
        String Customer_Emailx = SharedPrefManager.getInstance(this).getEmail();
        String Driver_IDx = GlobalVariables.SelectedDriversID;

        int Temp_Total_Price = GlobalVariables.F_TotalPrice;
        int package_amount = GlobalVariables.F_TotalPrice / GlobalVariables.F_Rentdays;
        String package_amountx = new Integer(package_amount).toString();
        String Total_Pricex = new Integer(Temp_Total_Price).toString();

        String Packagex = GlobalVariables.F_Package;
        String Pickup_Date_Timex = GlobalVariables.DisplayPickupDate + " " + GlobalVariables.pckptime;
        String Return_Date_Timex = GlobalVariables.DisplayReturnDate + " " + GlobalVariables.rtrntime;
        String Locationx = GlobalVariables.F_Location;

        int Temp_Rent_Days = GlobalVariables.F_Rentdays;
        String Rent_Daysx = new Integer(Temp_Rent_Days).toString();

        String MOPx = GlobalVariables.ModeOfPayment;
        String Ref_Numx = GlobalVariables.Reference_Number;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String result = jsonObject.getString("successs");

                            if (result.equals("11")) {
                                Intent i = new Intent(review.this, lastpage.class);
                                startActivity(i);
                                finish();
                                finishAffinity();
                                //updateCarStatus();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    NotificationChannel channel = new NotificationChannel("myCh", "myChannel", NotificationManager.IMPORTANCE_DEFAULT);
                                    NotificationManager manager = getSystemService(NotificationManager.class);

                                    manager.createNotificationChannel(channel);
                                }

                                if (GlobalVariables.F_Package.equals("Regular Package")) {
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(review.this, "myCh")
                                            .setSmallIcon(R.drawable.logo)
                                            .setContentTitle("Caravan Rental Booking Information")
                                            .setContentText("Booking Info")
                                            .setStyle(new NotificationCompat.BigTextStyle()
                                                    .bigText("Hi " + Customer_FirstNamex + ", you have successfully booked your car rental amounting to Php " + Total_Pricex + ".00. " +
                                                            /*"You have selected " + GlobalVariables.F_Package + " which includes Car fee, self-drive and full to full fuel policy.\n\n" +
                                                            "Destination: "+ GlobalVariables.F_Location + "\n" +
                                                            "Pick up date: "+ GlobalVariables.DisplayPickupDate + "\n" +
                                                            "Return date: " + GlobalVariables.DisplayReturnDate + "\n" +
                                                            "Rent Days: " + GlobalVariables.F_Rentdays+ */
                                                            "\nHowever, your booking is under review, please wait for our response within 24 hours.\nFor more info" +
                                                            " please check your transactions via in-app. Thank You!!"));

                                    notification = builder.build();

                                    notificationManagerCompat = NotificationManagerCompat.from(review.this);

                                    notificationManagerCompat.notify(1, notification);
                                    //Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    Log.e("anyText", response);
                                } else {
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(review.this, "myCh")
                                            .setSmallIcon(R.drawable.logo)
                                            .setContentTitle("Caravan Rental Booking Information")
                                            .setContentText("Booking Info")
                                            .setStyle(new NotificationCompat.BigTextStyle()
                                                    .bigText("Hi " + Customer_FirstNamex + ", you have successfully booked your car rental amounting to Php " + Total_Pricex + ".00. " +
                                                            "You have selected " + GlobalVariables.F_Package + " which includes Gas, Car fee, with driver, parking fee and toll fee.\n" +
                                                            /*"Driver's Name: "+ GlobalVariables.SelectedDriversName + "\n" +
                                                            "Destination: "+ GlobalVariables.F_Location + "\n" +
                                                            "Pick up date: "+ GlobalVariables.DisplayPickupDate + "\n" +
                                                            "Return date: " + GlobalVariables.DisplayReturnDate + "\n" +
                                                            "Rent Days: " + GlobalVariables.F_Rentdays+ */
                                                            "However, your booking is under review, please wait for our response within 24 hours.\nFor more info" +
                                                            " please check your transactions via in-app. Thank You!!"));

                                    notification = builder.build();

                                    notificationManagerCompat = NotificationManagerCompat.from(review.this);

                                    notificationManagerCompat.notify(1, notification);
                                    //Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    Log.e("anyText", response);
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message") + "\n" + response.toString(), Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText", response);
                            Toast.makeText(review.this, response.toString(), Toast.LENGTH_SHORT).show();
                            //TV_Drivers_address.setText(response.toString());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("customer_id", Customer_IDx);
                params.put("driver_id", Driver_IDx);
                params.put("vehicle_id", String.valueOf(GlobalVariables.SelectedVehiclesID));
                params.put("package_amount", package_amountx);
                params.put("rent_days", Rent_Daysx);
                params.put("total_amount", Total_Pricex);
                params.put("package_type", Packagex);
                params.put("booking_date", GlobalVariables.BookingDate);
                params.put("pick_up_date", Pickup_Date_Timex);
                params.put("return_date", Return_Date_Timex);
                params.put("location", Locationx);
                params.put("mode_of_payment", MOPx);
                //params.put("rent_status","");
                //params.put("created_at"," ");

                return params;
            }
        };

        RequestHandler.getInstance((Context) this).addToRequestQueue(stringRequest);

    }

    private void getDateList() {

        String Pickup_Date_Timex = GlobalVariables.DisplayPickupDate + " " + GlobalVariables.pckptime;
        String Return_Date_Timex = GlobalVariables.DisplayReturnDate + " " + GlobalVariables.rtrntime;

        String url = Constants.MAIN_URL + "checkdaterange.php?pick_up_date=" + Pickup_Date_Timex + "&return_date=" + Return_Date_Timex + "&vehicle_id=" + String.valueOf(GlobalVariables.SelectedVehiclesID);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
                progressDialog.dismiss();
                linearLayout.setVisibility(View.VISIBLE);
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
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("date_range");

            for (int i = 0; i < result.length(); i++) {
                try {
                    JSONObject jo = result.getJSONObject(i);

                    String pickupdate = jo.getString("pick_up_date");
                    String returndate = jo.getString("return_date");
                    String vehicle_id = jo.getString("vehicle_id");

                    DateRangeModel dateRangeModel = new DateRangeModel(
                            pickupdate, returndate, vehicle_id

                    );

                    checkDateList.add(dateRangeModel);

                } catch (Exception e) {
                    Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    linearLayout.setVisibility(View.VISIBLE);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getReqList() {
        //kunin yung id ng naka-login
        int Temp_Customer_ID = SharedPrefManager.getInstance(this).getId();
        String Customer_IDx = new Integer(Temp_Customer_ID).toString();

        String url = Constants.MAIN_URL + "uploadedreqlist(1).php?customer_id=" + Customer_IDx;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON2(response);
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

    private void showJSON2(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("tbl_photo");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);

                String id = jo.getString("id");
                String customer_id = jo.getString("customer_id");
                String photo_name = jo.getString("photo_name");
                String photo = jo.getString("photo");
                String created_at = jo.getString("created_at");


                RequirementsPhotoModel requirementsPhoto = new RequirementsPhotoModel(
                        id, photo_name,
                        customer_id, photo,
                        created_at

                );

                requirementsPhotoModels.add(requirementsPhoto);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}




