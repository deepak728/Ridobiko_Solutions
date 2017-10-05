package com.example.ritik.instabike;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Akash Chetty on 27-Mar-17.
 */

public class Bikes {

    @SerializedName("available")
    @Expose
    private List<Available> available = null;
    @SerializedName("on trip")
    @Expose
    private List<OnTrip> onTrip = null;
    @SerializedName("under maintenance")
    @Expose
    private List<UnderMaintenance> underMaintenance = null;
    @SerializedName("bikes returning today")
    @Expose
    private List<BikesReturningToday> bikesReturningToday = null;
    @SerializedName("total number of bikes")
    @Expose
    private TotalNumberOfBikes totalNumberOfBikes;

    public List<Available> getAvailable() {
        return available;
    }

    public void setAvailable(List<Available> available) {
        this.available = available;
    }

    public List<OnTrip> getOnTrip() {
        return onTrip;
    }

    public void setOnTrip(List<OnTrip> onTrip) {
        this.onTrip = onTrip;
    }

    public List<UnderMaintenance> getUnderMaintenance() {
        return underMaintenance;
    }

    public void setUnderMaintenance(List<UnderMaintenance> underMaintenance) {
        this.underMaintenance = underMaintenance;
    }

    public List<BikesReturningToday> getBikesReturningToday() {
        return bikesReturningToday;
    }

    public void setBikesReturningToday(List<BikesReturningToday> bikesReturningToday) {
        this.bikesReturningToday = bikesReturningToday;
    }

    public TotalNumberOfBikes getTotalNumberOfBikes() {
        return totalNumberOfBikes;
    }

    public void setTotalNumberOfBikes(TotalNumberOfBikes totalNumberOfBikes) {
        this.totalNumberOfBikes = totalNumberOfBikes;
    }






}
