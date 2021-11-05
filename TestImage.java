package com.mine.alertadddelete.modifiedscreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mine.alertadddelete.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class TestImage extends AppCompatActivity {

    Button read;
    ImageView mimageView;

    final int REQUEST_CODE_GALLERY = 999;
    ArrayList<String> imageUris;
    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle readdInstanceState) {
        super.onCreate(readdInstanceState);
        setContentView(R.layout.activity_test_image);
        mimageView = findViewById(R.id.imageView);
        read = findViewById(R.id.read);

        imageUris = new ArrayList<>();
        /*read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                File file = new File(directory, "mario" + ".png");
                imageView.setImageDrawable(Drawable.createFromPath(file.toString()));

                *//*try {
                    fetchImage(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }*//*
            }
        });*/

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ActivityCompat.requestPermissions(TestImage.this,new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);*/

                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, REQUEST_CODE_GALLERY);

//                pickImage();

            }
        });
    }

   /* public  void fetchImage(View view) throws IOException {

        File folder= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "");
        FileInputStream fis = new FileInputStream(folder);
        byte[] image= new byte[fis.available()];
        fis.read(image);
        *//*ContentValues values = new ContentValues();
        values.put("image",image);
        db.insert("imageTb", null,values);*//*
        fis.close();
        Toast.makeText(this,"Image Fetched", Toast.LENGTH_SHORT).show();
    }*/

    private void pickImage(){

        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 2);

       /* Intent imagePicking = new Intent();
        imagePicking.setType("image/*");
        imagePicking.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
//        imagePicking.setAction(Intent.ACTION_GET_CONTENT);
//        Action "ACTION_GET_CONTENT" is used for get the complete data that shown in the gallery
        imagePicking.setAction(Intent.ACTION_PICK);
//        Action "ACTION_PICK" is used for get the data from where(gallery,photos,media etc)
        startActivityForResult(imagePicking,REQUEST_CODE_GALLERY);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GALLERY){
            Uri imageUri = data.getData();
            mimageView.setImageURI(imageUri);
        }

        /*if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_GALLERY){

                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                mimageView.setImageURI(selectedImageUri);

                    *//*int count = data.getClipData().getItemCount(); //number of picked images
                    for(int i = 0; i<count; i++){
//                        get Image Uri at specific index
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageUris.add(String.valueOf(imageUri));*//*

                   *//* try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*//*
                }
            }*/


        }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private Context getActivity() {
        return TestImage.this;
    }
}
