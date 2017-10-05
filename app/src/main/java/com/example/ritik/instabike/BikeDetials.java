package com.example.ritik.instabike;

import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class BikeDetials extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Bike details");
        ImageView edit_details = (ImageView) findViewById(R.id.edit_button);
        final TextView tv1= (TextView) findViewById(R.id.bike_name_details);
        final TextView tv2= (TextView) findViewById(R.id.bike_id);
        final TextView tv3= (TextView) findViewById(R.id.bike_plate_type);
        final TextView tv4= (TextView)  findViewById(R.id.bike_rent_per_day);
        final TextView tv5= (TextView) findViewById(R.id.bike_rent_per_hour);
        final TextView tv6= (TextView)  findViewById(R.id.select_insurance_renewal_date);
        final TextView tv7= (TextView) findViewById(R.id.select_service_date);
        final TextView tv8= (TextView) findViewById(R.id.service_renewal_month);
        final TextView tv9= (TextView) findViewById(R.id.brand_name);


        final ImageView imageView=(ImageView) findViewById(R.id.bike_image);
        Bundle b=getIntent().getExtras();


        tv1.setText(b.getString("bike_name"));
        tv2.setText(b.getString("bike_id"));
        tv3.setText(b.getString("bike_plate"));
        tv4.setText(b.getString("bike_rent_day"));
        tv5.setText(b.getString("bike_rent_hour"));
        tv6.setText(b.getString("bike_name_insurance"));
        tv7.setText(b.getString("bike_name_service_date"));
        tv8.setText(b.getString("bike_name_service_month"));
        tv9.setText(b.getString("brand_name"));
        String url=b.getString("bike_image");
        if(Patterns.WEB_URL.matcher(url).matches()) {
            Picasso.with(getApplicationContext()).load(url).into(imageView);

        }else
        {
            imageView.setImageResource(R.drawable.bike_image);
        }


        edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainPage.activityFlag=1;
                Intent i=new Intent(BikeDetials.this,add_bikes.class);
                i.putExtra("bike_name",tv1.getText().toString());
                i.putExtra("bike_id",tv2.getText().toString());
                i.putExtra("bike_plate",tv3.getText().toString());
                i.putExtra("bike_rent_day",tv4.getText().toString());
                i.putExtra("bike_rent_hour",tv5.getText().toString());
                i.putExtra("bike_name_insurance",tv6.getText().toString());
                i.putExtra("bike_name_service_date",tv7.getText().toString());
                i.putExtra("bike_name_service_month",tv8.getText().toString());
                i.putExtra("brand_name",tv9.getText().toString());
                startActivity(i);
            }
        });



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
        return true;
    }
}
