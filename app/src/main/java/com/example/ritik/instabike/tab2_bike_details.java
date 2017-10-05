package com.example.ritik.instabike;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import java.util.List;


public class tab2_bike_details extends Fragment{
  ListView lv ;
    String url;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.th_tab2_bike_details, container, false);

          lv=(ListView)rootView.findViewById(R.id.th_cust_bikes);
        if(trip_history.send.size()!=0) {
            lv.setAdapter(new th_bike_adapter(container.getContext(), trip_history.send));
        }

        return rootView;
    }


    public class th_bike_adapter extends BaseAdapter{


        List<List<BikesId>> lists;

           Context con;
        public th_bike_adapter(Context con, List<List<BikesId>> lists){
            this.con=con;
            this.lists=lists;

        }


        @Override
        public int getCount() {
            return lists.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater li = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = li.inflate(R.layout.custom_th_cust_bike, viewGroup, false);

            ImageView tbike_image=(ImageView)row.findViewById(R.id.imageViewBikeImage);
            TextView tbike_name=(TextView)row.findViewById(R.id.bike_name_th);
            TextView tbike_plate_no=(TextView)row.findViewById(R.id.bike_plate_no_th);
            TextView tbike_rent_per_day=(TextView)row.findViewById(R.id.th_rent_pd);
            TextView tbike_rent_per_hour=(TextView)row.findViewById(R.id.th_rent_ph);


            List<BikesId> t= lists.get(i);
            if(t.size()!=0) {

                url=t.get(0).getBikeImage();


                if(Patterns.WEB_URL.matcher(url).matches()) {
                    Picasso.with(con).load(url).into(tbike_image);
                    Toast.makeText(con, "Image Loaded success", Toast.LENGTH_SHORT).show();

                }else
                {
                    tbike_image.setImageResource(R.drawable.bike_image);

                }
                tbike_name.setText(""+t.get(0).getBikeName());
                tbike_plate_no.setText(""+t.get(0).getBikeId());
                tbike_rent_per_day.setText(""+t.get(0).getRentPerDay());
                tbike_rent_per_hour.setText(""+t.get(0).getRentPerHour());
            }
            return row;
        }
    }


}
