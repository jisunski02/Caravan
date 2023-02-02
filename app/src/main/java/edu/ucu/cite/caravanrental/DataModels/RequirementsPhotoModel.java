package edu.ucu.cite.caravanrental.DataModels;

public class RequirementsPhotoModel {
    String id;
    String photo_name;
    String customer_id;
    String photo;
    String created_at;

    public RequirementsPhotoModel(String id, String photo_name, String customer_id, String photo, String created_at) {
        this.id = id;
        this.photo_name = photo_name;
        this.customer_id = customer_id;
        this.photo = photo;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public String getPhoto_name() {
        return photo_name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getPhoto() {
        return photo;
    }

    public String getCreated_at() {
        return created_at;
    }
}
