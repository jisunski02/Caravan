package edu.ucu.cite.caravanrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class selectmop extends AppCompatActivity {

    private RadioGroup radioGroupII;
    RadioButton radiobutton;
    Button Button_Next;
    String MOP;

    String Driver_id,Driver_name,Driver_mobile,Driver_address;
    int Vehicle_id,Vehicle_capacity,Vehicle_price;
    int Total_price;
    String Vehicle_name,Vehicle_platenumber,Vehicle_transmission;

    // ToolBar
    private Toolbar toolbar;
    private TextView activityTitle;

    String Drivers_Name, Drivers_ID, Drivers_Address, Drivers_Mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmop);

        //Toolbar Initialization..
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_activity));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectmop.super.onBackPressed();
            }
        });

        activityTitle = findViewById(R.id.activity_title);
        activityTitle.setText("Mode of Payment");

        radioGroupII = findViewById(R.id.radio_groupII);
        Button_Next = findViewById(R.id.Button_Next);


        if(GlobalVariables.F_Package=="Regular Package"){
            Drivers_ID="0";
            Drivers_Name = "None";
            Drivers_Address = "None";
            Drivers_Mobile = "None";

            GlobalVariables.SelectedDriversID = Drivers_ID;
            GlobalVariables.SelectedDriversName = Drivers_Name;
            GlobalVariables.SelectedDriversAddress = Drivers_Address;
            GlobalVariables.SelectedDriversMobile = Drivers_Mobile;
//COMPUTATION OF TOTAL PRICE
            GlobalVariables.F_TotalPrice=(Integer.parseInt(GlobalVariables.regularVehiclePrice) *GlobalVariables.F_Rentdays);

        }else
        {
            Drivers_ID =  getIntent().getStringExtra("keyDriver_id");
            Drivers_Name = getIntent().getStringExtra("keyDriver_name");
            Drivers_Address = getIntent().getStringExtra("keyDriver_address");
            Drivers_Mobile = getIntent().getStringExtra("keyDriver_mobile");

            GlobalVariables.SelectedDriversID = Drivers_ID;
            GlobalVariables.SelectedDriversName = Drivers_Name;
            GlobalVariables.SelectedDriversAddress = Drivers_Address;
            GlobalVariables.SelectedDriversMobile = Drivers_Mobile;
            //COMPUTATION OF TOTAL PRICE
            GlobalVariables.F_TotalPrice=(Integer.parseInt(GlobalVariables.completeVehiclePrice) *GlobalVariables.F_Rentdays);
        }



        Button_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = radioGroupII.getCheckedRadioButtonId();
                checkButton(checkedId);

                if(MOP=="Cash On Pickup"){

                    Intent intentX = new Intent(selectmop.this,review.class);
                    intentX.putExtra("key_Vehicles_ID",Vehicle_id);
                    intentX.putExtra("key_Vehicles_Name",Vehicle_name);
                    intentX.putExtra("key_Vehicles_Platenum",Vehicle_platenumber);
                    intentX.putExtra("key_Vehicles_Capacity",Vehicle_capacity);
                    intentX.putExtra("key_Vehicles_Transmission",Vehicle_transmission);
                    intentX.putExtra("key_Vehicles_Price",Vehicle_price);

                    GlobalVariables.ModeOfPayment=MOP;
                    GlobalVariables.Reference_Number="None";
                    startActivity(intentX);
                    overridePendingTransition(R.anim.fadein,R.anim.fadeout);

                }else{

                    Intent intentY = new Intent(selectmop.this,review.class);
                    intentY.putExtra("key_Vehicles_ID",Vehicle_id);
                    intentY.putExtra("key_Vehicles_Name",Vehicle_name);
                    intentY.putExtra("key_Vehicles_Platenum",Vehicle_platenumber);
                    intentY.putExtra("key_Vehicles_Capacity",Vehicle_capacity);
                    intentY.putExtra("key_Vehicles_Transmission",Vehicle_transmission);
                    intentY.putExtra("key_Vehicles_Price",Vehicle_price);

                    GlobalVariables.ModeOfPayment=MOP;
                    startActivity(intentY);
                    overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                }
            }
        });

    }


    private void checkButton(int checkedId) {
        checkedId = radioGroupII.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.cop:
                //Message.message(getApplicationContext(), "Mode of payment: Cash on pickup");
                // set value for the variable "MOP"
                MOP="Cash On Pickup";
                break;
            case R.id.gcash:
                //Message.message(getApplicationContext(), "Mode of payment: GCash");
                // set value for the variable "MOP"
                MOP="GCash";
                break;
        }
    }
}