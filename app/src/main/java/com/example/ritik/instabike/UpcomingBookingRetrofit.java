package com.example.ritik.instabike;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Akash Chetty on 26-Mar-17.
 */

public class UpcomingBookingRetrofit {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pickup_date")
    @Expose
    private String pickupDate;
    @SerializedName("drop_date")
    @Expose
    private String dropDate;
    @SerializedName("vendor_email_id")
    @Expose
    private String vendorEmailId;
    @SerializedName("customer_email_id")
    @Expose
    private String customerEmailId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_mobile")
    @Expose
    private String customerMobile;
    @SerializedName("customer_address")
    @Expose
    private String customerAddress;
    @SerializedName("customer_id_name")
    @Expose
    private String customerIdName;
    @SerializedName("customer_id_number")
    @Expose
    private String customerIdNumber;
    @SerializedName("customer_dl_number")
    @Expose
    private String customerDlNumber;
    @SerializedName("bikes_id")
    @Expose
    private List<List<BikesId>> bikesId = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDropDate() {
        return dropDate;
    }

    public void setDropDate(String dropDate) {
        this.dropDate = dropDate;
    }

    public String getVendorEmailId() {
        return vendorEmailId;
    }

    public void setVendorEmailId(String vendorEmailId) {
        this.vendorEmailId = vendorEmailId;
    }

    public String getCustomerEmailId() {
        return customerEmailId;
    }

    public void setCustomerEmailId(String customerEmailId) {
        this.customerEmailId = customerEmailId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerIdName() {
        return customerIdName;
    }

    public void setCustomerIdName(String customerIdName) {
        this.customerIdName = customerIdName;
    }

    public String getCustomerIdNumber() {
        return customerIdNumber;
    }

    public void setCustomerIdNumber(String customerIdNumber) {
        this.customerIdNumber = customerIdNumber;
    }

    public String getCustomerDlNumber() {
        return customerDlNumber;
    }

    public void setCustomerDlNumber(String customerDlNumber) {
        this.customerDlNumber = customerDlNumber;
    }

    public List<List<BikesId>> getBikesId() {
        return bikesId;
    }

    public void setBikesId(List<List<BikesId>> bikesId) {
        this.bikesId = bikesId;
    }
}
