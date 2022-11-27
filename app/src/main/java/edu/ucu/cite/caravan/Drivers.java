package edu.ucu.cite.caravan;

public class Drivers {

    private String drivers_id;
    private String drivers_name;
    private String drivers_address;
    private String drivers_mobile;
    private String drivers_photo;
    private String drivers_exp;
    private String drivers_license;
    private String driver_license_restriction;

    public Drivers(String drivers_id, String drivers_name, String drivers_address, String drivers_mobile, String drivers_photo, String drivers_exp,String drivers_license) {
        this.drivers_id = drivers_id;
        this.drivers_name = drivers_name;
        this.drivers_address = drivers_address;
        this.drivers_mobile = drivers_mobile;
        this.drivers_photo = drivers_photo;
        this.drivers_exp = drivers_exp;
        this.drivers_license = drivers_license;
    }

    public String getDrivers_id() {
        return drivers_id;
    }

    public String getDrivers_name() {
        return drivers_name;
    }

    public String getDrivers_address() {
        return drivers_address;
    }

    public String getDrivers_mobile() {
        return drivers_mobile;
    }

    public String getDrivers_photo() {
        return drivers_photo;
    }

    public String getDrivers_exp() {
        return drivers_exp;
    }

    public String getDrivers_license() {
        return drivers_license;
    }
}
