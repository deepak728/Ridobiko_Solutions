package com.example.ritik.instabike;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akash Chetty on 24-Mar-17.
 */

public class Detail{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vendor_email_id")
    @Expose
    private String vendorEmailId;
    @SerializedName("bike_id")
    @Expose
    private String bikeId;
    @SerializedName("bike_brand")
    @Expose
    private String bikeBrand;
    @SerializedName("bike_name")
    @Expose
    private String bikeName;
    @SerializedName("bike_plate_type")
    @Expose
    private String bikePlateType;
    @SerializedName("bike_image")
    @Expose
    private String bikeImage;
    @SerializedName("rent_per_day")
    @Expose
    private String rentPerDay;
    @SerializedName("rent_per_hour")
    @Expose
    private String rentPerHour;
    @SerializedName("insurance_renewal_date")
    @Expose
    private String insuranceRenewalDate;
    @SerializedName("periodic_service_date")
    @Expose
    private String periodicServiceDate;
    @SerializedName("service_renewal_month")
    @Expose
    private String serviceRenewalMonth;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendorEmailId() {
        return vendorEmailId;
    }

    public void setVendorEmailId(String vendorEmailId) {
        this.vendorEmailId = vendorEmailId;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getBikeBrand() {
        return bikeBrand;
    }

    public void setBikeBrand(String bikeBrand) {
        this.bikeBrand = bikeBrand;
    }

    public String getBikeName() {
        return bikeName;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public String getBikePlateType() {
        return bikePlateType;
    }

    public void setBikePlateType(String bikePlateType) {
        this.bikePlateType = bikePlateType;
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

    public String getInsuranceRenewalDate() {
        return insuranceRenewalDate;
    }

    public void setInsuranceRenewalDate(String insuranceRenewalDate) {
        this.insuranceRenewalDate = insuranceRenewalDate;
    }

    public String getPeriodicServiceDate() {
        return periodicServiceDate;
    }

    public void setPeriodicServiceDate(String periodicServiceDate) {
        this.periodicServiceDate = periodicServiceDate;
    }

    public String getServiceRenewalMonth() {
        return serviceRenewalMonth;
    }

    public void setServiceRenewalMonth(String serviceRenewalMonth) {
        this.serviceRenewalMonth = serviceRenewalMonth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
