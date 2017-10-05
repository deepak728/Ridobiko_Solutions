package com.example.ritik.instabike;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Maintaince extends AppCompatActivity {

    ListView lv;
    MaintainceCustomListAdapter adapter;
    List<Bike> data;

    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintaince);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Maintenance");
        pg = new ProgressDialog(Maintaince.this);
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
            Toast.makeText(Maintaince.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(Maintaince.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }
    private void initViews(){

        lv=(ListView)findViewById(R.id.mlistView);
        loadJSON();
    }
    private void loadJSON() {
        pg.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ridobiko.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);

        Call<MaintenanceRetrofit> call = request.getMaintenance(Singleton.getInstance().getUser());


       call.enqueue(new Callback<MaintenanceRetrofit>() {
           @Override
           public void onResponse(Call<MaintenanceRetrofit> call, Response<MaintenanceRetrofit> response) {

                MaintenanceRetrofit jsonResponse = response.body();

               if(jsonResponse!=null)
                   data = jsonResponse.getBikes();
               if(data.size()!=0) {
                   adapter = new MaintainceCustomListAdapter(data, Maintaince.this);
                   lv.setAdapter(adapter);
               }
               pg.dismiss();
           }

           @Override
           public void onFailure(Call<MaintenanceRetrofit> call, Throwable t) {
               pg.dismiss();
           }
       });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in);

    }
}


class MaintainceCustomListAdapter extends BaseAdapter {
    private List<Bike> android;
    Context context;

    public MaintainceCustomListAdapter(List<Bike> android,Context context) {
        this.android = android;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_maintaince, parent, false);

        TextView Name = (TextView) rowView.findViewById(R.id.mitem3);
        TextView Id = (TextView) rowView.findViewById(R.id.mitem4);
        ImageView Maintanance=(ImageView) rowView.findViewById(R.id.maintanance);

        ImageView comment_maintenance=(ImageView) rowView.findViewById(R.id.comment_maintenance);

        Maintanance.setTag(position);


        if(android.size()!=0) {
            Name.setText(""+android.get(position).getBike_name());
            Id.setText(""+android.get(position).getBike_id());
        }

         comment_maintenance.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Toast.makeText(context, "main", Toast.LENGTH_SHORT).show();


                 final Dialog dialog3 = new Dialog(parent.getContext());
                 dialog3.setContentView(R.layout.comment_maintenance);
                 dialog3.setCancelable(true);
                  dialog3.show();
                 Button cancel=(Button)dialog3.findViewById(R.id.cancel_butt_reason);
                 cancel.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         dialog3.dismiss();
                     }
                 });
             }
         });

        Maintanance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int position = (Integer) view.getTag();


                final Dialog dialog1 = new Dialog(parent.getContext());
                dialog1.setContentView(R.layout.maintaince_dialog_1);
                dialog1.setCancelable(false);
                dialog1.setTitle("Maintenance");

                Button bt1= (Button) dialog1.findViewById(R.id.nextbutton5);
                Button bt2= (Button) dialog1.findViewById(R.id.nextbutton6);

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



                        final Dialog dialog2 = new Dialog(parent.getContext());
                        dialog2.setContentView(R.layout.maintaince_dialog_yes_no);
                        dialog2.setCancelable(false);
                        dialog2.setTitle("Are You Sure ?");

                        Button bt3= (Button) dialog2.findViewById(R.id.nextbutton7);
                        Button bt4= (Button) dialog2.findViewById(R.id.nextbutton8);


                        bt3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog2.dismiss();


                                if(android.get(position)!=null)

                                removeFromMaintenance(android.get(position).getBike_id());

                                Intent i = new Intent(context.getApplicationContext(),Maintaince.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);


                            }
                        });
                        bt4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                dialog2.dismiss();
                            }
                        });
                        dialog2.show();

                    }

                });
                dialog1.show();



            }
        });

        return rowView;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return android.size();
    }

    @Override
    public String getItem(int position) {
        return "";
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void removeFromMaintenance(final String bike_Id) {


        class abc extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("bike_id",""+bike_Id);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest("http://ridobiko.com/api/bikes/removefrommaintainance", params);

                try
                {
                    JSONObject jsonObject = new JSONObject(res);
                    String s = jsonObject.optString("error");

                    if(s.equalsIgnoreCase("0"))
                    {
                        return "Removed From Maintenance success";
                    }




                }catch (JSONException e){ e.printStackTrace();}
                return "Removed From Maintenance Failure";
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                    Toast.makeText(context.getApplicationContext(),s, Toast.LENGTH_SHORT).show();
}
        }
        abc ae = new abc();
        ae.execute();
    }

    }


