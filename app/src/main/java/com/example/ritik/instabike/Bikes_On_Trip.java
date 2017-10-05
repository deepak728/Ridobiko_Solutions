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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.jar.Attributes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Bikes_On_Trip extends AppCompatActivity {
    ListView lv;
    BikesOnTripCustomListAdapter adapter;
    ProgressDialog pg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikes__on__trip);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Bikes on trip");

        pg = new ProgressDialog(Bikes_On_Trip.this);
        pg.setTitle("Loading");
        pg.setMessage("Please Wait..!!");

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            initViews();
        } else {
            Toast.makeText(Bikes_On_Trip.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onResume() {

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            initViews();
        } else {
            Toast.makeText(Bikes_On_Trip.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

    public void complete() {
        finish();
    }


    private void initViews() {

        lv = (ListView) findViewById(R.id.listView);
        loadJSON();
    }

    private void loadJSON() {
        pg.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ridobiko.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<List<BikesOnTripRetrofit>> call = request.getBikesOnTrip(Singleton.getInstance().getUser());

        call.enqueue(new Callback<List<BikesOnTripRetrofit>>() {
            @Override
            public void onResponse(Call<List<BikesOnTripRetrofit>> call, Response<List<BikesOnTripRetrofit>> response) {


                List<BikesOnTripRetrofit> jsonResponse = response.body();

                List<Pair<List<BikesId>, BikesOnTripRetrofit>> hm = new ArrayList<Pair<List<BikesId>, BikesOnTripRetrofit>>();

                if (jsonResponse.size() != 0) {
                    for (int i = 0; i < jsonResponse.size(); i++) {

                        if (jsonResponse.get(i).getBikesId().size() != 0) {
                            for (int j = 0; j < jsonResponse.get(i).getBikesId().size(); j++) {
                                hm.add(Pair.create(jsonResponse.get(i).getBikesId().get(j), jsonResponse.get(i)));

                            }
                        }
                    }

                    if (hm.size() != 0) {

                        adapter = new BikesOnTripCustomListAdapter(hm, Bikes_On_Trip.this);
                        lv.setAdapter(adapter);
                    }
                }

                pg.dismiss();
            }

            @Override
            public void onFailure(Call<List<BikesOnTripRetrofit>> call, Throwable t) {
                Log.d("Error", t.getMessage());
                pg.dismiss();
            }

        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in);

    }

    public void reason_dialog() {
        try {

            final AlertDialog.Builder aBuilder = new AlertDialog.Builder(Bikes_On_Trip.this);
            View aView = getLayoutInflater().inflate(R.layout.reason_layout_bikes_ontrip, null);

            aBuilder.setView(aView);
            final AlertDialog adialoge = aBuilder.create();
            adialoge.show();
            adialoge.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            CheckBox cb1=(CheckBox)aView.findViewById(R.id.checkBox1);
            CheckBox cb2=(CheckBox)aView.findViewById(R.id.checkBox2);
            CheckBox cb3=(CheckBox)aView.findViewById(R.id.checkBox3);
            CheckBox cb4=(CheckBox)aView.findViewById(R.id.checkBox4);
            CheckBox cb5=(CheckBox)aView.findViewById(R.id.checkBox5);
            CheckBox cb6=(CheckBox)aView.findViewById(R.id.checkBox6);

            EditText comment=(EditText)aView.findViewById(R.id.comment);
             Button submit=(Button)aView.findViewById(R.id.submit_butt_reason);

            String comment_string=comment.getText().toString();


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //comment-string
                    Toast.makeText(Bikes_On_Trip.this, "Moved to maintenance", Toast.LENGTH_SHORT).show();
                    adialoge.dismiss();
                }
            });







        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class BikesOnTripCustomListAdapter extends BaseAdapter {

        List<Pair<List<BikesId>, BikesOnTripRetrofit>> hm;
        Context context;


        public BikesOnTripCustomListAdapter(List<Pair<List<BikesId>, BikesOnTripRetrofit>> hm, Context context) {

            this.context = context;
            this.hm = hm;

        }


        @Override
        public int getCount() {
            return hm.size();
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
        public View getView(final int position, final View convertView, final ViewGroup parent) {


            View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_bikes_on_trip, parent, false);


            TextView tBike_Name = (TextView) rowView.findViewById(R.id.item1);
            TextView tBike_Id = (TextView) rowView.findViewById(R.id.item2);
            TextView tName = (TextView) rowView.findViewById(R.id.item3);
            TextView tId = (TextView) rowView.findViewById(R.id.item4);
            Button Available = (Button) rowView.findViewById(R.id.available);
            Button Maintaince = (Button) rowView.findViewById(R.id.maintenance);

            Maintaince.setTag(position);
            Available.setTag(position);


            if (hm.get(position).first.size() != 0 && hm.get(position).first.get(0) != null) {
                tBike_Name.setText("" + hm.get(position).first.get(0).getBikeName());
                tBike_Id.setText("" + hm.get(position).first.get(0).getBikeId());
            }
            if (hm.get(position).second != null) {
                tName.setText("" + hm.get(position).second.getCustomerName());
                tId.setText("" + hm.get(position).second.getCustomerMobile());
            }

            Available.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int n = (Integer) view.getTag();

                    final Dialog dialog1;
                    dialog1 = new Dialog(parent.getContext());
                    dialog1.setContentView(R.layout.available_dialog);
                    dialog1.setTitle("Make it Available");

                    Button bt1 = (Button) dialog1.findViewById(R.id.nextbutton5);
                    Button bt2 = (Button) dialog1.findViewById(R.id.nextbutton6);


                    bt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog1.dismiss();
                            if (hm.get(position).first.size() != 0 && hm.get(position).first.get(0) != null)
                                Availabledata("" + hm.get(n).first.get(0).getBikeId(), "" + hm.get(n).second.getId(), context);

                            Intent i = new Intent(context.getApplicationContext(), MainPage.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                            parent.getContext().startActivity(i);


                        }
                    });
                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog1.dismiss();
                        }
                    });

                    dialog1.show();


                }
            });

            Maintaince.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final int n = (Integer) view.getTag();


                    reason_dialog();


