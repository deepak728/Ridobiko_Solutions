package com.example.ritik.instabike;

/**
 * Created by Akash Chetty on 23-Mar-17.
 */

import com.example.ritik.instabike.Models.BikeDetails;
import com.example.ritik.instabike.Models.CreateTripResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RequestInterface {

    @FormUrlEncoded
    @POST("bikes/availablebikesintimeperiod")
    Call<BikeDetails> getAvailableBikesInGivenTimePeriod(@Field("vendor_email_id") String email, @Field("pickup_date")String pickup_date,
                                                         @Field("drop_date") String drop_date);

    @FormUrlEncoded
    @POST("trip/addtrip")
    Call<CreateTripResponse> addTrip(@Field("vendor_email_id") String email, @Field("pickup_date") String pickup_date,
                                     @Field("drop_date") String drop_date,@Field("customer_email_id") String customer_email_id,
                                     @Field("customer_name") String customer_name, @Field("customer_mobile") String customer_mobile,
                                     @Field("customer_address") String customer_address, @Field("customer_id_name") String customer_id_name,
                                     @Field("customer_id_number") String customer_id_number, @Field("customer_dl_number") String customer_dl_number,
                                     @Field("city") String city,@Field("bikes_id[]") String[] bikes_id );

    @FormUrlEncoded
    @POST("bikes/maintainence")
    Call <MaintenanceRetrofit> getMaintenance(@Field("vendor_email_id") String email);

    @POST("trip/ongoingtrips")
    @FormUrlEncoded
    Call<List<BikesOnTripRetrofit>> getBikesOnTrip(@Field("vendor_email_id") String email);

    @POST("trip/upcomingtrips")
    @FormUrlEncoded
    Call<List<UpcomingBookingRetrofit>> getUpcomingBooking(@Field("vendor_email_id") String email);

    @POST("bikes/mybikes")
    @FormUrlEncoded
    Call<MyBikesRetrofit> getMyBikes(@Field("vendor_email_id") String email);

    @POST("trip/triphistory")
    @FormUrlEncoded
    Call <List<HistoryRetrofit>> getHistory(@Field("vendor_email_id") String email);

    @POST("home")
    @FormUrlEncoded
    Call <MainPageRetrofit> getMainPage(@Field("email") String email);



}


