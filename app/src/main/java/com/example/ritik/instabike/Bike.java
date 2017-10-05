
package com.example.ritik.instabike;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bike {

    @SerializedName("bike_id")
    @Expose
    private String bike_id;
    @SerializedName("bike_name")
    @Expose
    private String bike_name;
    @SerializedName("bike_image")
    @Expose
    private String bike_image;

    public String getBike_id() {
        return bike_id;
    }

    public void setBike_id(String bike_id) {
        this.bike_id = bike_id;
    }

    public String getBike_name() {
        return bike_name;
    }

    public void setBike_name(String bike_name) {
        this.bike_name = bike_name;
    }

    public String getBike_image() {
        return bike_image;
    }

    public void setBike_image(String bike_image) {
        this.bike_image = bike_image;
    }

}
