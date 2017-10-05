
package com.example.ritik.instabike;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MaintenanceRetrofit {

    @SerializedName("vendor_email_id")
    @Expose
    private String vendor_email_id;
    @SerializedName("bikes")
    @Expose
    private List<Bike> bikes = null;

    public String getVendor_email_id() {
        return vendor_email_id;
    }

    public void setVendor_email_id(String vendor_email_id) {
        this.vendor_email_id = vendor_email_id;
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(List<Bike> bikes) {
        this.bikes = bikes;
    }

}
