package edu.ucu.cite.caravanrental.DataModels;

public class VehiclePhotoModel {

    private String id;
    private String vehicle_id;
    private String vehicle_name;
    private String ceeated_at;

    public VehiclePhotoModel(String id, String vehicle_id, String vehicle_name, String ceeated_at) {
        this.id = id;
        this.vehicle_id = vehicle_id;
        this.vehicle_name = vehicle_name;
        this.ceeated_at = ceeated_at;
    }

    public String getId() {
        return id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public String getCeeated_at() {
        return ceeated_at;
    }
}