//                final Dialog dialog1;
//                dialog1 = new Dialog(parent.getContext());
//                dialog1.setContentView(R.layout.maintaince_dialog);
//                dialog1.setTitle("Move in Maintenance");
//
//
//                Button bt1 = (Button) dialog1.findViewById(R.id.nextbutton7);
//
//
//                bt1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog1.dismiss();
//                        if(hm.get(position).first.size()!=0&&hm.get(position).first.get(0)!=null)
//                        Maintenancedata(""+hm.get(n).first.get(0).getBikeId(),""+hm.get(n).second.getId(), parent.getContext());
//
//                        Intent i = new Intent(context.getApplicationContext(),MainPage.class);
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
//                        parent.getContext().startActivity(i);
//                        //parent.getContext().startActivity(i);
//
//
//                    }
//                });
//
//
//                dialog1.show();
//

                }
            });


            return rowView;

        }

        public void Maintenancedata(final String Bike_Id, final String Id, final Context context) {

            class abc extends AsyncTask<Void, Void, String> {

                @Override
                protected String doInBackground(Void... v) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("bike_id", "" + Bike_Id);
                    params.put("trip_id", "" + Id);


                    RequestHandler rh = new RequestHandler();
                    String res = rh.sendPostRequest("http://ridobiko.com/api/bikes/moveinmaintenance", params);

                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        String s = jsonObject.optString("error");

                        if (s.equalsIgnoreCase("0")) {
                            return "success";
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return "Failure";


                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(String s) {

                    super.onPostExecute(s);
                    if (s.equalsIgnoreCase("success")) {
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                        new Bikes_On_Trip().complete();
                    } else {
                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();

                    }

                }
            }
            abc ae = new abc();
            ae.execute();


        }

        public void Availabledata(final String Bike_Id, final String Id, final Context context) {


            class abc extends AsyncTask<Void, Void, String> {

                @Override
                protected String doInBackground(Void... v) {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("bike_id", "" + Bike_Id);
                    params.put("trip_id", "" + Id);

                    RequestHandler rh = new RequestHandler();
                    String res = rh.sendPostRequest("http://ridobiko.com/api/bikes/makeavailable", params);
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        String s = jsonObject.optString("error");

                        if (s.equalsIgnoreCase("0")) {
                            return "success";
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return "Failure";

                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (s.equalsIgnoreCase("success")) {
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                        new Bikes_On_Trip().complete();
                    } else {
                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();

                    }

                }
            }
            abc ae = new abc();
            ae.execute();
        }

    }
}
