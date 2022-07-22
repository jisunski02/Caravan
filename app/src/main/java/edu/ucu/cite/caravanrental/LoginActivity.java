 package edu.ucu.cite.caravanrental;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity<Backendless> extends AppCompatActivity implements View.OnClickListener {

    // ToolBar
    private Toolbar toolbar;
    private TextView activityTitle;

    private TextInputEditText textInputEditTextUsername, textInputEditTextpassword;
    private Button btnsignin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Toolbar Initialization..
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activityTitle = findViewById(R.id.activity_title);
        activityTitle.setText("User Login");


        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,Mainscreen.class));
            return;
        }


        //Assign Variable
        textInputEditTextUsername = findViewById(R.id.etUsername);
        textInputEditTextpassword = findViewById(R.id.etPassword);
        btnsignin = findViewById(R.id.BTNsignin);

        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Please wait...");


        btnsignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == btnsignin) {
            userLogin();
        }
    }
    private void userLogin() {
        final String username = textInputEditTextUsername.getText().toString().trim();
        final String password = textInputEditTextpassword.getText().toString().trim();

        if (username.isEmpty()){
            textInputEditTextUsername.setError("Please enter your Email");
        }
        if (password.isEmpty()){
            textInputEditTextpassword.setError("Please enter your Password");
        }
        if (!username.isEmpty() && !password.isEmpty() ) {

            progressDialog.show();

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("login");

                                if(success.equals("1")){
                                    for(int a = 0; a <= jsonArray.length(); a++){
                                        JSONObject object = jsonArray.getJSONObject(a);

                                        int id = object.getInt("id");
                                        String firstname = object.getString("firstname");
                                        String lastname = object.getString("lastname");
                                        String phonenumber = object.getString("contact_no");
                                        String email = object.getString("email");
                                        String username = object.getString("username");
                                        String password = object.getString("password");
                                        String status = object.getString("user_status");
                                        String address = object.getString("address");
                                        SharedPrefManager.getInstance(getApplicationContext())
                                                .userLogin(id, username, email, password, firstname, lastname, address, phonenumber, status);
                                       // Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Mainscreen.class));
                                        finish();
                                    }
                                }

                                if(success.equals("0")) {
                                    Toast.makeText(getApplicationContext(), "Account doesn't exist", Toast.LENGTH_SHORT).show();
                                }

                                else{
                                    Toast.makeText(getApplicationContext(), "Account doesn't exist", Toast.LENGTH_SHORT).show();
                                }
                               /* Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                if (!jsonObject.getBoolean("error")) {
                                    SharedPrefManager.getInstance(getApplicationContext())
                                            .userLogin(
                                                    jsonObject.getInt("id"),
                                                    jsonObject.getString("username"),
                                                    jsonObject.getString("email"),
                                                    jsonObject.getString("password"),
                                                    jsonObject.getString("firstname"),
                                                    jsonObject.getString("lastname"),
                                                    jsonObject.getString("phonenumber")

                                            );

                                    startActivity(new Intent(getApplicationContext(), Mainscreen.class));
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            jsonObject.getString("message"),
                                            Toast.LENGTH_SHORT
                                    ).show();
                                } */

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("anyText", response);
                                //Toast.makeText(LoginActivity.this, "Awit \n"+response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
        else {
            Toast.makeText(getApplicationContext(), "All Fields are required", Toast.LENGTH_SHORT).show();
        }
    }



    public void signUp(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }
}
