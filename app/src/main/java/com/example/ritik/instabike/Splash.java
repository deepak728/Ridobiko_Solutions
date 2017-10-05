package com.example.ritik.instabike;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Splash extends Activity {

    boolean loggedIn;

    SQLiteDatabase sd;
    String tablequery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }

        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(3000);


                    sd=openOrCreateDatabase("RidoBiko",MODE_PRIVATE,null);


                    //sd.execSQL("drop table Users");
                    tablequery="create table if not exists Users(id integer,image string);";
                    sd.execSQL(tablequery);
                    Cursor c=sd.rawQuery("select * from Users where id=1;",null);

                    if(!c.moveToNext()) {

                        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),  R.drawable.cancel);
                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                        byte [] ba = bao.toByteArray();
                        String ba1= Base64.encodeToString(ba,Base64.DEFAULT);

                        sd.execSQL("insert into Users values(1,'bal');");


                    }


                    SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                    loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                    if(loggedIn){
                        Intent intent = new Intent(Splash.this, MainPage.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(Splash.this, LogIn.class);
                        startActivity(intent);

                    }
                    finish();

                } catch (Exception e) {

                }
            }
        };

        background.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
