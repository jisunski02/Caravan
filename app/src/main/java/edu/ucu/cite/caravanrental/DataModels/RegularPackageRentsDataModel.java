package edu.ucu.cite.caravanrental.DataModels;

public class RegularPackageRentsDataModel {

    private String customer_id;
    private String driver_id;
    private String vehicle_id;
    private String total_price;
    private String rent_package;
    private String pick_up_date;
    private String return_date;
    private String location;
    private String mode_of_payment;
    private String rent_status;
    private String rent_days;
    private String approved_date;
    private String reason;
    private String created_at;
    private String vehicle_photo;
    private String vehicle_transmission;
    private String vehicle_name;
    private String year_model;
    private String seat_capacity;
    private String manufactured_by;
    private String plate_number;
    private String vehicle_color;
    private String registration_expiry;
    private String price;
    private String vehicle_status;

    public RegularPackageRentsDataModel(

            String customer_id,
            String driver_id,
            String vehicle_id,
            String total_price,
            String rent_package,
            String pick_up_date,
            String return_date,
            String location,
            String mode_of_payment,
            String rent_status,
            String rent_days,
            String approved_date,
            String reason,
            String created_at,
            String vehicle_photo,
            String vehicle_transmission,
            String vehicle_name,
            String year_model,
            String seat_capacity,
            String manufactured_by,
            String plate_number,
            String vehicle_color,
            String registration_expiry,
            String price,
            String vehicle_status
    ){
        this.customer_id = customer_id;
        this.driver_id = driver_id;
        this.vehicle_id = vehicle_id;
        this.total_price = total_price;
        this.rent_package = rent_package;
        this.pick_up_date = pick_up_date;
        this.return_date = return_date;
        this.location = location;
        this.mode_of_payment = mode_of_payment;
        this.rent_status = rent_status;
        this.rent_days = rent_days;
        this.approved_date = approved_date;
        this.reason = reason;
        this.created_at = created_at;
        this.vehicle_photo = vehicle_photo;
        this.vehicle_transmission = vehicle_transmission;
        this.vehicle_name = vehicle_name;
        this.year_model = year_model;
        this.seat_capacity = seat_capacity;
        this.manufactured_by = manufactured_by;
        this.plate_number = plate_number;
        this.vehicle_color = vehicle_color;
        this.registration_expiry = registration_expiry;
        this.price = price;
        this.vehicle_status = vehicle_status;


    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getRent_package() {
        return rent_package;
    }

    public String getPick_up_date() {
        return pick_up_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public String getLocation() {
        return location;
    }

    public String getMode_of_payment() {
        return mode_of_payment;
    }

    public String getRent_status() {
        return rent_status;
    }

    public String getRent_days() {
        return rent_days;
    }

    public String getApproved_date() {
        return approved_date;
    }

    public String getReason() {
        return reason;
    }

    public String getCreated_at() {
        return created_at;
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

    public String getPrice() {
        return price;
    }

    public String getVehicle_status() {
        return vehicle_status;
    }
}
