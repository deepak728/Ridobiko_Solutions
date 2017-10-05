package com.example.ritik.instabike.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.ritik.instabike.Adapters.AvailableBikesAdapter;
import com.example.ritik.instabike.Models.BikeDetail;
import com.example.ritik.instabike.R;
import com.example.ritik.instabike.Models.Bike;
import com.example.ritik.instabike.Utility.GlobalData;

public class AvailableBikes extends AppCompatActivity {

    @Bind(R.id.bikesListRecyclerView)
    RecyclerView bikesListRecyclerView;

    @Bind(R.id.nextButton)
    Button nextButton;

    @Bind(R.id.pickUpDateText)
    TextView pickUpDateText;

    @Bind(R.id.dropDateText)
    TextView dropDateText;

    @Bind(R.id.cityText)
    TextView cityText;

    @Bind(R.id.editTripButton)
    ImageView editTripButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_bikes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Available bikes");
        ButterKnife.bind(this);
        getCityTime();
        loadBikesList();
        clickListeners();
    }

    public void getCityTime(){
        pickUpDateText.setText(GlobalData.pickUpdate);
        dropDateText.setText(GlobalData.dropdate);
        cityText.setText(GlobalData.citiesList.get(GlobalData.cityId));
    }

    public void loadBikesList(){
        List<BikeDetail> bikeList = GlobalData.availableBikes;
        AvailableBikesAdapter adapter = new AvailableBikesAdapter(AvailableBikes.this,bikeList);
        bikesListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bikesListRecyclerView.setAdapter(adapter);
    }

    public void clickListeners(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvailableBikes.this,SelectedBikes.class);
                startActivity(intent);
               // finish();
            }
        });

        editTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvailableBikes.this,CreateTrip.class);
                startActivity(intent);
              //  finish();
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
