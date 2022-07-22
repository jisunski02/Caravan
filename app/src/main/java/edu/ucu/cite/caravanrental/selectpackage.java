package edu.ucu.cite.caravanrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class selectpackage extends AppCompatActivity {

    //TODO: add navigation view here
    //TextView displayLocation,displayPickdate,displayPickhour,displayReturndate,displayReturnhour,displayRentDays;

    private RadioGroup radioGroup;
    RadioButton radiobutton;
    Button b_next, btnNext;
    String Package="Walang Laman",Location,Pickupdate,Pickuphour,Returndate,Returnhour;
    Long Rentdays;
    String TextReturnMonth,TextPickupMonth;
    //Final variable
    String Fpickupdate,Fpickuptime,Freturndate,Freturntime;

    // ToolBar
    private Toolbar toolbar;
    private TextView activityTitle;

    String Driver_id,Driver_name,Driver_mobile,Driver_address;
    int Vehicle_id,Vehicle_capacity,Vehicle_price;
    int Total_price;
    String Vehicle_name,Vehicle_platenumber,Vehicle_transmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectpackage);

       // displayLocation=findViewById(R.id.idLocation);
        //displayPickdate=findViewById(R.id.idPickDate);
        //displayPickhour=findViewById(R.id.idPickhour);
        //displayReturndate=findViewById(R.id.idReturnDate);
        //displayReturnhour=findViewById(R.id.idReturnHour);
        // displayRentDays=findViewById(R.id.idRentdays);

        //Toolbar Initialization..
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_activity));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectpackage.super.onBackPressed();
            }
        });

        activityTitle = findViewById(R.id.activity_title);
        activityTitle.setText("Select Package");

        Vehicle_id = getIntent().getIntExtra("key_Vehicles_ID", 0);
        Vehicle_name = getIntent().getStringExtra("key_Vehicles_Name");
        Vehicle_platenumber = getIntent().getStringExtra("key_Vehicles_Platenum");
        Vehicle_capacity = getIntent().getIntExtra("key_Vehicles_Capacity", 0);
        Vehicle_transmission = getIntent().getStringExtra("key_Vehicles_Transmission");
        Vehicle_price = getIntent().getIntExtra("key_Vehicles_Price", 0);

        GlobalVariables.SelectedVehiclesID=Vehicle_id;
        GlobalVariables.SelectedVehiclesName=Vehicle_name;
        GlobalVariables.SelectedVehiclesPlateNumber=Vehicle_platenumber;
        GlobalVariables.SelectedVehiclesTransmission=Vehicle_transmission;

        b_next = findViewById(R.id.btnNext);
        radioGroup = findViewById(R.id.radio_group);

        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                checkButton(checkedId);

                if(Package=="Regular Package"){

                Intent intentX = new Intent(selectpackage.this,selectmop.class);
                    /*intentX.putExtra("nextPackage",Package.toString());
                    intentX.putExtra("nextlocation",Location.toString());
                    intentX.putExtra("nextdate",Pickupdate.toString());
                    intentX.putExtra("nextpickuphour",Pickuphour.toString());
                    intentX.putExtra("nextreturndate",Returndate.toString());
                    intentX.putExtra("nextreturntime",Returnhour.toString());
                    intentX.putExtra("nextRentdays", Rentdays);
        */
                    //GlobalVariables.F_Location=Location;
                    GlobalVariables.F_Package=Package;
                    //GlobalVariables.F_Rentdays=Rentdays.intValue();

                startActivity(intentX);

                }else{

                    Intent intent = new Intent(selectpackage.this,trydriverlist.class);
                   /* intent.putExtra("nextlocation",Location.toString());
                    intent.putExtra("nextdate",Pickupdate.toString());
                    intent.putExtra("nextpickuphour",Pickuphour.toString());
                    intent.putExtra("nextreturndate",Returndate.toString());
                    intent.putExtra("nextreturntime",Returnhour.toString());
                    intent.putExtra("nextRentdays", Rentdays);
                    intent.putExtra("nextPackage", Package);
*/
                    //GlobalVariables.F_Location=Location;
                    GlobalVariables.F_Package=Package;
                    //GlobalVariables.F_Rentdays=Rentdays.intValue();
                    startActivity(intent);
                }
            }
        });
    }

    private void checkButton(int checkedId) {
        checkedId = radioGroup.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.reg_package:
                Message.message(getApplicationContext(), "Regular package is selected");
                // set value for the variable "Package"
                Package="Regular Package";
                break;
            case R.id.com_package:
                Message.message(getApplicationContext(), "Complete package is selected");
                // set value for the variable "Package"
                Package="Complete Package";
                break;
        }

    }

}