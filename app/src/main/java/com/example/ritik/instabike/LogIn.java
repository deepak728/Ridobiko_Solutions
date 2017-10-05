package com.example.ritik.instabike;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogIn extends AppCompatActivity {

    EditText et1,et2;
    Button login;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        et1 = (EditText) findViewById(R.id.editText3);
        et2 = (EditText) findViewById(R.id.editText4);
        login = (Button) findViewById(R.id.button3);

        pg = new ProgressDialog(LogIn.this);
        pg.setTitle("Loading");
        pg.setMessage("Please Wait..!!");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s1 = et1.getText().toString();
                String s2 = et2.getText().toString();
                if(s1.equals("") ||s2.equals(""))
                {
                    Toast.makeText(LogIn.this, "Fields cannot be left blank", Toast.LENGTH_SHORT).show();
                    return;
                }

                ConnectivityManager cm =
                        (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if(isConnected)
                {
                    viewdata();

                }
                else
                {
                    Toast.makeText(LogIn.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void viewdata()
    {
        final String usnm = et1.getText().toString();
        final String pswdd = et2.getText().toString();
        final String[] name = new String[1];

        class abc extends AsyncTask<Void,Void,String>
        {
            JSONObject bikes;

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> param = new HashMap<>();
                param.put("email",""+usnm);
                param.put("password",""+pswdd);

                RequestHandler rh = new RequestHandler();

                String res = rh.sendPostRequest("http://ridobiko.com/api/login", param);


                String newres=res.substring(res.indexOf("{"));
                try
                {

                    JSONObject jsonObject = new JSONObject(newres);
                    String s = jsonObject.optString("error");

                    if(s.equalsIgnoreCase("0"))
                    {
                        name[0] =jsonObject.optString("vendor name");
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

                pg.dismiss();
                if(s.equalsIgnoreCase("success"))
                {

                    //startService(new Intent("my.service.intent"));
                    SharedPreferences sharedPreferences = LogIn.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                    editor.putString(Config.EMAIL_SHARED_PREF, usnm);
                    editor.putString(Config.VENDOR_NAME, String.valueOf(name[0]));
                    editor.commit();

                    Toast.makeText(LogIn.this, "Login Success", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(LogIn.this,MainPage.class);
                    startActivity(i);

                    finish();
                }
                else
                {
                    et1.setText("");
                    et2.setText("");

                    Toast.makeText(LogIn.this, "Login Failed", Toast.LENGTH_LONG).show();

                }
                super.onPostExecute(s);
            }
        }
        abc  ae = new abc();
        ae.execute();
    }
}
