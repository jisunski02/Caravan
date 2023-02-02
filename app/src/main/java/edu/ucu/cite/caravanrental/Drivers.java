package edu.ucu.cite.caravanrental;

public class Drivers {

    private String drivers_id;
    private String drivers_firstname;
    private String drivers_lastname;
    private String drivers_address;
    private String drivers_mobile;
    private String drivers_photo;
    private String drivers_exp;
    private String drivers_license;

    public Drivers(String drivers_id, String drivers_firstname, String drivers_lastname, String drivers_address, String drivers_mobile, String drivers_photo, String drivers_exp, String drivers_license) {
        this.drivers_id = drivers_id;
        this.drivers_firstname = drivers_firstname;
        this.drivers_lastname = drivers_lastname;
        this.drivers_address = drivers_address;
        this.drivers_mobile = drivers_mobile;
        this.drivers_photo = drivers_photo;
        this.drivers_exp = drivers_exp;
        this.drivers_license = drivers_license;
    }

    public String getDrivers_id() {
        return drivers_id;
    }

    public String getDrivers_firstname() {
        return drivers_firstname;
    }

    public String getDrivers_lastname() {
        return drivers_lastname;
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
