package com.example.ritik.instabike.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.ritik.instabike.Adapters.AvailableBikesAdapter;
import com.example.ritik.instabike.Adapters.SelectedBikesAdapter;
import com.example.ritik.instabike.R;
import com.example.ritik.instabike.Utility.GlobalData;

public class SelectedBikes extends AppCompatActivity {

    @Bind(R.id.selectedBikesRecyclerView)
    RecyclerView bikesListRecyclerView;

    @Bind(R.id.addBikeButton)
    ImageView addBikeButton;

    @Bind(R.id.nextButton)
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_bikes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Selected Bikes");
        ButterKnife.bind(this);
        loadBikesList();
        clickListeners();
    }

    public void loadBikesList(){
        SelectedBikesAdapter adapter = new SelectedBikesAdapter(SelectedBikes.this,SelectedBikes.this, GlobalData.selectedBikes);
        bikesListRecyclerView.setLayoutManager(new LinearLayoutManager(SelectedBikes.this));
        bikesListRecyclerView.setAdapter(adapter);
    }

    public void clickListeners(){
        addBikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedBikes.this,AvailableBikes.class);
                startActivity(intent);
                //finish();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedBikes.this,RentSummary.class);
                startActivity(intent);
               // finish();
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
