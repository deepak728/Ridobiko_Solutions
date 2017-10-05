
package com.example.ritik.instabike.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTripResponse {

    @SerializedName("trip_id")
    @Expose
    private Integer tripId;
    @SerializedName("error")
    @Expose
    private String error;

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
