package com.example.ritik.instabike.Utility;

import java.util.ArrayList;
import java.util.List;

import com.example.ritik.instabike.Models.Bike;
import com.example.ritik.instabike.Models.BikeDetail;
import com.example.ritik.instabike.Models.BikeDetails;
import com.example.ritik.instabike.Models.Customer;

/**
 * Created by Abhishek on 3/13/2017.
 */

public class GlobalData {
    public static int cityId=0;
    public static String pickUpdate=null;
    public static String dropdate=null;
    private static final GlobalData ourInstance = new GlobalData();

    public static ArrayList<BikeDetail> selectedBikes;
    public static List<BikeDetail> availableBikes;
    public static ArrayList<String> citiesList;
    public static Customer customer;

    private GlobalData(){
        selectedBikes = new ArrayList<>();
        citiesList = new ArrayList<>();
    }

    public static GlobalData getInstance() {
        return ourInstance;
    }

    public void storeCityDate(int cityId,String pickUpdate,String dropdate) {
        this.cityId=cityId;
        this.pickUpdate=pickUpdate;
        this.dropdate=dropdate;
    }

    public void storeCustomerDetails(Customer customer){
        this.customer = customer;
    }
    public void addCityName(String cityName){
        citiesList.add(cityName);
    }
    public void addBikes(BikeDetail bike){
        this.selectedBikes.add(bike);
    }
    public void addAvailableBikes(BikeDetails bike){
        availableBikes = bike.getBikeDetails();
    }

    public void removeBike(String cancelledBikeName){
        for(int i=0;i<selectedBikes.size();i++){
            if(selectedBikes.get(i).getBikeName().equals(cancelledBikeName)){
                selectedBikes.remove(i);
                break;
            }
        }
    }
}
