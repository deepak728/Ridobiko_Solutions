package com.example.ritik.instabike.Activities;

import com.example.ritik.instabike.Models.BikeDetails;
import com.example.ritik.instabike.Models.Customer;
import com.example.ritik.instabike.RequestInterface;
import com.example.ritik.instabike.Singleton;
import com.example.ritik.instabike.Utility.*;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.ritik.instabike.R;

public class CreateTrip extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    @Bind(R.id.cityList)
    Spinner spinner;
    ArrayList<String> cityArray;
    GlobalData globalData;

    @Bind(R.id.pickUpDate)
    EditText pickUpDate;
    String date;

    @Bind(R.id.dropDate)
    EditText dropDate;

    @Bind(R.id.selectBikeButton)
    Button selectBikeButton;

    @Bind(R.id.customerName)
    EditText customerName;
    @Bind(R.id.mobile)
    EditText mobile;
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.landmark)
    EditText landmark;
    @Bind(R.id.city)
    EditText city;
    @Bind(R.id.district)
    EditText district;
    @Bind(R.id.state)
    EditText state;
    @Bind(R.id.pincode)
    EditText pincode;
    @Bind(R.id.idName)
    EditText idName;
    @Bind(R.id.idNumber)
    EditText idNumber;
    @Bind(R.id.DLNumber)
    EditText DLNumber;

    private int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Create trip");
        ButterKnife.bind(this);
        loadCityList();
        reloadPreviousData();
        clickListeners();
    }

    public void reloadPreviousData(){
        if(!TextUtils.isEmpty(GlobalData.pickUpdate))
            pickUpDate.setText(GlobalData.pickUpdate);
        if(!TextUtils.isEmpty(GlobalData.dropdate))
            dropDate.setText(GlobalData.dropdate);
        spinner.setSelection(GlobalData.cityId);
        if(GlobalData.customer!=null){
            if(!TextUtils.isEmpty(GlobalData.customer.getName()))
                customerName.setText(GlobalData.customer.getName());
            if(!TextUtils.isEmpty(GlobalData.customer.getMobile()))
                mobile.setText(GlobalData.customer.getMobile());
            if(!TextUtils.isEmpty(GlobalData.customer.getEmailId()))
                email.setText(GlobalData.customer.getEmailId());
            if(!TextUtils.isEmpty(GlobalData.customer.getLandmark()))
                landmark.setText(GlobalData.customer.getLandmark());
            if(!TextUtils.isEmpty(GlobalData.customer.getCity()))
                city.setText(GlobalData.customer.getCity());
            if(!TextUtils.isEmpty(GlobalData.customer.getDistrict()))
                district.setText(GlobalData.customer.getDistrict());
            if(!TextUtils.isEmpty(GlobalData.customer.getState()))
                state.setText(GlobalData.customer.getState());
            if(!TextUtils.isEmpty(GlobalData.customer.getPincode()))
                pincode.setText(GlobalData.customer.getPincode());
            if(!TextUtils.isEmpty(GlobalData.customer.getIdName()))
                idName.setText(GlobalData.customer.getIdName());
            if(!TextUtils.isEmpty(GlobalData.customer.getIdNumber()))
                idNumber.setText(GlobalData.customer.getIdNumber());
            if(!TextUtils.isEmpty(GlobalData.customer.getDLNumber()))
                DLNumber.setText(GlobalData.customer.getDLNumber());
        }
    }
    public void clickListeners(){
        pickUpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreateTrip.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                flag=0;
                dpd.show(getFragmentManager(), "PickUpDatepickerdialog");
            }
        });

        dropDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreateTrip.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                flag=1;
                dpd.show(getFragmentManager(), "DropDatepickerdialog");
            }
        });

        selectBikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cityId = spinner.getSelectedItemPosition();
                String pickUp = pickUpDate.getText().toString();
                String drop = dropDate.getText().toString();
                String customerNameS = customerName.getText().toString();
                String mobileS = mobile.getText().toString();
                String emailS = email.getText().toString();
                String landmarkS = landmark.getText().toString();
                String cityS = city.getText().toString();
               String districtS = district.getText().toString();
                String stateS = state.getText().toString();
                String pincodeS = pincode.getText().toString();
                String idNameS = idName.getText().toString();
                String idNumberS = idNumber.getText().toString();
                String DLNumberS = DLNumber.getText().toString();

                if(cityId!=0 && !TextUtils.isEmpty(pickUp) && !TextUtils.isEmpty(drop) && !TextUtils.isEmpty(customerNameS)
                        && !TextUtils.isEmpty(mobileS) && !TextUtils.isEmpty(emailS) && !TextUtils.isEmpty(landmarkS)
                        && !TextUtils.isEmpty(cityS) && !TextUtils.isEmpty(stateS) &&
                        !TextUtils.isEmpty(pincodeS) && !TextUtils.isEmpty(idNameS) && !TextUtils.isEmpty(idNumberS) && !TextUtils.isEmpty(DLNumberS)){

                    Customer customer = new Customer(customerNameS,mobileS, emailS,landmarkS,cityS,districtS,stateS,
                            pincodeS,idNameS,idNumberS,DLNumberS);
                    globalData = GlobalData.getInstance();
                    globalData.storeCustomerDetails(customer);

                    globalData.storeCityDate(cityId,pickUp,drop);

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://ridobiko.com/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    RequestInterface request = retrofit.create(RequestInterface.class);
                    Call<BikeDetails> call = request.getAvailableBikesInGivenTimePeriod(Singleton.getInstance().getUser(),
                            GlobalData.pickUpdate,GlobalData.dropdate);

                    call.enqueue(new Callback<BikeDetails>() {
                        @Override
                        public void onResponse(Call<BikeDetails> call, Response<BikeDetails> response) {
                            globalData = GlobalData.getInstance();
                            globalData.addAvailableBikes(response.body());

                            Intent intent = new Intent(CreateTrip.this,AvailableBikes.class);
                            startActivity(intent);
                          //  finish();
                        }

                        @Override
                        public void onFailure(Call<BikeDetails> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Failed:"+t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                    Toast.makeText(getApplicationContext(),"Complete all details",Toast.LENGTH_LONG).show();

            }
        });
    }

    private void loadCityList(){
        if(GlobalData.citiesList.size()==0){
            GlobalData.citiesList.add("Select City");
            GlobalData.citiesList.add("Rishikesh");
            GlobalData.citiesList.add("Chandigarh");
            GlobalData.citiesList.add("Amritsar");
            GlobalData.citiesList.add("Dehradun");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateTrip.this,R.layout.support_simple_spinner_dropdown_item,GlobalData.citiesList);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                CreateTrip.this,
                Calendar.HOUR_OF_DAY,
                Calendar.MINUTE,
                true);
        tpd.show(getFragmentManager(),"PickUpTimePickerDialog");

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        if(flag==0)
            pickUpDate.setText(date + " "+ hourOfDay + ":" +minute+":00");
        if(flag==1)
            dropDate.setText(date + " "+ hourOfDay + ":" +minute+":00");
        /**String hr,min,am_pm;
        hr = ""+hourOfDay;
        min = ""+minute;

        if(hourOfDay<12)
            am_pm= "a.m.";
        else
            am_pm = "p.m.";

        if(hr.length()==1)
            hr = "0"+hr;
        if(min.length()==1)
            min = "0"+min;

        if(flag==0)
        pickUpDate.setText(date + "   "+ hr + " : " + min + " "+am_pm);
        if(flag==1)
            dropDate.setText(date + "   "+ hr + " : " + min + " "+am_pm);**/
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
