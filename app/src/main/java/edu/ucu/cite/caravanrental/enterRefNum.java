package edu.ucu.cite.caravanrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class enterRefNum extends AppCompatActivity {

    EditText ref_num;
    Button BTN_Enter;

    int Vehicle_id,Vehicle_capacity,Vehicle_price;
    String Vehicle_name,Vehicle_platenumber,Vehicle_transmission,Temp_refnum;

    TextView TV_TotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_ref_num);

        ref_num = findViewById(R.id.id_ref_num);
        BTN_Enter = findViewById(R.id.id_BTN_Enter);
        TV_TotalPrice = findViewById(R.id.id_amount);

        TV_TotalPrice.setText(String.valueOf(GlobalVariables.F_TotalPrice));




        Vehicle_id = getIntent().getIntExtra("key_Vehicles_ID", 0);
        Vehicle_name = getIntent().getStringExtra("key_Vehicles_Name");
        Vehicle_platenumber = getIntent().getStringExtra("key_Vehicles_Platenum");
        Vehicle_capacity = getIntent().getIntExtra("key_Vehicles_Capacity", 0);
        Vehicle_transmission = getIntent().getStringExtra("key_Vehicles_Transmission");
        Vehicle_price = getIntent().getIntExtra("key_Vehicles_Price", 0);



        BTN_Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ref_num.length() < 13) {
                    ref_num.requestFocus();
                    ref_num.setError("Ref. no. must be at least 13 digits");
                } else {

                    Temp_refnum = ref_num.getText().toString();
                    GlobalVariables.Reference_Number = Temp_refnum;

                    Intent intentZ = new Intent(enterRefNum.this, review.class);
                    intentZ.putExtra("key_Vehicles_ID", Vehicle_id);
                    intentZ.putExtra("key_Vehicles_Name", Vehicle_name);
                    intentZ.putExtra("key_Vehicles_Platenum", Vehicle_platenumber);
                    intentZ.putExtra("key_Vehicles_Capacity", Vehicle_capacity);
                    intentZ.putExtra("key_Vehicles_Transmission", Vehicle_transmission);
                    intentZ.putExtra("key_Vehicles_Price", Vehicle_price);
                    startActivity(intentZ);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        });




    }

    public void openGCash(View view){
        //Intent intent = getPackageManager().getLaunchIntentForPackage("com.globe.gcash.android");
        //startActivity(intent);

        final String appPackageName = "com.globe.gcash.android"; // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}