package com.example.ritik.instabike;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Akash Chetty on 24-Mar-17.
 */

public class MyBikesRetrofit {
    @SerializedName("vendor_email_id")
    @Expose
    private String vendorEmailId;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public String getVendorEmailId() {
        return vendorEmailId;
    }

    public void setVendorEmailId(String vendorEmailId) {
        this.vendorEmailId = vendorEmailId;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

}
