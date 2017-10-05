package com.example.ritik.instabike;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class RequestHandler {

    //Method to send httpPostRequest
    //This method is taking two arguments
    //First argument is the URL of the script to which we will send the request
    //Other is an HashMap with name value pairs containing the data to be send with the request
//    public String sendPostRequest(String requestURL,
//                                  HashMap<String, String> postDataParams) {
//        //Creating a URL
//        URL url;
//
//        //StringBuilder object to store the message retrieved from the server
//        StringBuilder sb = new StringBuilder();
//        try {
//            //Initializing Url
//            url = new URL(requestURL);
//
//            //Creating an httmlurl connection
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            //Configuring connection properties
//            conn.setReadTimeout(15000);
//            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//            conn.setRequestProperty("Content-Type", "application/json");
//            //conn.setRequestProperty("Content-Length", "" + postData.getBytes().length);
//            //conn.setRequestProperty("Content-Language", "en-US");
//
//            //Creating an output stream
//            OutputStream os = conn.getOutputStream();
//
//            //Writing parameters to the request
//            //We are using a method getPostDataString which is defined below
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(getPostDataString(postDataParams));
//
//            //writer.write("email=ridobiko@gmail.com&password=ridobiko");
//
//            writer.flush();
//            writer.close();
//            os.close();
//            int responseCode = conn.getResponseCode();
//
//            if (responseCode == HttpsURLConnection.HTTP_OK) {
//
//                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                sb = new StringBuilder();
//                String response;
//                //Reading server response
//                while ((response = br.readLine()) != null){
//                    sb.append(response);
//                }
//
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(sb.toString());
//        return sb.toString();
//    }

    public String sendPostRequest(String requestURL) {
        //Creating a URL
        URL url;

        //StringBuilder object to store the message retrieved from the server
        StringBuilder sb = new StringBuilder();
        try {
            //Initializing Url
            url = new URL(requestURL);

            //Creating an httmlurl connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //Configuring connection properties
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

//            conn.setRequestProperty("Content-Type",
//                    "application/json");
//
//            conn.setRequestProperty("Accept",
//                    "application/json");

            //req.setHeader('Accept', 'application/json');

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;
                //Reading server response
                while ((response = br.readLine()) != null){
                    sb.append(response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



    public String sendPostRequest(String requestURL,HashMap<String,String> postDataParams)
    {
        URL url;

        StringBuilder sb = new StringBuilder();

        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();
                String response;
                while ((response = br.readLine()) != null){
                    sb.append(response);
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String getPostDataString(HashMap<String,String> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
