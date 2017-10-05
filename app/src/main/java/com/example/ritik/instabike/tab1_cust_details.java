package com.example.ritik.instabike;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class tab1_cust_details extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.th_tab1_cust_det, container, false);

        TextView trip_to=(TextView)rootView.findViewById(R.id.trip_to_th);
        TextView pick_up_date=(TextView)rootView.findViewById(R.id.pick_up_th);
        TextView drop_up_date=(TextView)rootView.findViewById(R.id.drop_up_th);
        TextView cust_name_th=(TextView)rootView.findViewById(R.id.cust_name_th);
        TextView con_no=(TextView)rootView.findViewById(R.id.contact_no_th);
        TextView email_id=(TextView)rootView.findViewById(R.id.cust_email_th);
        TextView id_name=(TextView)rootView.findViewById(R.id.id_name_th);
        TextView id_nu=(TextView)rootView.findViewById(R.id.id_number_th);
        TextView dl=(TextView)rootView.findViewById(R.id.dl_number_th);
        TextView add=(TextView)rootView.findViewById(R.id.address_th);



        trip_to.setText(""+t_history_details.trip_to_where);
        pick_up_date.setText(""+t_history_details.pickUpDate);
        drop_up_date.setText(""+t_history_details.dropUp);
        cust_name_th.setText(""+t_history_details.customerName);
        con_no.setText(""+t_history_details.contactNumber);
        email_id.setText(""+t_history_details.emailId);
        id_name.setText(""+t_history_details.idName);
        id_nu.setText(""+t_history_details.idNumber);
        dl.setText(""+t_history_details.dl_number);
        add.setText(""+t_history_details.address);


        return rootView;
    }


}
