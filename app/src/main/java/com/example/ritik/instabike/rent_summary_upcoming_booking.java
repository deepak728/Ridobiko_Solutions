package com.example.ritik.instabike;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class rent_summary_upcoming_booking extends AppCompatActivity {
         ListView list_ub;
    int total_cost=0;
    TextView total_cost_field;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_summary_upcoming_booking);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Rent Summary");
        list_ub = (ListView) findViewById(R.id.rent_summary_ub);
        list_ub.setAdapter(new rent_summary_ub_adapter(this));

        total_cost_field=(TextView)findViewById(R.id.totalCost);






    }
    class single_row {
        String bike_name_rs;
        String bike_no_rs;
        String bike_rent_rs;

        public single_row(String s1, String s2, String s3) {
            this.bike_name_rs = s1;
            this.bike_no_rs = s2;
            this.bike_rent_rs = s3;
        }

    }
         class rent_summary_ub_adapter extends BaseAdapter{
            ArrayList<single_row> list;
                        Context context;

            rent_summary_ub_adapter(Context c){

                context = c;
                list = new ArrayList<single_row>();
                Resources res = context.getResources();
//                String[] bike_name_rs_ub = res.getStringArray(R.array.bikeName);
//                String[] bike_no_rs_ub = res.getStringArray(R.array.bikePlateNo);
//                String[] bike_rent_rs_ub = res.getStringArray(R.array.bikeRent);
//
//                for (int i = 0; i < 9; i++) {
//                    list.add(new single_row(bike_name_rs_ub[i], bike_no_rs_ub[i], bike_rent_rs_ub[i]));
//                    total_cost+=Integer.parseInt(bike_rent_rs_ub[i]);
//
//            }
        }


            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int i) {
                return list.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row = li.inflate(R.layout.bike_summary_ub, viewGroup, false);

                TextView bike_name_rs=(TextView)row.findViewById(R.id.bike_name_rs_ub);
                TextView bike_no_rs=(TextView)row.findViewById(R.id.bike_no_rs_ub);
                TextView bike_rent_rs=(TextView)row.findViewById(R.id.bike_rent_rs__ub);

                single_row temp=list.get(i);
                bike_name_rs.setText(temp.bike_name_rs);
                bike_no_rs.setText(temp.bike_no_rs);
                bike_rent_rs.setText("₹ "+temp.bike_rent_rs);


                 total_cost_field.setText(""+"₹ "+total_cost);




                return row;
            }
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