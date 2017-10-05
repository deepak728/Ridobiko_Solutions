package com.example.ritik.instabike.Models;

/**
 * Created by Abhishek on 3/13/2017.
 */

public class Bike {
    private String name,cost;
    private int imageId;

    public Bike(String name,String cost,int imageId){
        this.name = name;
        this.cost = cost;
        this.imageId = imageId;
    }

    public String getName(){
        return name;
    }

    public String getCost(){
        return cost;
    }

    public int getImageId(){
        return imageId;
    }
}
