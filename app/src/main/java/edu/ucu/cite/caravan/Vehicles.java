package edu.ucu.cite.caravan;

public class Vehicles {

    private int vehicles_id;
    private String vehicle_category;
    private String vehicle_photo;
    private String transmission;
    private String vehicle_name;
    private String year_model;
    private String seat_capacity;
    private String manufactured_by;
    private String vehicle_platenum;
    private String vehicle_color;
    private String vehicle_regexpiry;
    private String regular_package;
    private String complete_package;
    private String vehicle_status;


    public Vehicles(int vehicles_id, String vehicle_category, String vehicle_photo, String transmission, String vehicle_name, String year_model, String seat_capacity, String manufactured_by, String vehicle_platenum, String vehicle_color, String vehicle_regexpiry, String regular_package, String complete_package, String vehicle_status) {
        this.vehicles_id = vehicles_id;
        this.vehicle_category = vehicle_category;
        this.vehicle_photo = vehicle_photo;
        this.transmission = transmission;
        this.vehicle_name = vehicle_name;
        this.year_model = year_model;
        this.seat_capacity = seat_capacity;
        this.manufactured_by = manufactured_by;
        this.vehicle_platenum = vehicle_platenum;
        this.vehicle_color = vehicle_color;
        this.vehicle_regexpiry = vehicle_regexpiry;
        this.regular_package = regular_package;
        this.complete_package = complete_package;
        this.vehicle_status = vehicle_status;
    }

    public int getVehicles_id() {
        return vehicles_id;
    }

    public String getVehicle_category() {
        return vehicle_category;
    }

    public String getVehicle_photo() {
        return vehicle_photo;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public String getYear_model() {
        return year_model;
    }

    public String getSeat_capacity() {
        return seat_capacity;
    }

    public String getManufactured_by() {
        return manufactured_by;
    }

    public String getVehicle_platenum() {
        return vehicle_platenum;
    }

    public String getVehicle_color() {
        return vehicle_color;
    }

    public String getVehicle_regexpiry() {
        return vehicle_regexpiry;
    }

    public String getRegular_package() {
        return regular_package;
    }

    public String getComplete_package() {
        return complete_package;
    }

    public String getVehicle_status() {
        return vehicle_status;
    }
}



