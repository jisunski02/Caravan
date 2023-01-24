package edu.ucu.cite.caravanrental;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SMSOtpActivityOnProfile extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 60000;
    private LinearLayout linearLayout_hide_timer;
    private TextView textView_countDown;
    private TextView errorcatcher;
    private TextView customerNumber;
    private Button resendOTP, verifyOTP, proceed_without_verification;

    private CountDownTimer countDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private TextInputLayout verificationCode;

    String VerificationCode = "";
    String mobileNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_smsverify);

        customerNumber = findViewById(R.id.customerNumber);
        resendOTP = findViewById(R.id.resendOTP);
        verifyOTP = findViewById(R.id.verifyOTP);
        proceed_without_verification = findViewById(R.id.button_without_otp);
        errorcatcher = findViewById(R.id.code_error_catcher);
        textView_countDown = findViewById(R.id.timer_notice);
        linearLayout_hide_timer = findViewById(R.id.linear_timer_to_hide);

        verificationCode = findViewById(R.id.text_input_layout_verification_code);

        mobileNo = getIntent().getStringExtra("mobile_no");
        //VerificationCode = getIntent().getStringExtra("verification_code");

        String maskedNumber = mobileNo.replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
        customerNumber.setText(maskedNumber);
        // for OTP inputs with textwatcher


        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendOTP();
                Toast.makeText(SMSOtpActivityOnProfile.this, "Verifying OTP Credentials", Toast.LENGTH_SHORT).show();
                String verificationcode = verificationCode.getEditText().getText().toString();

                if(verificationcode.isEmpty()){
                    errorcatcher.setText("Verification code required");
                    errorcatcher.setTextColor(Color.RED);

                }
                else if (verificationcode.length()<6){
                    errorcatcher.setText("Verification code too short");
                    errorcatcher.setTextColor(Color.RED);
                }
                else{
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(Constants.verification_Code, verificationcode);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        //update user verification status in table user from 0 to 1(tbl_user)
                                        updateVerificationStatus();


                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Verification code not valid", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        proceed_without_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SMSOtpActivityOnProfile.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                linearLayout_hide_timer.setVisibility(View.INVISIBLE);
                resendOTP.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        updateCountDownText();



        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOTP();
                resendOTP.setVisibility(View.INVISIBLE);
                linearLayout_hide_timer.setVisibility(View.VISIBLE);

                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                updateCountDownText();

                countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long l) {
                        mTimeLeftInMillis = l;
                        updateCountDownText();
                    }

                    @Override
                    public void onFinish() {
                        mTimerRunning = false;
                        linearLayout_hide_timer.setVisibility(View.INVISIBLE);
                        resendOTP.setVisibility(View.VISIBLE);
                    }
                }.start();
                mTimerRunning = true;

               /* String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                StringBuilder salt = new StringBuilder();
                Random rnd = new Random();
                while (salt.length() < 6) { // length of the random string.
                    int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                    salt.append(SALTCHARS.charAt(index));
                }
                String saltStr = salt.toString(); */


            }
        });
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textView_countDown.setText(timeLeftFormatted);
    }

    private void updateVerificationStatus(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("success");

                            if(result.equals("1")){
                                Toast.makeText(SMSOtpActivityOnProfile.this, "Account successfuly verified", Toast.LENGTH_SHORT).show();

                                SharedPrefManager.getInstance(getApplicationContext()).saveVerificationCode("1");
                                //startActivity(new Intent(getApplicationContext(), Mainscreen.class));
                                finish();
                            }

                            else{
                                Toast.makeText(SMSOtpActivityOnProfile.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception jsonException){
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SMSOtpActivityOnProfile.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                int id=SharedPrefManager.getInstance(SMSOtpActivityOnProfile.this).getId();
                String stringId = String.valueOf(id);
                Map<String,String> params = new HashMap<>();
                params.put("contact_no", mobileNo);
                //params.put("verification_status", "1");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SMSOtpActivityOnProfile.this);
        requestQueue.add(stringRequest);
    }

    public void resendOTP(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+63"+mobileNo, 60, TimeUnit.SECONDS,
                SMSOtpActivityOnProfile.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(SMSOtpActivityOnProfile.this,"Already verified", Toast.LENGTH_SHORT).show();
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
                        Constants.verification_Code = verificationId;
                        Toast.makeText(SMSOtpActivityOnProfile.this,"Verification code has been resent", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}