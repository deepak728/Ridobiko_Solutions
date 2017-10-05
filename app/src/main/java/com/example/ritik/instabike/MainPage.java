package com.example.ritik.instabike;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ritik.instabike.Activities.CreateTrip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wdullaer.materialdatetimepicker.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPage extends AppCompatActivity {

    ProgressDialog pg;
    TextView bikes_on_trip,maintenance_bikes_no,bikes_returing_today,total_bikes;
    static int activityFlag=0;

    SwipeRefreshLayout mSwipeRefreshLayout;
    ConnectivityManager cm;
    NetworkInfo activeNetwork;
    boolean isConnected;

    SQLiteDatabase sd;
    String insertquery;
    String checkquery;

    Cursor m;


    private static final int PICK_PROFILE = 1;
    Uri imageUri;
    ImageView profile_photo;
    Bitmap bitmap_s;
    String image_base64_content;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        requestPermission();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);

        SharedPreferences sharedPreferences = MainPage.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Singleton.getInstance().setUser(sharedPreferences.getString(Config.EMAIL_SHARED_PREF, ""));

        tv= (TextView) findViewById(R.id.name);
        tv.setText(sharedPreferences.getString(Config.VENDOR_NAME,""));

        pg = new ProgressDialog(MainPage.this);
        pg.setTitle("Loading");
        pg.setMessage("Please Wait..!!");

        ImageView openDrawer =(ImageView)findViewById(R.id.open_drawer);
        ImageView notification=(ImageView)findViewById(R.id.notification);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });


        ImageView createTrip =(ImageView)findViewById(R.id.create_trip);
        ImageView upcomingBooking =(ImageView)findViewById(R.id.upcoming_booking);
        ImageView onTripBikes =(ImageView)findViewById(R.id.onTrip);
        ImageView gps_order =(ImageView)findViewById(R.id.order_gps);
        ImageView maintenanceBikes =(ImageView)findViewById(R.id.maintenance);
        ImageView tripHistory =(ImageView)findViewById(R.id.trips);
        ImageView terms =(ImageView)findViewById(R.id.terms);
        ImageView myBikes =(ImageView)findViewById(R.id.my_bikes);
        ImageView support =(ImageView)findViewById(R.id.get_support);
        ImageView addBikes =(ImageView)findViewById(R.id.add_bikes);
        profile_photo =(ImageView)findViewById(R.id.profile_photo);
        ImageView addProfilePhoto=(ImageView)findViewById(R.id.profile_add);
        Button logout =(Button)findViewById(R.id.logOut);

         bikes_on_trip= (TextView) findViewById(R.id.bikes_on_trip);
         maintenance_bikes_no= (TextView) findViewById(R.id.maintenance_bikes_no);
         bikes_returing_today= (TextView) findViewById(R.id.bikes_returing_today);
         total_bikes= (TextView) findViewById(R.id.total_bikes);


        createTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,CreateTrip.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
            }
        });

        upcomingBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,upcoming_booking.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);


            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,terms_and_conditions.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);


            }
        });

        onTripBikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,Bikes_On_Trip.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);


            }
        });
        gps_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), order_gps.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);

            }
        });
        maintenanceBikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,Maintaince.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
            }
        });


        tripHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,trip_history.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);

            }
        });



        myBikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MainPage.this,MyBikes.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
            }
        });


        addBikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainPage.this, add_bikes.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);

            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), support.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
            }
        });

        addProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainPage.this, "profile-pic", Toast.LENGTH_SHORT).show();
                pickImageFromGallery();


            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               logout();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainPage.this,CreateTrip.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
            }
        });

        cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if(isConnected)
        {
            initViews();
        }
        else
        {
            bikes_on_trip.setText("");
            maintenance_bikes_no.setText("");
            total_bikes.setText("");
            bikes_returing_today.setText("");
            Toast.makeText(MainPage.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                     @Override
                                                     public void onRefresh() {

                                                         refreshContent();

                                                     }
                                                 });




    }

    public void  setImage() {
        sd = openOrCreateDatabase("RidoBiko", MODE_PRIVATE, null);
        m = sd.rawQuery("select * from Users where id=1;", null);

if(m!=null)
        if (m.moveToNext()) {


            String b = m.getString(1);

            if(b!=null&&!b.isEmpty()) {

                byte[] decodedString = Base64.decode(b, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

              // ByteArrayInputStream imageStream = new ByteArrayInputStream(decodedByte);
              //  profile_photo.setImageBitmap(getCircleBitmap(BitmapFactory.decodeStream(imageStream)));
                profile_photo.setImageBitmap(decodedByte);
            }

        }

    }



    @Override
    protected void onResume() {

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            initViews();
        }
        else
        {

            Toast.makeText(MainPage.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }

    private void refreshContent(){
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            initViews();
        }
        else
        {

            bikes_on_trip.setText("");
            maintenance_bikes_no.setText("");
            total_bikes.setText("");
            bikes_returing_today.setText("");
            Toast.makeText(MainPage.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }

        mSwipeRefreshLayout.setRefreshing(false);

        }

    private void initViews(){

        loadJSON();
    }
    private void loadJSON() {
        pg.show();

        //setImage();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ridobiko.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);


        Call<MainPageRetrofit> call = request.getMainPage(Singleton.getInstance().getUser());


        call.enqueue(new Callback<MainPageRetrofit>() {
            @Override
            public void onResponse(Call<MainPageRetrofit> call, Response<MainPageRetrofit> response) {


                MainPageRetrofit jsonResponse = response.body();
                if(jsonResponse!=null) {
                     Bikes bikes = jsonResponse.getBikes();
                    if(jsonResponse.getError().equalsIgnoreCase("0")&&bikes!=null)
                    {
                        if(bikes.getOnTrip()!=null)
                        bikes_on_trip.setText(""+bikes.getOnTrip().size());
                        if(bikes.getUnderMaintenance()!=null)
                        maintenance_bikes_no.setText(""+bikes.getUnderMaintenance().size());

                        if(bikes.getTotalNumberOfBikes()!=null)
                        {
                            List<Detail> lt=bikes.getTotalNumberOfBikes().getDetails();
                            if(lt!=null)
                                total_bikes.setText(""+lt.size());

                        }


                        if(bikes.getBikesReturningToday()!=null)
                       bikes_returing_today.setText(""+bikes.getBikesReturningToday().size());

                    }
                }
                pg.dismiss();
            }

            @Override
            public void onFailure(Call<MainPageRetrofit> call, Throwable t) {
                pg.dismiss();
                Toast.makeText(MainPage.this,"Please check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void logout()
    {

        class abc extends AsyncTask<Void,Void,String>
        {

            @Override
            protected String doInBackground(Void... v) {
                RequestHandler rh = new RequestHandler();

                String res = rh.sendPostRequest("http://ridobiko.com/api/logout");
                try
                {

                    JSONObject jsonObject = new JSONObject(res);
                    String s = jsonObject.optString("status");

                    if(s.equalsIgnoreCase("logged out"))
                        return "Success";
                }catch (JSONException e){ e.printStackTrace();}

                return "Failure";
            }

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {


                if(s.equalsIgnoreCase("success"))
                {
                    //stopService(new Intent("my.service.intent"));

                    Toast.makeText(MainPage.this, "Logout success", Toast.LENGTH_SHORT).show();

                    //Getting out sharedpreferences
                    SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                    //Getting editor
                    SharedPreferences.Editor editor = preferences.edit();

                    //Puting the value false for loggedin
                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                    //Putting blank value to email
                    editor.putString(Config.EMAIL_SHARED_PREF, "");
                    editor.putString(Config.VENDOR_NAME,"");


                    //Saving the sharedpreferences
                    editor.commit();

                    Intent i=new Intent(MainPage.this,LogIn.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(MainPage.this, "Logout failed", Toast.LENGTH_SHORT).show();

                }

                super.onPostExecute(s);
            }
        }
        abc  ae = new abc();
        ae.execute();


    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    private void pickImageFromGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_PROFILE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PROFILE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(imageUri, filePath, null, null, null);

            if(cursor!=null) {
                if (cursor.moveToFirst()) {
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

                    profile_photo.setImageBitmap(getCircleBitmap(BitmapFactory.decodeFile(imagePath)));
                    try {
                        bitmap_s = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (bitmap_s != null) {
                        new MainPage.encode_image_to_base64().execute();
                    } else
                        Toast.makeText(MainPage.this, "Can't Upload Image", Toast.LENGTH_SHORT).show();
                    cursor.close();

                }
            }

        }
    }

    private class encode_image_to_base64 extends AsyncTask<Bitmap,Void,String> {


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            new Handler().postDelayed(new Runnable() {
                public void run() {

                }
            }, 100);

        }
        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            if(bitmap_s!=null){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap_s.compress(Bitmap.CompressFormat.PNG, 90, stream);
                byte [] byte_arr = stream.toByteArray();

                sd=openOrCreateDatabase("RidoBiko",MODE_PRIVATE,null);
                checkquery="select * from Users where id=1;";
                Cursor c=sd.rawQuery(checkquery,null);

                if(c.moveToNext())
                {
                    SQLiteStatement statement=sd.compileStatement("update Users set image=? where Users.id=1;");
                    image_base64_content = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                    statement.bindString(1,image_base64_content);
                    statement.execute();
//
//                }
//                else
//                {
//                    SQLiteStatement statement=sd.compileStatement("insert into Users(image) values(?) where;");
//                    statement.bindBlob(1,byte_arr);
//                    statement.execute();
                }

                return "success";

            }

            return "failure";
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            //pdia.dismiss();
            if(aVoid.equalsIgnoreCase("success"))
            {
                Toast.makeText(MainPage.this,"Picture Uploaded",Toast.LENGTH_SHORT).show();
                refreshContent();
            }
            else
                {
                Toast.makeText(MainPage.this,"Picture Uploaded failure",Toast.LENGTH_SHORT).show();
            }

        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE,
                                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET}
                        , 10);
            }

        } else
            startService(new Intent(this, MainPage.class));

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    startService(new Intent(this, MainPage.class));
                }
                else {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    Toast.makeText(MainPage.this, "Please Allow Permissions", Toast.LENGTH_LONG).show();

                    finish();
                    System.exit(0);
                }
                break;
            }
            default:
                break;
        }


    }
}