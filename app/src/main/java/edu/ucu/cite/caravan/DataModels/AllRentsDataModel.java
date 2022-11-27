package edu.ucu.cite.caravan.DataModels;

public class AllRentsDataModel {

    private String id;
    private String booking_number;
    private String invoice_number;
    private String transaction_no;
    private String customer_id;
    private String driver_id;
    private String vehicle_id;
    private String package_amount;
    private String rent_days;
    private String total_amount;
    private String package_type;
    private String booking_date;
    private String pick_up_date;
    private String return_date;
    private String approved_date;
    private String location;
    private String mode_of_payment;
    private String rent_status;
    private String reason;
    private String cancelledBy;
    private String vehicle_photo;
    private String vehicle_name;

    public AllRentsDataModel(String id, String booking_number, String invoice_number, String transaction_no, String customer_id, String driver_id, String vehicle_id, String package_amount, String rent_days, String total_amount, String package_type, String booking_date, String pick_up_date, String return_date, String approved_date, String location, String mode_of_payment, String rent_status, String reason, String cancelledBy, String vehicle_photo, String vehicle_name) {

        this.id = id;
        this.booking_number = booking_number;
        this.invoice_number = invoice_number;
        this.transaction_no = transaction_no;
        this.customer_id = customer_id;
        this.driver_id = driver_id;
        this.vehicle_id = vehicle_id;
        this.package_amount = package_amount;
        this.rent_days = rent_days;
        this.total_amount = total_amount;
        this.package_type = package_type;
        this.booking_date = booking_date;
        this.pick_up_date = pick_up_date;
        this.return_date = return_date;
        this.approved_date = approved_date;
        this.location = location;
        this.mode_of_payment = mode_of_payment;
        this.rent_status = rent_status;
        this.reason = reason;
        this.cancelledBy = cancelledBy;
        this.vehicle_photo = vehicle_photo;
        this.vehicle_name = vehicle_name;
    }

    public String getId() {
        return id;
    }

    public String getBooking_number() {
        return booking_number;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public String getTransaction_no() {
        return transaction_no;
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

    public String getPackage_amount() {
        return package_amount;
    }

    public String getRent_days() {
        return rent_days;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public String getPackage_type() {
        return package_type;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getPick_up_date() {
        return pick_up_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public String getApproved_date() {
        return approved_date;
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

    public String getReason() {
        return reason;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public String getVehicle_photo() {
        return vehicle_photo;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }
}
