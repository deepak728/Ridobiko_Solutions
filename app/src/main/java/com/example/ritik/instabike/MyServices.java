package com.example.ritik.instabike;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Akash Chetty on 29-Mar-17.
 */

public class MyServices extends Service{
    static boolean bikesReturingToday=true;


    ConnectivityManager cm;
    NetworkInfo activeNetwork;
    boolean isConnected;

    NotificationCompat.Builder ntf;
    private static final int unique=13121;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if(isConnected)
        {
            initViews();
        }


    }
    private void initViews(){

        loadJSON();
    }


    private void loadJSON() {

        SharedPreferences sharedPreferences = MyServices.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Singleton.getInstance().setUser(sharedPreferences.getString(Config.EMAIL_SHARED_PREF, ""));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ridobiko.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);

        Call<MainPageRetrofit> call = request.getMainPage(Singleton.getInstance().getUser());


        call.enqueue(new Callback<MainPageRetrofit>() {
            @Override
            public void onResponse(Call<MainPageRetrofit> call, Response<MainPageRetrofit> response) {


                MainPageRetrofit jsonResponse = response.body();
                if(jsonResponse!=null) {
                    Bikes bikes = jsonResponse.getBikes();

                    if(jsonResponse.getError().equalsIgnoreCase("0")&&bikes!=null)
                    {

                        if(bikes.getBikesReturningToday()!=null&&bikesReturingToday)
                        {


                            List<BikesReturningToday> bT=bikes.getBikesReturningToday();


                            ntf.setSmallIcon(android.R.drawable.ic_delete);
                            ntf.setTicker("RidoBiko");
                            ntf.setWhen(System.currentTimeMillis());
                            ntf.setContentText(""+bT.size());
                            ntf.setContentTitle("Bikes Returning Today");
                            Intent i=new Intent(MyServices.this,Splash.class);

                            PendingIntent pdin=PendingIntent.getActivity(MyServices.this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
                            ntf.setContentIntent(pdin);

                            NotificationManager nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(unique,ntf.build());

                            bikesReturingToday=false;

                        }


//
//                        if(bikes.getOnTrip()!=null)
//                            bikes_on_trip.setText(""+bikes.getOnTrip().size());
//                        if(bikes.getUnderMaintenance()!=null)
//                            maintenance_bikes_no.setText(""+bikes.getUnderMaintenance().size());
//
//                        if(bikes.getTotalNumberOfBikes()!=null)
//                        {
//                            List<Detail> lt=bikes.getTotalNumberOfBikes().getDetails();
//                            if(lt!=null)
//                                total_bikes.setText(""+lt.size());
//
//                        }
//
//
//                        if(bikes.getBikesReturningToday()!=null)
//                            bikes_returing_today.setText(""+bikes.getBikesReturningToday().size());

                    }
                }
            }

            @Override
            public void onFailure(Call<MainPageRetrofit> call, Throwable t) {

            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();

        cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        ntf=new NotificationCompat.Builder(MyServices.this);
        ntf.setAutoCancel(true);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
