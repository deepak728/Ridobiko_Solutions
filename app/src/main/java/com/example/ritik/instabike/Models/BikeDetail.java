
package com.example.ritik.instabike.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BikeDetail {

    @SerializedName("bike_id")
    @Expose
    private String bikeId;
    @SerializedName("bike_name")
    @Expose
    private String bikeName;
    @SerializedName("bike_image")
    @Expose
    private String bikeImage;
    @SerializedName("rent_per_day")
    @Expose
    private String rentPerDay;
    @SerializedName("rent_per_hour")
    @Expose
    private String rentPerHour;

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getBikeName() {
        return bikeName;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public String getBikeImage() {
        return bikeImage;
    }

    public void setBikeImage(String bikeImage) {
        this.bikeImage = bikeImage;
    }

    public String getRentPerDay() {
        return rentPerDay;
    }

    public void setRentPerDay(String rentPerDay) {
        this.rentPerDay = rentPerDay;
    }

    public String getRentPerHour() {
        return rentPerHour;
    }

    public void setRentPerHour(String rentPerHour) {
        this.rentPerHour = rentPerHour;
    }

}
