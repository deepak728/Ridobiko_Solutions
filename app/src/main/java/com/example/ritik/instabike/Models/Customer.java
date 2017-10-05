package com.example.ritik.instabike.Models;

/**
 * Created by Abhishek on 3/14/2017.
 */

public class Customer {
    String name, mobile, emailId,landmark,city,district,state,pincode,idName,idNumber,DLNumber;

    public Customer(String name, String mobile, String emailId, String landmark, String city,
                    String district, String state, String pincode, String idName, String idNumber, String DLNumberS) {
        this.name = name;
        this.mobile = mobile;
        this.emailId = emailId;
        this.landmark = landmark;
        this.city = city;
       this.district = district;
        this.state = state;
        this.pincode = pincode;
        this.idName = idName;
        this.idNumber = idNumber;
        this.DLNumber = DLNumber;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public String getIdName() {
        return idName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getDLNumber() {
        return DLNumber;
    }
}
