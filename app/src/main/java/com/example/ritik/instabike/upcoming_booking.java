package com.example.ritik.instabike;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
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

public class upcoming_booking extends AppCompatActivity {
    ListView list_ub;

    ProgressDialog pg;

    static List<List<BikesId>> send;
    upcoming_book_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_booking);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Upcoming booking");
        pg = new ProgressDialog(upcoming_booking.this);
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
            Toast.makeText(upcoming_booking.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(upcoming_booking.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

    private void initViews() {

        list_ub = (ListView) findViewById(R.id.upcoming_booking_list);
        loadJSON();
    }

    private void loadJSON() {
        pg.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ridobiko.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);

        Call<List<UpcomingBookingRetrofit>> call = request.getUpcomingBooking(Singleton.getInstance().getUser());


        call.enqueue(new Callback<List<UpcomingBookingRetrofit>>() {
            @Override
            public void onResponse(Call<List<UpcomingBookingRetrofit>> call, Response<List<UpcomingBookingRetrofit>> response) {


                if(response.body().size()!=0) {
                    adapter = new upcoming_book_adapter(response.body(), upcoming_booking.this);
                    list_ub.setAdapter(adapter);
                }
                pg.dismiss();
            }

            @Override
            public void onFailure(Call<List<UpcomingBookingRetrofit>> call, Throwable t) {
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
class upcoming_book_adapter extends BaseAdapter {

    private List<UpcomingBookingRetrofit> android;

    Context context;

    public upcoming_book_adapter(List<UpcomingBookingRetrofit> android, Context context) {
        this.android = android;
        this.context = context;

    }


    @Override
    public int getCount() {
        return android.size();
    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = li.inflate(R.layout.custom_upcoming_booking, viewGroup, false);

        LinearLayout ub_Layout = (LinearLayout) row.findViewById(R.id.upcoming_booking_layout);
        TextView tcusName = (TextView) row.findViewById(R.id.cust_name_th);
        TextView tcustNo = (TextView) row.findViewById(R.id.cust_no_th);
        TextView tnoOfBikes = (TextView) row.findViewById(R.id.no_of_bikes_th);
        TextView tpickUpDate = (TextView) row.findViewById(R.id.pickUp_th);
        TextView tdropDate = (TextView) row.findViewById(R.id.dropUp_th);
        Button delete= (Button) row.findViewById(R.id.delete_upcoming_booking);
        Button issue= (Button) row.findViewById(R.id.issue_upcoming_booking);

        if(android.get(position)!=null) {
            tcusName.setText(""+android.get(position).getCustomerName());
            tcustNo.setText(""+android.get(position).getCustomerIdNumber());

            if(android.get(position).getBikesId()!=null)
                tnoOfBikes.setText(""+android.get(position).getBikesId().size());

            tpickUpDate.setText(""+android.get(position).getPickupDate());
            tdropDate.setText(""+android.get(position).getDropDate());
        }

        ub_Layout.setTag(position);
        delete.setTag(position);
        issue.setTag(position);


        ub_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = (Integer) view.getTag();
                Intent intent = new Intent(context, upcoming_booking_details.class);

                intent.putExtra("TT", android.get(position).getCity());
                intent.putExtra("PD", android.get(position).getPickupDate());
                intent.putExtra("DD", android.get(position).getDropDate());
                intent.putExtra("CUSN", android.get(position).getCustomerName());
                intent.putExtra("CONN", android.get(position).getCustomerMobile());
                intent.putExtra("EI", android.get(position).getCustomerEmailId());
                intent.putExtra("INAME", android.get(position).getCustomerIdName());
                intent.putExtra("INUM", android.get(position).getCustomerIdNumber());
                intent.putExtra("DL", android.get(position).getCustomerDlNumber());
                intent.putExtra("ADD", android.get(position).getCustomerAddress());
                upcoming_booking.send = android.get(position).getBikesId();

                context.startActivity(intent);


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = (Integer) view.getTag();

                final Dialog dialog2 = new Dialog(context);
                dialog2.setContentView(R.layout.maintaince_dialog_yes_no);
                dialog2.setTitle("Delete Trip");


                Button bt3= (Button) dialog2.findViewById(R.id.nextbutton7);
                Button bt4= (Button) dialog2.findViewById(R.id.nextbutton8);


                bt3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();



                        if(android.get(position)!=null)
                        removeFromUpcoming(android.get(position).getId(),viewGroup.getContext());

                        Intent i = new Intent(context.getApplicationContext(),MainPage.class);
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


        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = (Integer) view.getTag();

                final Dialog dialog2 = new Dialog(context);
                dialog2.setContentView(R.layout.maintaince_dialog_yes_no);
                dialog2.setTitle("Issue trip");


                Button bt3= (Button) dialog2.findViewById(R.id.nextbutton7);
                Button bt4= (Button) dialog2.findViewById(R.id.nextbutton8);


                bt3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();



                        if(android.get(position)!=null)
                            issueFromUpcoming(android.get(position).getId(),viewGroup.getContext());

                        Intent i = new Intent(context.getApplicationContext(),MainPage.class);
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

        return row;
    }
    public void removeFromUpcoming(final String Id, final Context context) {


        class abc extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("trip_id",Id);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest("http://ridobiko.com/api/Trip/upcomingdelete", params);

                try
                {
                    JSONObject jsonObject = new JSONObject(res);
                    String s = jsonObject.optString("error");

                    if(s.equalsIgnoreCase("0"))
                    {
                        return "Deleted success";
                    }

                }catch (JSONException e){ e.printStackTrace();}
                return "Deleted Failure";
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
    public void issueFromUpcoming(final String Id, final Context context) {


        class abc extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("trip_id",Id);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest("http://ridobiko.com/api/Trip/upcomingissue", params);

                try
                {
                    JSONObject jsonObject = new JSONObject(res);
                    String s = jsonObject.optString("error");

                    if(s.equalsIgnoreCase("0"))
                    {
                        return "Trip started";
                    }

                }catch (JSONException e){ e.printStackTrace();}
                return "Could not start trip";
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
