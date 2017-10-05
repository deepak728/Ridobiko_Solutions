
package com.example.ritik.instabike;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyBikes extends AppCompatActivity {

    ProgressDialog pg;
    ListView lv;
    MyBikesCustomListAdapter adapter;
    List<Detail> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bikes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("My bikes");
        pg = new ProgressDialog(MyBikes.this);
        pg.setTitle("Loading");
        pg.setMessage("Please Wait..!!");

    }
    private void initViews(Context context){

        lv=(ListView)findViewById(R.id.my_bikes_list);
        loadJSON(context);
    }

    private void loadJSON(final Context context) {
        pg.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ridobiko.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);

        Call<MyBikesRetrofit> call = request.getMyBikes(Singleton.getInstance().getUser());

        call.enqueue(new Callback<MyBikesRetrofit>() {
            @Override
            public void onResponse(Call<MyBikesRetrofit> call, Response<MyBikesRetrofit> response) {

                MyBikesRetrofit jsonResponse = response.body();

                if(jsonResponse!=null)
                    data = jsonResponse.getDetails();
                if(data.size()!=0) {
                    adapter = new MyBikesCustomListAdapter(data, context);
                    lv.setAdapter(adapter);
                }
                pg.dismiss();
            }

            @Override
            public void onFailure(Call<MyBikesRetrofit> call, Throwable t) {
                Log.d("Error", t.getMessage());
                pg.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
        return true;
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
            initViews(this);
        }
        else
        {
            Toast.makeText(MyBikes.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }
}


class MyBikesCustomListAdapter extends BaseAdapter {

    Context context;
    private List<Detail> android;


    public MyBikesCustomListAdapter(List<Detail> android,Context context) {
        this.android=android;
        this.context=context;

    }


    @Override
    public int getCount() {
        return android.size();
    }

    @Override
    public Object getItem(int i) {
        return "";
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_my_bikes, parent, false);

        TextView Name = (TextView) row.findViewById(R.id.bike_name_my_bikes);
        TextView Id = (TextView) row.findViewById(R.id.bike_plate_no_my_bikes);
        ImageView bikeImage = (ImageView) row.findViewById(R.id.imageViewBikeImage);
        ImageView Details = (ImageView) row.findViewById(R.id.bike_info_butt);
        ImageView Delete = (ImageView) row.findViewById(R.id.delete_bike_butt);


        Details.setTag(position);
        Delete.setTag(position);


if(android.get(position)!=null) {
    Name.setText(android.get(position).getBikeName());
    Id.setText(android.get(position).getBikeId());
    String url=android.get(position).getBikeImage();
    if(Patterns.WEB_URL.matcher(url).matches())
    Picasso.with(context).load(url).into(bikeImage);
    else  bikeImage.setImageResource(R.drawable.bike_image);
}




        Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int n = (Integer) view.getTag();

                Intent i=new Intent(context,BikeDetials.class);
                i.putExtra("bike_name",android.get(n).getBikeName());
                i.putExtra("bike_id",android.get(n).getBikeId());
                i.putExtra("bike_plate",android.get(n).getBikePlateType());
                i.putExtra("bike_rent_day",android.get(n).getRentPerDay());
                i.putExtra("bike_image",android.get(n).getBikeImage());
                i.putExtra("bike_rent_hour",android.get(n).getRentPerHour());
                i.putExtra("bike_name_insurance",android.get(n).getInsuranceRenewalDate());
                i.putExtra("bike_name_service_date",android.get(n).getPeriodicServiceDate());
                i.putExtra("bike_name_service_month",android.get(n).getServiceRenewalMonth());
                i.putExtra("brand_name",android.get(n).getBikeBrand());

                context.startActivity(i);

            }
        });


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = (Integer) view.getTag();
                final Dialog dialog1 = new Dialog(parent.getContext());
                dialog1.setContentView(R.layout.delete_alert_layout);
                dialog1.setCancelable(false);
                dialog1.setTitle("Delete");

                Button bt1 = (Button) dialog1.findViewById(R.id.delete_cancel);
                Button bt2 = (Button) dialog1.findViewById(R.id.delete_confirm);


                bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });
                bt2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog1.dismiss();

                        if(android.get(position)!=null)
                        DeleteFromUsers(android.get(position).getBikeId());

                        Intent i = new Intent(context.getApplicationContext(),MyBikes.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);


                    }
                });

                dialog1.show();


            }
        });
        return row;
    }

    public void DeleteFromUsers(final String bike_Id)
    {


        class abc extends AsyncTask<Void,Void,String>
        {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("bike_id",""+bike_Id);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest("http://ridobiko.com/api/bikes/deletebike", params);

                try
                {
                    JSONObject jsonObject = new JSONObject(res);
                    String s = jsonObject.optString("error");

                    if(s.equalsIgnoreCase("0"))
                    {
                        return "Bike Deleted Success";
                    }
                }catch (JSONException e){ e.printStackTrace();}
                return "Bike Deleted Failure";
            }

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                    Toast.makeText(context.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }
        }
        abc  ae = new abc();
        ae.execute();

    }
}

