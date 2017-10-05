package com.example.ritik.instabike;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akash Chetty on 27-Mar-17.
 */

public class MainPageRetrofit {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("bikes")
    @Expose
    private Bikes bikes;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Bikes getBikes() {
        return bikes;
    }

    public void setBikes(Bikes bikes) {
        this.bikes = bikes;
    }


}
