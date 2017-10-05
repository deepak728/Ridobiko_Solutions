package com.example.ritik.instabike;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class trip_history extends AppCompatActivity {
    ListView list_th;
    ProgressDialog pg;
    static List<List<BikesId>> send;
    tripHistory_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_history_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Trip history");


        pg = new ProgressDialog(trip_history.this);
        pg.setTitle("Loading");
        pg.setMessage("Please Wait..!!");

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            initViews();

        }
        else
        {
            Toast.makeText(trip_history.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }
    @Override
    protected void onResume() {

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            initViews();
        }
        else
        {
            Toast.makeText(trip_history.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

    private void initViews(){

        list_th=(ListView)findViewById(R.id.trip_history_list);
        loadJSON();
    }

    private void loadJSON() {
        pg.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ridobiko.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);

        Call<List<HistoryRetrofit>> call = request.getHistory(Singleton.getInstance().getUser());



        call.enqueue(new Callback<List<HistoryRetrofit>>() {
            @Override
            public void onResponse(Call<List<HistoryRetrofit>> call, Response<List<HistoryRetrofit>> response) {

                if(response.body().size()!=0) {
                    adapter = new tripHistory_adapter(response.body(), trip_history.this);
                    list_th.setAdapter(adapter);
                }

                pg.dismiss();

            }

            @Override
            public void onFailure(Call<List<HistoryRetrofit>> call, Throwable t) {
                pg.dismiss();
            }


        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
        return true;
    }
}

class tripHistory_adapter extends BaseAdapter{

    private List<HistoryRetrofit> android;
    Context context;


    public tripHistory_adapter(List<HistoryRetrofit> android,Context context)
    {
        this.android = android;
        this.context=context;

    }

    @Override
    public int getCount()
    {
        return android.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_trip_history, parent, false);


        LinearLayout th_Layout = (LinearLayout) row.findViewById(R.id.trip_history_layout);
        TextView cusName = (TextView) row.findViewById(R.id.cust_name_th);
        TextView custNo = (TextView) row.findViewById(R.id.cust_no_th);
        TextView noOfBikes = (TextView) row.findViewById(R.id.no_of_bikes_th);
        TextView pickUpDate = (TextView) row.findViewById(R.id.pickUp_th);
        TextView dropDate = (TextView) row.findViewById(R.id.dropUp_th);


        if(android.get(position)!=null) {
            cusName.setText(""+android.get(position).getCustomerName());
            custNo.setText(""+android.get(position).getCustomerMobile());

            if(android.get(position).getBikesId()!=null)
            noOfBikes.setText(""+android.get(position).getBikesId().size());
            pickUpDate.setText(""+android.get(position).getPickupDate());
            dropDate.setText(""+android.get(position).getDropDate());
        }
        th_Layout.setTag(position);


        th_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int position = (Integer) view.getTag();

                Intent intent = new Intent(parent.getContext(), t_history_details.class);
                intent.putExtra("TT",android.get(position).getCity() );
                intent.putExtra("PD", android.get(position).getPickupDate());
                intent.putExtra("DD", android.get(position).getDropDate());
                intent.putExtra("CUSN", android.get(position).getCustomerName());
                intent.putExtra("CONN", android.get(position).getCustomerMobile());
                intent.putExtra("EI", android.get(position).getCustomerEmailId());
                intent.putExtra("INAME",android.get(position).getCustomerIdName());
                intent.putExtra("INUM", android.get(position).getCustomerIdNumber());
                intent.putExtra("DL", android.get(position).getCustomerDlNumber());
                intent.putExtra("ADD", android.get(position).getCustomerAddress());
                trip_history.send=android.get(position).getBikesId();
                context.startActivity(intent);

            }
        });


        return row;
    }
}