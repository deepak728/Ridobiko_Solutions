package com.example.ritik.instabike;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class upcoming_booking_details extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    public static String trip_to_where;
    public  static String pickUpDate;
    public static String dropUp;
    public  static String customerName;
    public static String contactNumber;
    public  static String emailId;
    public static String idName;
    public  static String idNumber;
    public static String dl_number;
    public  static String address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ub_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Details");

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        Bundle bundle = getIntent().getExtras();

       trip_to_where=bundle.getString("TT");
        pickUpDate=bundle.getString("PD");
        dropUp=bundle.getString("DD");
         customerName=bundle.getString("CUSN");
        contactNumber=bundle.getString("CONN");
        emailId=bundle.getString("EI");
         idName=bundle.getString("INAME");
        idNumber=bundle.getString("INUM");
         dl_number=bundle.getString("DL");
        address=bundle.getString("ADD");


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    ub_tab1_cust_details tab_1=new ub_tab1_cust_details();
                    return tab_1;
                case 1:
                    ub_tab2_bike_details tab_2=new ub_tab2_bike_details();
                    return tab_2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CUSTOMER DETAILS";
                case 1:
                    return "BIKES";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out, R.anim.slide_in);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
        return true;
    }

}