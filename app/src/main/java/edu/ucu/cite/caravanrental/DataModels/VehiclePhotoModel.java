package edu.ucu.cite.caravanrental.DataModels;

public class VehiclePhotoModel {

    private String id;
    private int vehicle_id;
    private String vehicle_name;
    private String created_at;

    public VehiclePhotoModel(String id, int vehicle_id, String vehicle_name, String created_at) {
        this.id = id;
        this.vehicle_id = vehicle_id;
        this.vehicle_name = vehicle_name;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public String getCreated_at() {
        return created_at;
    }
}
