package com.example.ritik.instabike;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ritik.instabike.Activities.CreateTrip;
import com.example.ritik.instabike.R;

public class upcoming_booking_issue extends AppCompatActivity {
        String id_type_data;
        String state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_booking_issue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Upcoming Booking");

        Button next_ub_butt=(Button)findViewById(R.id.next_ub_button) ;
        final EditText id_no_ub=(EditText)findViewById(R.id.id_number_ub);
        final EditText dl_ub =(EditText)findViewById(R.id.dl_no_ub);
        final EditText landmark_ub=(EditText)findViewById(R.id.landmark_ub);
        final EditText city_ub=(EditText)findViewById(R.id.city_ub);
        final EditText district_name_ub=(EditText)findViewById(R.id.district_ub);



        Spinner id_type_spinner = (Spinner) findViewById(R.id.id_name_spinner_ub);
        ArrayAdapter<CharSequence> id_type_spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.id_type_array, android.R.layout.simple_spinner_item);
        id_type_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_type_spinner.setAdapter(id_type_spinner_adapter);


        id_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_type_data = String.valueOf(adapterView.getItemAtPosition(i));
                //Toast.makeText(upcoming_booking_issue.this, "" + id_type_data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(upcoming_booking_issue.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });



        Spinner state_spinner = (Spinner) findViewById(R.id.state_spinner);
        ArrayAdapter<CharSequence> state_adapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        state_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state_spinner.setAdapter(state_adapter);


        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = String.valueOf(adapterView.getItemAtPosition(i));
              //  Toast.makeText(upcoming_booking_issue.this, "" + state, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(upcoming_booking_issue.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });


        next_ub_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //id_type_data;
                //state;
                String id_number_data=id_no_ub.getText().toString();
                String dl_number_data=dl_ub.getText().toString();
                String landmark_data=landmark_ub.getText().toString();
                String city_data=city_ub.getText().toString();
                String district_data=district_name_ub.getText().toString();



                Intent intent = new Intent(getApplicationContext(), rent_summary_upcoming_booking.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);

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
