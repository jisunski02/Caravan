package edu.ucu.cite.caravanrental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class displayDetails extends AppCompatActivity {

    TextView ViewVehicleName,ViewDriverName,ViewDriverNumber,ViewDriverAddress,ViewPlateNumber,ViewTotalPrice,ViewPackage,ViewPickupDateTime,ViewReturnDateTime,ViewLocation,ViewRentdays,ViewMOP;
    String VehicleName,DriverName,DriverNumber,DriverAddress,PlateNumber,TotalPrice,Package,PickupDateTime,ReturnDateTime,Location,Rentdays,MOP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);

        ViewVehicleName = findViewById(R.id.VehicleName);
        ViewDriverName =  findViewById(R.id.DriverName);
        ViewDriverNumber = findViewById(R.id.DriverNumber);
        ViewDriverAddress = findViewById(R.id.DriverAddress);
        ViewPlateNumber = findViewById(R.id.PlateNumber);
        ViewTotalPrice = findViewById(R.id.TotalPrice);
        ViewPackage = findViewById(R.id.Package);
        ViewPickupDateTime = findViewById(R.id.PickupDateTime);
        ViewReturnDateTime = findViewById(R.id.ReturnDateTime);
        ViewLocation = findViewById(R.id.Location);
        ViewRentdays = findViewById(R.id.Rentdays);
        ViewMOP = findViewById(R.id.MOP);

        VehicleName = getIntent().getStringExtra("key_Vehicle_Name");
        DriverName = getIntent().getStringExtra("key_Driver_Name");
        DriverNumber = getIntent().getStringExtra("key_Driver_Number");
        DriverAddress = getIntent().getStringExtra("key_Driver_Address");
        PlateNumber = getIntent().getStringExtra("key_Plate_Number");
        TotalPrice = getIntent().getStringExtra("key_Total_Price");
        Package = getIntent().getStringExtra("key_Package");
        PickupDateTime = getIntent().getStringExtra("key_Pickup_Date_Time");
        ReturnDateTime = getIntent().getStringExtra("key_Return_Date_Time");
        Location = getIntent().getStringExtra("key_Location");
        Rentdays = getIntent().getStringExtra("key_Rent_days");
        MOP = getIntent().getStringExtra("key_MOP");

        ViewVehicleName.setText(VehicleName);
        ViewDriverName.setText(DriverName);
        ViewDriverNumber.setText(DriverNumber);
        ViewDriverAddress.setText(DriverAddress);
        ViewPlateNumber.setText(PlateNumber);
        ViewTotalPrice.setText(TotalPrice);
        ViewPackage.setText(Package);
        ViewPickupDateTime.setText(PickupDateTime);
        ViewReturnDateTime.setText(ReturnDateTime);
        ViewLocation.setText(Location);
        ViewRentdays.setText(Rentdays);
        ViewMOP.setText(MOP);


    }
}