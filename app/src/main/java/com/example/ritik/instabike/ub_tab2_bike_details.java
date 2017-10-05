package com.example.ritik.instabike;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ub_tab2_bike_details extends Fragment {
    ListView lv;
    String url;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ub_tab2_bike_details, container, false);

        lv = (ListView) rootView.findViewById(R.id.ub_cust_bikes);
        if(upcoming_booking.send.size()!=0)
        lv.setAdapter(new ub_bike_adapter(container.getContext(), upcoming_booking.send));


        return rootView;
    }


    class ub_bike_adapter extends BaseAdapter {
        List<List<BikesId>> lists;

        Context con;

        public ub_bike_adapter(Context con, List<List<BikesId>> lists) {
            this.con = con;
            this.lists = lists;


        }


        @Override
        public int getCount() {
            return lists.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater li = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = li.inflate(R.layout.custon_ub_bike, viewGroup, false);

            ImageView bike_image = (ImageView) row.findViewById(R.id.imageViewBikeImage_ub);
            TextView bike_name = (TextView) row.findViewById(R.id.bike_name_ub);
            TextView bike_plate_no = (TextView) row.findViewById(R.id.bike_plate_no_ub);
            TextView bike_rent_per_day = (TextView) row.findViewById(R.id.ub_rent_pd);
            TextView bike_rent_per_hour = (TextView) row.findViewById(R.id.ub_rent_ph);

            List<BikesId> t = lists.get(i);

            if(t.size()!=0) {
                url=t.get(0).getBikeImage();


                if(Patterns.WEB_URL.matcher(url).matches()) {
                    Picasso.with(con).load(url).into(bike_image);
                    Toast.makeText(con, "Image Loaded success", Toast.LENGTH_SHORT).show();

                }else
                {
                    bike_image.setImageResource(R.drawable.bike_image);

                }

                bike_name.setText(t.get(0).getBikeName());
                bike_plate_no.setText(t.get(0).getBikeId());
                bike_rent_per_day.setText(t.get(0).getRentPerDay());
                bike_rent_per_hour.setText(t.get(0).getRentPerHour());
            }

            return row;
        }
    }
}



