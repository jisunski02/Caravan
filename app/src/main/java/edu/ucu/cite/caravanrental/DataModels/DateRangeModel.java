package edu.ucu.cite.caravanrental.DataModels;

public class DateRangeModel {

    String pick_up_date;
    String return_date;
    String vehicle_id;

    public DateRangeModel(String pick_up_date, String return_date, String vehicle_id) {
        this.pick_up_date = pick_up_date;
        this.return_date = return_date;
        this.vehicle_id = vehicle_id;
    }

    public String getPick_up_date() {
        return pick_up_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }
}
