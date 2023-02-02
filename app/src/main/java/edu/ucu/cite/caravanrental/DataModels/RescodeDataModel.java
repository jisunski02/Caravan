package edu.ucu.cite.caravanrental.DataModels;

public class RescodeDataModel {

    private String driver_id;
    private String rescode;

    public RescodeDataModel(String driver_id, String rescode) {
        this.driver_id = driver_id;
        this.rescode = rescode;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public String getRescode() {
        return rescode;
    }
}
