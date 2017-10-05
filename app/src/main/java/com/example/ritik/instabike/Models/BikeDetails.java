
package com.example.ritik.instabike.Models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BikeDetails {

    @SerializedName("bikeDetails")
    @Expose
    private List<BikeDetail> bikeDetails = new ArrayList<BikeDetail>();

    public List<BikeDetail> getBikeDetails() {
        return bikeDetails;
    }

    public void setBikeDetails(List<BikeDetail> bikeDetails) {
        this.bikeDetails = bikeDetails;
    }

}
