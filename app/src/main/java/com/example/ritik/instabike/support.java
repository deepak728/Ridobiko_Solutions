package com.example.ritik.instabike;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.example.ritik.instabike.R;

public class support extends AppCompatActivity {

    private static final int PICK_PROFILE = 1;
    Uri imageUri=null;
    ImageView profile_photo;
    Bitmap bitmap_s=null;
    String image_base64_content=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Support");

        LinearLayout make_call = (LinearLayout) findViewById(R.id.make_call);

        make_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:8006359582"));
                startActivity(callIntent);
            }
        });


        LayoutInflater li = getLayoutInflater();

         View view = li.inflate(R.layout.my_drawer, null);
         profile_photo=(ImageView)view.findViewById(R.id.profile_photo);
        ImageView profile_add=(ImageView)view.findViewById(R.id.profile_add);

        profile_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(support.this, "profile-pic", Toast.LENGTH_SHORT).show();
                pickImageFromGallery();
            }
        });



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
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            profile_photo.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            try {
                bitmap_s = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(bitmap_s!=null){
                new encode_image_to_base64().execute();
            }
            else
                Toast.makeText(support.this,"Can't Upload Image",Toast.LENGTH_SHORT).show();
            cursor.close();

        }
    }

    private class encode_image_to_base64 extends AsyncTask<Bitmap,Void,Void> {
        private ProgressDialog pdia;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pdia = new ProgressDialog(support.this);
            pdia.setMessage("Uploading Profile Pic");
            pdia.setCancelable(false);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    pdia.show();
                }
            }, 100);

        }
        @Override
        protected Void doInBackground(Bitmap... bitmaps) {
            if(bitmap_s!=null){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap_s.compress(Bitmap.CompressFormat.PNG, 90, stream);
                byte [] byte_arr = stream.toByteArray();
                image_base64_content = Base64.encodeToString(byte_arr, Base64.DEFAULT);
            }
            else
                Toast.makeText(support.this,"Image Error",Toast.LENGTH_SHORT).show();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(support.this,"Picture Uploaded",Toast.LENGTH_SHORT).show();
            pdia.dismiss();
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition( R.anim.slide_out, R.anim.slide_in);
        return true;
    }
}
