package com.example.ritik.instabike;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class order_gps extends AppCompatActivity {
         String no_of_gps_d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_gps_device);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("GPS Device");

        final EditText no_of_gps_device=(EditText)findViewById(R.id.no_of_gps_device);
        Button order_now =(Button)findViewById(R.id.order_now);
        final TextView total_cost=(TextView)findViewById(R.id.total_cost);
        no_of_gps_device.setText(""+"1");
        total_cost.setText(""+"₹ 4000");



        no_of_gps_device.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                no_of_gps_d=no_of_gps_device.getText().toString();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }


            @Override
            public void afterTextChanged(Editable s) {
               int myNum =0;
                try {
                    myNum = Integer.parseInt(no_of_gps_device.getText().toString());
                } catch(NumberFormatException nfe) {
                    Log.d("error",nfe.toString());
                }
                 total_cost.setText(""+"₹ "+(myNum*4000));

            }
        });

      order_now.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              no_of_gps_d=no_of_gps_device.getText().toString();

              if(!(no_of_gps_d.isEmpty()||no_of_gps_d==null)){
                  final Dialog dialog1;
                  dialog1=new Dialog(order_gps.this);
                  dialog1.setContentView(R.layout.custom_order_now_dialog);
                  dialog1.setCancelable(false);
                  dialog1.setTitle("order now");
                  dialog1.show();

                  Button cancel_order_butt=(Button)dialog1.findViewById(R.id.cancel_order);
                  Button confirm_order_butt=(Button)dialog1.findViewById(R.id.order_confirm);

                  cancel_order_butt.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          dialog1.dismiss();
                      }
                  });

                  confirm_order_butt.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          dialog1.dismiss();
                          final Dialog dialog2;
                          dialog2=new Dialog(order_gps.this);
                          dialog2.setContentView(R.layout.custom_order_confirmed);
                          dialog2.setCancelable(true);
                          dialog2.setTitle("order confirm");
                          dialog2.show();

                          TextView text=(TextView)dialog2.findViewById(R.id.no_of_gpsd);
                          text.setText(""+"No of GPS device : "+no_of_gps_d);
                      }
                  });
              }
              else{
                  Toast.makeText(order_gps.this,"Please fill no of GPS device",Toast.LENGTH_SHORT).show();
              }

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
