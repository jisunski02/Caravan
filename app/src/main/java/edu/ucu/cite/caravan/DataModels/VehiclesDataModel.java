package edu.ucu.cite.caravan.DataModels;

public class VehiclesDataModel {
    // Data model for vehicles
    private int vehicle_id;
    private String vehicle_photo;
    private String vehicle_transmission;
    private String vehicle_name;
    private String year_model;
    private String seat_capacity;
    private String chasis_no;
    private String engine_no;
    private String manufactured_by;
    private String plate_number;
    private String vehicle_color;
    private String registration_expiry;
    private String vehicle_status;
    private String created_at;

    public VehiclesDataModel(
            int vehicle_id,
            String vehicle_photo,
            String vehicle_transmission,
            String vehicle_name,
            String year_model,
            String seat_capacity,
            String chasis_no,
            String engine_no,
            String manufactured_by,
            String plate_number,
            String vehicle_color,
            String registration_expiry,
            String vehicle_status,
            String created_at
    ){
        this.vehicle_id = vehicle_id;
        this.vehicle_photo = vehicle_photo;
        this.vehicle_transmission = vehicle_transmission;
        this.vehicle_name = vehicle_name;
        this.year_model = year_model;
        this.seat_capacity = seat_capacity;
        this.chasis_no = chasis_no;
        this.engine_no = engine_no;
        this.manufactured_by = manufactured_by;
        this.plate_number = plate_number;
        this.vehicle_color = vehicle_color;
        this.registration_expiry = registration_expiry;
        this.vehicle_status = vehicle_status;
        this.created_at = created_at;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public String getVehicle_photo() {
        return vehicle_photo;
    }

    public String getVehicle_transmission() {
        return vehicle_transmission;
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

    public String getChasis_no() {
        return chasis_no;
    }

    public String getEngine_no() {
        return engine_no;
    }

    public String getManufactured_by() {
        return manufactured_by;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public String getVehicle_color() {
        return vehicle_color;
    }

    public String getRegistration_expiry() {
        return registration_expiry;
    }

    public String getVehicle_status() {
        return vehicle_status;
    }

    public String getCreated_at() {
        return created_at;
    }
}
