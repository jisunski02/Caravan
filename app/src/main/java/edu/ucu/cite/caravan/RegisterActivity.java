package edu.ucu.cite.caravan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    // ToolBar
    private Toolbar toolbar;
    private TextView activityTitle;

    private TextInputEditText textInputEditTextLastname, textInputEditTextFirstname, textInputEditTextphonenumber,
            textInputEditTextEmail, textInputEditTextUsername, textInputEditTextpassword, textInputEditTextconfirmpassword, textInputEditTextAddress;
    private Button btnsignup ;

    //try checkbox
    CheckBox checkBox;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Toolbar Initialization..
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_activity));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.super.onBackPressed();
            }
        });

        activityTitle = findViewById(R.id.activity_title);
        activityTitle.setText("Sign up");

        //Assign Variable
        textInputEditTextLastname = findViewById(R.id.lname);
        textInputEditTextFirstname = findViewById(R.id.fname);
        textInputEditTextphonenumber = findViewById(R.id.phonenumber);
        textInputEditTextEmail = findViewById(R.id.etEmail);
        textInputEditTextUsername = findViewById(R.id.etUsername);
        textInputEditTextpassword = findViewById(R.id.etPassword);
        textInputEditTextconfirmpassword = findViewById(R.id.etRepassword);
        textInputEditTextAddress = findViewById(R.id.etAddress);
        btnsignup = findViewById(R.id.BTNsignup);

        progressDialog = new ProgressDialog(this);

        checkBox = findViewById(R.id.check);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    btnsignup.setEnabled(true);



                }else {
                    btnsignup.setEnabled(false);
                }

            }
        });

        btnsignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnsignup)

            registerUser();
    }

    private boolean registerUser() {
        final String firstname = textInputEditTextFirstname.getText().toString().trim();
        final String lastname = textInputEditTextLastname.getText().toString().trim();
        final String phone = textInputEditTextphonenumber.getText().toString().trim();
        final String email = textInputEditTextEmail.getText().toString().trim();
        final String username = textInputEditTextUsername.getText().toString().trim();
        final String password = textInputEditTextpassword.getText().toString().trim();
        final String repassword = textInputEditTextconfirmpassword.getText().toString().trim();
        final String address = textInputEditTextAddress.getText().toString().trim();

        if (firstname.length() == 0) {
            textInputEditTextFirstname.requestFocus();
            textInputEditTextFirstname.setError("Field can't be empty");
            return false;
        } else if (!firstname.matches("[a-zA-Z]+")) {
            textInputEditTextFirstname.requestFocus();
            textInputEditTextFirstname.setError("Enter alphabetical character only!");
            return false;
        } else if (lastname.length() == 0) {
            textInputEditTextLastname.requestFocus();
            textInputEditTextLastname.setError("Field can't be empty");
            return false;
        } else if (!lastname.matches("[a-zA-Z]+")) {
            textInputEditTextLastname.requestFocus();
            textInputEditTextLastname.setError("Enter alphabetical character only!");
            return false;
        } else if (phone.length() == 0) {
            textInputEditTextphonenumber.requestFocus();
            textInputEditTextphonenumber.setError("Field can't be empty");
            return false;
        } else if (!phone.matches("[0-9]{10,13}$")) {
            textInputEditTextphonenumber.requestFocus();
            textInputEditTextphonenumber.setError("Correct Format: +639****");
            return false;
        } else if (email.length() == 0) {
            textInputEditTextEmail.requestFocus();
            textInputEditTextEmail.setError("Field can't be empty");
            return false;
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            textInputEditTextEmail.requestFocus();
            textInputEditTextEmail.setError("Please enter a valid Email address!");
            return false;
        }
        else if (username.length() == 0) {
            textInputEditTextUsername.requestFocus();
            textInputEditTextUsername.setError("Field can't be empty");
            return false;
        } else if (username.length() < 3) {
            textInputEditTextUsername.requestFocus();
            textInputEditTextUsername.setError("Username must be at least 3 characters");
            return false;
        } else if (password.length() == 0) {
            textInputEditTextpassword.requestFocus();
            textInputEditTextpassword.setError("Field can't be empty");
            return false;
        } else if (repassword.length() == 0) {
            textInputEditTextconfirmpassword.requestFocus();
            textInputEditTextconfirmpassword.setError("Field can't be empty");
            return false;
        } else if (password.length() < 5) {
            textInputEditTextpassword.requestFocus();
            textInputEditTextpassword.setError("Password must be at least 5 characters");
            return false;
        } else if (!password.equals(repassword)) {
            textInputEditTextconfirmpassword.requestFocus();
            textInputEditTextconfirmpassword.setError("Password Would Not be matched");
            return false;
        }

        else if (address.isEmpty()) {
            textInputEditTextAddress.requestFocus();
            textInputEditTextAddress.setError("Address is required");
            return false;
        } else if (address.length()<6) {
            textInputEditTextAddress.requestFocus();
            textInputEditTextAddress.setError("Address is too short!");
            return false;
        }

        else if (!(firstname.isEmpty() && lastname.isEmpty() && phone.isEmpty() && email.isEmpty() && username.isEmpty() && password.isEmpty() && address.isEmpty())){

            progressDialog.setMessage("Registering user...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.getString("success");

                                if(result.equals("1")){
                                   /* Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(i); */
                                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+63"+phone, 60, TimeUnit.SECONDS,
                                            RegisterActivity.this,
                                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

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
                                                    Toast.makeText(getApplicationContext(), "OTP Sent", Toast.LENGTH_SHORT).show();
                                                     Intent i = new Intent(RegisterActivity.this, SMSOtpActivity.class);
                                                     i.putExtra("mobile_no", phone);
                                                     Constants.verification_Code = verificationId;
                                                     startActivity(i);
                                                }
                                            }
                                            );

                                    Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "OTP Sending...", Toast.LENGTH_SHORT).show();

                                }

                                else{
                                    Toast.makeText(getApplicationContext(), "Email or phone number already taken", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("anyText", response);
                                Toast.makeText(getApplicationContext(), "Register Fail \n" + response.toString(), Toast.LENGTH_SHORT).show();
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
                    params.put("firstname", firstname);
                    params.put("lastname", lastname);
                    params.put("contact_no", phone);
                    params.put("email", email);
                    params.put("address", address);
                    params.put("username", username);
                    params.put("password", password);
                    params.put("type", "customer");
                    //params.put("confirmpassword", repassword);
                    // params.put("address", address);
                    // params.put("zone", zone);
                    return params;
                }
            };
            //RequestHandler.getInstance((Context) this).addToRequestQueue(stringRequest);
            RequestHandler.getInstance((Context) this).addToRequestQueue(stringRequest);
            return true;
            // RequestQueue requestQueue = Volley.newRequestQueue(this);
            // requestQueue.add(stringRequest);
        }
        else {
            return false;
        }
    }

    //LINKS
    public void signin(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
    public void termsofservice(View view) {
       Intent intent = new Intent(this,TermsOfService.class);
        startActivity(intent);

    }
    public void privacypolicy(View view) {
        Intent intent = new Intent(this,PrivacyPolicy.class);
        startActivity(intent);
    }
}