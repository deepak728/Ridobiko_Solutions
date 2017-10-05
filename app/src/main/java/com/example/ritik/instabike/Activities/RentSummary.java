package com.example.ritik.instabike.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.ritik.instabike.Adapters.RentSummaryAdapter;
import com.example.ritik.instabike.LogIn;
import com.example.ritik.instabike.MainPage;
import com.example.ritik.instabike.Models.CreateTripResponse;
import com.example.ritik.instabike.Models.Customer;
import com.example.ritik.instabike.R;
import com.example.ritik.instabike.RequestInterface;
import com.example.ritik.instabike.Singleton;
import com.example.ritik.instabike.Utility.GlobalData;

public class RentSummary extends AppCompatActivity {
    @Bind(R.id.rentSummaryRecyclerView)
    RecyclerView rentSummaryRecyclerView;

    @Bind(R.id.totalCost)
    TextView totalCostText;
    @Bind(R.id.submitButton)
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_summary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Rent summary");
        ButterKnife.bind(this);
        loadRentSummary();
        onClickListener();
    }

    public void onClickListener(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] selectedBikes = new String[GlobalData.selectedBikes.size()];
                for (int i=0;i<GlobalData.selectedBikes.size();i++){
                    selectedBikes[i] = GlobalData.selectedBikes.get(i).getBikeId();
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ridobiko.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RequestInterface request = retrofit.create(RequestInterface.class);
                Call<CreateTripResponse> call = request.addTrip(Singleton.getInstance().getUser(), GlobalData.pickUpdate,
                        GlobalData.dropdate,GlobalData.customer.getEmailId(),GlobalData.customer.getName(),
                        GlobalData.customer.getMobile(),GlobalData.customer.getLandmark()+" "+GlobalData.customer.getCity()+
                " "+GlobalData.customer.getDistrict()+" "+GlobalData.customer.getState()+" "+GlobalData.customer.getPincode(),
                        GlobalData.customer.getIdName(),GlobalData.customer.getIdNumber(),GlobalData.customer.getDLNumber(),
                        GlobalData.citiesList.get(GlobalData.cityId),selectedBikes);

                call.enqueue(new Callback<CreateTripResponse>() {
                    @Override
                    public void onResponse(Call<CreateTripResponse> call, Response<CreateTripResponse> response) {
                        if (response.body().getError().equals("0")) {
                            Toast.makeText(getApplicationContext(), "Trip successfully created", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RentSummary.this, MainPage.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                           // finish();
                        }

                        else
                            Toast.makeText(getApplicationContext(),"Some error encountered",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<CreateTripResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void loadRentSummary(){
        RentSummaryAdapter adapter = new RentSummaryAdapter(RentSummary.this, GlobalData.selectedBikes);
        rentSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rentSummaryRecyclerView.setAdapter(adapter);
        int totalAmount = 0;
        for(int i=0;i<GlobalData.selectedBikes.size();i++)
            totalAmount += Integer.parseInt(GlobalData.selectedBikes.get(i).getRentPerDay());
        totalCostText.setText(""+totalAmount);

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
