package edu.ucu.cite.caravanrental;

public class CRents {

    private String Driver_Name;
    private String Driver_Number;
    private String Driver_Address;
    private String Vehicle_Name;
    private String Plate_Number;
    private String Total_Price;
    private String Package;
    private String Pickup_Date_Time;
    private String Return_Date_Time;
    private String Location;
    private String Rent_days;
    private String MOP;

    public CRents(String driver_Name, String driver_Number, String driver_Address, String vehicle_Name, String plate_Number, String total_Price, String aPackage, String pickup_Date_Time, String return_Date_Time, String location, String rent_days, String MOP) {
        Driver_Name = driver_Name;
        Driver_Number = driver_Number;
        Driver_Address = driver_Address;
        Vehicle_Name = vehicle_Name;
        Plate_Number = plate_Number;
        Total_Price = total_Price;
        Package = aPackage;
        Pickup_Date_Time = pickup_Date_Time;
        Return_Date_Time = return_Date_Time;
        Location = location;
        Rent_days = rent_days;
        this.MOP = MOP;
    }

    public String getDriver_Name() {
        return Driver_Name;
    }

    public String getDriver_Number() {
        return Driver_Number;
    }

    public String getDriver_Address() {
        return Driver_Address;
    }

    public String getVehicle_Name() {
        return Vehicle_Name;
    }

    public String getPlate_Number() {
        return Plate_Number;
    }

    public String getTotal_Price() {
        return Total_Price;
    }

    public String getPackage() {
        return Package;
    }

    public String getPickup_Date_Time() {
        return Pickup_Date_Time;
    }

    public String getReturn_Date_Time() {
        return Return_Date_Time;
    }

    public String getLocation() {
        return Location;
    }

    public String getRent_days() {
        return Rent_days;
    }

    public String getMOP() {
        return MOP;
    }
}
