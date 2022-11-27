package edu.ucu.cite.caravan.DataModels;

import com.google.android.gms.maps.model.LatLng;

public class RepairShopModel {

    LatLng latLng;
    String repairShopName;

    public RepairShopModel(LatLng latLng, String repairShopName) {
        this.latLng = latLng;
        this.repairShopName = repairShopName;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getRepairShopName() {
        return repairShopName;
    }
}
