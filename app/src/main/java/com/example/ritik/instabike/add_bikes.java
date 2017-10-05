package com.example.ritik.instabike;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class add_bikes extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    String bike_names, bike_plate_no, bike_plate_type_data;
    String rent_per_day, rent_per_hour, service_renewal_month_data,bike_brand;
    Button add_bike_butt;
    String in_date,se_date;
    ImageView bike_image;
    TextView select_insurance_renewal_date,select_service_date_field;
    private  int flag =0;
    LinearLayout bike_brand_field_layout, bike_name_field_layout;
    EditText bike_brand_manually,bike_name_manually;
    EditText bike_plate_no_field;
     EditText rent_per_day_field;
    EditText rent_per_hour_field;
    ProgressDialog pg;
    TextView title;
    int spinnerPosition;
    String image;

    Spinner spinner_plate_type;
    ArrayAdapter<CharSequence> plate_type_adapter;
    Spinner spinner_service_renewal_month;
    ArrayAdapter<CharSequence> service_renewal_month_adapter;

    private int mFlag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bikes);

        pg = new ProgressDialog(add_bikes.this);
        pg.setTitle("Loading");
        pg.setMessage("Please Wait..!!");


        title= (TextView) findViewById(R.id.textView);

         select_insurance_renewal_date = (TextView) findViewById(R.id.select_insurance_renewal_date1);
         select_service_date_field = (TextView) findViewById(R.id.select_service_date1);
         bike_plate_no_field = (EditText) findViewById(R.id.bike_plate_no);
         rent_per_day_field = (EditText) findViewById(R.id.bike_rent_per_day1);
        rent_per_hour_field = (EditText) findViewById(R.id.bike_rent_per_hour1);
        add_bike_butt = (Button) findViewById(R.id.add_bike_button);
        bike_brand_manually=(EditText)findViewById(R.id.bike_brand_manually);
        bike_name_manually=(EditText)findViewById(R.id.bike_name_manually);
        bike_brand_field_layout=(LinearLayout)findViewById(R.id.bike_brand_field_layout1);
        bike_name_field_layout=(LinearLayout)findViewById(R.id.bike_name_field_layout);
        bike_image= (ImageView) findViewById(R.id.bike_image1);



        spinner_plate_type = (Spinner) findViewById(R.id.bike_plate_type_spinner);
        plate_type_adapter = ArrayAdapter.createFromResource(this,
                R.array.bike_plate_type_resource, android.R.layout.simple_spinner_item);
        plate_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_plate_type.setAdapter(plate_type_adapter);

        spinner_plate_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bike_plate_type_data = String.valueOf(adapterView.getItemAtPosition(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(add_bikes.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });


        spinner_service_renewal_month = (Spinner) findViewById(R.id.service_renewal_month_spinner);
        service_renewal_month_adapter = ArrayAdapter.createFromResource(this,
                R.array.service_renewal_mon, android.R.layout.simple_spinner_item);
        service_renewal_month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_service_renewal_month.setAdapter(service_renewal_month_adapter);


        spinner_service_renewal_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                service_renewal_month_data = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(add_bikes.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });


        Spinner spinner_bike_brand = (Spinner) findViewById(R.id.bike_brand_spinner);
        ArrayAdapter<CharSequence> spinner_bike_brand_adapter = ArrayAdapter.createFromResource(this,
                R.array.bikeBrandArray, android.R.layout.simple_spinner_item);
        spinner_bike_brand_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_bike_brand.setAdapter(spinner_bike_brand_adapter);


        spinner_bike_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bike_brand = String.valueOf(adapterView.getItemAtPosition(i));
                if(i==16){
                    mFlag=1;
                    bike_brand_field_layout.setVisibility(View.GONE);
                    bike_brand_manually.setVisibility(View.VISIBLE);

                    bike_name_field_layout.setVisibility(View.GONE);
                    bike_name_manually.setVisibility(View.VISIBLE);

                    bike_image.setImageResource(R.drawable.bike_image);
                    image="";
                }
                 else{
                    check_bike_of_this_brand(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(add_bikes.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });


        select_insurance_renewal_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd =
                        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        add_bikes.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                   flag=0;
                dpd.show(getFragmentManager(), "InsuranceRenewalDate");
            }
        });

        select_service_date_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        add_bikes.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                   flag=1;
                dpd.show(getFragmentManager(), "ServiceRenewalDate");
            }
        });


        if(MainPage.activityFlag==1)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            setTitle("Edit Bike");

            MainPage.activityFlag=0;
            title.setText("Edit Bike");
            add_bike_butt.setText("EDIT BIKE");

            Bundle b=getIntent().getExtras();


            bike_plate_no_field.setText(b.getString("bike_id"));
            rent_per_day_field.setText(b.getString("bike_rent_day"));
            rent_per_hour_field.setText(b.getString("bike_rent_hour"));
            select_insurance_renewal_date.setText(b.getString("bike_name_insurance"));
            select_service_date_field.setText(b.getString("bike_name_service_date"));

            in_date=b.getString("bike_name_insurance");
            se_date=b.getString("bike_name_service_date");
            spinnerPosition = plate_type_adapter.getPosition("bike_plate");
            spinner_plate_type.setSelection(spinnerPosition);

            spinnerPosition=service_renewal_month_adapter.getPosition(b.getString("bike_name_service_month"));
            spinner_service_renewal_month.setSelection(spinnerPosition);




            add_bike_butt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mFlag == 1) {
                        bike_brand = bike_brand_manually.getText().toString();
                        bike_names = bike_name_manually.getText().toString();
                    }
                    if (mFlag == 2) {
                        bike_names = bike_name_manually.getText().toString();
                    }

                    bike_plate_no = bike_plate_no_field.getText().toString();
                    rent_per_day = rent_per_day_field.getText().toString();
                    rent_per_hour = rent_per_hour_field.getText().toString();



                    if (
                            checkDataEntry(bike_brand, bike_names, bike_plate_no, rent_per_hour, rent_per_day, in_date, se_date,
                                    bike_plate_type_data, service_renewal_month_data) == true)
                    {


                        EditDetails();
                        //Toast.makeText(add_bikes.this, ""+rent_per_hour, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getApplicationContext(),MyBikes.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();

                    }
                }
            });

        }
        else {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            setTitle("Add Bike");

            title.setText("Add Bike");
            add_bike_butt.setText("ADD BIKE");

            add_bike_butt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     if (mFlag == 1) {
                        bike_brand = bike_brand_manually.getText().toString();
                        bike_names = bike_name_manually.getText().toString();
                    }
                    if (mFlag == 2) {
                        bike_names = bike_name_manually.getText().toString();
                    }

                    bike_plate_no = bike_plate_no_field.getText().toString();
                    rent_per_day = rent_per_day_field.getText().toString();
                    rent_per_hour = rent_per_hour_field.getText().toString();

                    if (
                            checkDataEntry(bike_brand, bike_names, bike_plate_no, rent_per_hour, rent_per_day, in_date, se_date,
                                    bike_plate_type_data, service_renewal_month_data) == true) {

                        AddDetails();

                        Intent i = new Intent(getApplicationContext(),MainPage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }
            });

        }
    }


    public void AddDetails()
    {


        class abc extends AsyncTask<Void,Void,String>
        {

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("vendor_email_id",""+Singleton.getInstance().getUser());
                params.put("bike_id",""+bike_plate_no);
                params.put("bike_brand",""+bike_brand);
                params.put("bike_name",""+bike_names);
                params.put("bike_plate_type",""+bike_plate_type_data);
                params.put("rent_per_day",""+rent_per_day);
                params.put("rent_per_hour",""+rent_per_hour);
                params.put("bike_image",""+image);
                params.put("service_renewal_month",""+service_renewal_month_data);
                params.put("periodic_service_date",""+se_date);
                params.put("insurance_renewal_date",""+in_date);


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest("http://ridobiko.com/api/bikes/addbikes",params);

                try
                {
                    JSONObject jsonObject = new JSONObject(res);
                    String s = jsonObject.optString("error");

                    if(s.equalsIgnoreCase("0"))
                    {
                        return "success";

                    }
                }catch (JSONException e){ e.printStackTrace();}



                return "failure";
            }

            @Override
            protected void onPreExecute() {
               // pg.show();

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
//                pg.dismiss();

                if(s.equalsIgnoreCase("success"))
                {
                    Toast.makeText(add_bikes.this, s, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(add_bikes.this, s, Toast.LENGTH_SHORT).show();

                }

                super.onPostExecute(s);
            }
        }
        abc  ae = new abc();
        ae.execute();


    }
    public void EditDetails()
    {


        class abc extends AsyncTask<Void,Void,String>
        {

            @Override
            protected String doInBackground(Void... v) {


                HashMap<String,String> params = new HashMap<>();
                params.put("vendor_email_id",""+Singleton.getInstance().getUser());
                params.put("bike_id",""+bike_plate_no);
                params.put("bike_brand",""+bike_brand);
                params.put("bike_name",""+bike_names);
                params.put("bike_plate_type",""+bike_plate_type_data);
                params.put("rent_per_day",""+rent_per_day);
                params.put("rent_per_hour",""+rent_per_hour);
                params.put("bike_image",""+image);
                params.put("service_renewal_month",""+service_renewal_month_data);
                params.put("periodic_service_date",""+se_date);
                params.put("insurance_renewal_date",""+in_date);





                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest("http://ridobiko.com/api/bikes/editbike",params);

                try
                {
                    JSONObject jsonObject = new JSONObject(res);
                    String s = jsonObject.optString("error");

                    if(s.equalsIgnoreCase("0"))
                    {
                        return "success";

                    }



                }catch (JSONException e){ e.printStackTrace();}



                return "failure";
            }

            @Override
            protected void onPreExecute() {
                //pg.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                //pg.dismiss();
                if(s.equalsIgnoreCase("success"))
                {
                    Toast.makeText(add_bikes.this, s, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(add_bikes.this, s, Toast.LENGTH_SHORT).show();

                }

                super.onPostExecute(s);
            }
        }
        abc  ae = new abc();
        ae.execute();


    }


    private void check_bike_of_this_brand(int bb) {
        final Spinner spinner_bike_name = (Spinner) findViewById(R.id.bike_name_spinner);
        ArrayAdapter<CharSequence> spinner_bike_name_adapter = null;

        switch(bb){
            case 0:
                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Kawasaki_Array, android.R.layout.simple_spinner_item);
                break;
            case 1:
                 spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Honda_Array, android.R.layout.simple_spinner_item);
                break;
            case 2:
              spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Yamaha_Array, android.R.layout.simple_spinner_item);
                break;
            case 3:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.TVS_Array, android.R.layout.simple_spinner_item);
                break;
            case 4:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Triumph_Array, android.R.layout.simple_spinner_item);
                break;
            case 5:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.KTM_Array, android.R.layout.simple_spinner_item);
                break;
            case 6:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Mahindra_Array, android.R.layout.simple_spinner_item);
                break;
            case 7:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Bajaj_Array, android.R.layout.simple_spinner_item);
                break;
            case 8:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Harley_Davidson_Array, android.R.layout.simple_spinner_item);
                break;
            case 9:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.RoyalEnfield_Array, android.R.layout.simple_spinner_item);
                break;
            case 10:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Hyosung_650_Array, android.R.layout.simple_spinner_item);
                break;
            case 11:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Suzuki_Array, android.R.layout.simple_spinner_item);
                break;
            case 12:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Hero_Array, android.R.layout.simple_spinner_item);
                break;
            case 13:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Vepsa_Array, android.R.layout.simple_spinner_item);
                break;
            case 14:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Ducati_Array, android.R.layout.simple_spinner_item);
                break;
            case 15:

                spinner_bike_name_adapter = ArrayAdapter.createFromResource(this,
                        R.array.Benelli_Array, android.R.layout.simple_spinner_item);
                break;
            default:
                break;

        }
        spinner_bike_name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_bike_name.setAdapter(spinner_bike_name_adapter);
             final int array_length=spinner_bike_name_adapter.getCount();
        spinner_bike_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bike_names = String.valueOf(adapterView.getItemAtPosition(i));
                if(i==(array_length-1)){
                    mFlag=2;
                     bike_name_field_layout.setVisibility(View.GONE);
                     bike_name_manually.setVisibility(View.VISIBLE);
                     bike_image.setImageResource(R.drawable.bikesdefault);
                    image="";
                }
                if(i!=0) {


                    setImage(bike_names, bike_brand);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(add_bikes.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }





        });

    }

    public void setImage(final String bike_name,final String bike_brand) {
        class abc extends AsyncTask<Void, Void, String> {
            String url;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("bike_brand", ""+bike_brand);
                params.put("bike_name", ""+bike_name);


                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest("http://ridobiko.com/api/bikes/bikeimage", params);

                try
                {
                    JSONObject jsonObject = new JSONObject(res);
                    String s = jsonObject.optString("error");

                    if(s.equalsIgnoreCase("0"))
                    {
                         url=jsonObject.getString("url");

                        return "success";
                    }

                }catch (JSONException e){ e.printStackTrace();}
                return "Failure";
            }

            @Override
            protected void onPreExecute() {
                pg.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pg.dismiss();

                if(s.equalsIgnoreCase("success"))
                {
                    image=url;

                    if(Patterns.WEB_URL.matcher(url).matches()) {
                        Picasso.with(getApplicationContext()).load(image).into(bike_image);
                        Toast.makeText(getApplicationContext(), "Image Loaded success", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        bike_image.setImageResource(R.drawable.bike_image);
                        image="";
                    }
                }
                else
                {
                    bike_image.setImageResource(R.drawable.bike_image);
                    image="";

                }

            }
        }
        abc ae = new abc();
        ae.execute();
    }

    private boolean checkDataEntry(String bb, String bn, String bpn, String rph,
                                   String rpd, String id, String sd, String bptd,
                                   String srmd) {

        if(!(bb==null||bb.isEmpty())&&!(bn==null||bn.isEmpty())&&!(bpn==null||bpn.isEmpty())&&!(rpd==null||rpd.isEmpty())&&!(id==null||id.isEmpty())
                &&!(sd==null||sd.isEmpty())&&!(bptd==null||bptd.isEmpty())&&!(srmd==null||srmd.isEmpty())){
            return true;
        }
        else if(!(bb==null||bb.isEmpty())&&!(bn==null||bn.isEmpty())&&!(bpn==null||bpn.isEmpty())&&!(rph==null||rph.isEmpty())&&!(id==null||id.isEmpty())
                &&!(sd==null||sd.isEmpty())&&!(bptd==null||bptd.isEmpty())&&!(srmd==null||srmd.isEmpty())){

            return true;
        }

        else{
            Toast.makeText(add_bikes.this,"Please Fill all entries",Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in);

    }


    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
      String  date = ""+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        if(flag==0){
            in_date=date;
            select_insurance_renewal_date.setText(""+date);
        }else if(flag==1){
            se_date=date;
            select_service_date_field.setText(""+date);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
        return true;
    }

}
