package edu.ucu.cite.caravan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class profile extends AppCompatActivity {


    // ToolBar
    private Toolbar toolbar;
    private TextView activityTitle;
    private ImageView editProfile;
    private Button btnverifynow;

    Dialog dialog;

    TextInputEditText firstname, lastname, user_address;
    Button updateProfile;

    String fname, lname, useraddress;

    String id, Firstname, Lastname, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Toolbar Initialization..
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_activity));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.super.onBackPressed();
            }
        });

        activityTitle = findViewById(R.id.activity_title);
        editProfile = findViewById(R.id.editProfile);
        btnverifynow = findViewById(R.id.btnverifynow);


        TextView TV_fullname= findViewById(R.id.TV_fullname);
        TextView TV_phonenumber = findViewById(R.id.TV_phonenumber);
        TextView TV_email = findViewById(R.id.TV_Email);
        TextView TV_username = (TextView) findViewById(R.id.TV_username);
        TextView TV_address = findViewById(R.id.TV_address);
        TextView TV_status = findViewById(R.id.TV_status);

         id = String.valueOf(SharedPrefManager.getInstance(this).getId());
        String Username=SharedPrefManager.getInstance(this).getUsername();
        String Email=SharedPrefManager.getInstance(this).getEmail();
         Firstname=SharedPrefManager.getInstance(this).getFirstname();
         Lastname=SharedPrefManager.getInstance(this).getLastname();
        String Phonenumber=SharedPrefManager.getInstance(this).getPhonenumber();
         address = SharedPrefManager.getInstance(this).getAddress();
        String status = SharedPrefManager.getInstance(this).getStatus();

        TV_username.setText(Username);
        TV_fullname.setText(Firstname + " " + Lastname);
        TV_email.setText(Email);
        TV_phonenumber.setText(Phonenumber);
        TV_address.setText(address);
        TV_status.setText(status);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(profile.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_edit_profile);

                firstname = dialog.findViewById(R.id.fname);
                lastname = dialog.findViewById(R.id.lname);
                user_address = dialog.findViewById(R.id.address);
                updateProfile = dialog.findViewById(R.id.btnupdateprofile);

                fname = firstname.getText().toString().trim();
                lname = lastname.getText().toString().trim();
                useraddress = user_address.getText().toString().trim();

                firstname.setText(Firstname);
                lastname.setText(Lastname);
                user_address.setText(address);

                updateProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(profile.this, "Under construction..to be followed", Toast.LENGTH_SHORT).show();
                       updateProfile();
                    }
                });


                dialog.setCancelable(true);
                dialog.show();

            }
        });

        btnverifynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber("+63"+Phonenumber, 60, TimeUnit.SECONDS,
                        profile.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(getApplicationContext(), "Invalid Request", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(profile.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(profile.this, SMSOtpActivityOnProfile.class);
                                i.putExtra("mobile_no", Phonenumber);
                                Constants.verification_Code = verificationId;
                                startActivity(i);
                            }
                        }
                );
            }
        });

                if (status.equals("0")) {
                    TV_status.setBackgroundResource(R.drawable.buttongradient);
                    TV_status.setText("Unverified");



                } else {
                    TV_status.setText("Verified");
                    TV_status.setBackgroundResource(R.drawable.buttongradient2);
                    btnverifynow.setVisibility(View.GONE);
                }

                activityTitle.setText(Firstname + "\'s Profile");


            }

            private boolean isValidFirstname(){

                fname = firstname.getText().toString().trim();

                if(fname.isEmpty()){
                    firstname.setError("Field can't be empty");
                    return false;
                }
                else if(fname.length()<2){
                    firstname.setError("First name too short");
                    return false;
                }

                else{
                    //firstname.setText(null);
                    return true;
                }


            }

    private boolean isValidLastname(){
        lname = lastname.getText().toString().trim();

        if(lname.isEmpty()){
            lastname.setError("Field can't be empty");
            return false;
        }
        else if(lname.length()<2){
            lastname.setError("Last name too short");
            return false;
        }

        else{
            //lastname.setText(null);
            return true;
        }


    }

    private boolean isValidAddress(){
        useraddress = user_address.getText().toString().trim();

        if(useraddress.isEmpty()){
            user_address.setError("Field can't be empty");
            return false;
        }
        else if(useraddress.length()<2){
            user_address.setError("Address too short");
            return false;
        }

        else{
            //user_address.setText(null);
            return true;
        }


    }

    private void updateProfile(){

        try{
            if (!isValidFirstname() | !isValidLastname() | !isValidAddress()){
                return;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(profile.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("success");

                            if(result.equals("1")){
                                Toast.makeText(profile.this, "Account successfuly updated", Toast.LENGTH_SHORT).show();

                                SharedPrefManager.getInstance(getApplicationContext()).saveProfile(id, fname, lname,useraddress);
                                startActivity(new Intent(getApplicationContext(), Mainscreen.class));
                                finish();
                            }

                            else{
                                Toast.makeText(profile.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception jsonException){
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(profile.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("firstname", fname);
                params.put("lastname", lname);
                params.put("user_address", useraddress);
                return params;
            }
        };


        requestQueue.add(stringRequest);

    }


}