package com.example.art.augmentedrealitytranslator_gp;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View.OnClickListener;

import java.io.File;
import java.io.IOException;


public class MainActivity extends ActionBarActivity implements OnClickListener {
    Button Capture;
    Button set_wallpaper;
    ImageView image_view ;
    Intent i;
    final static int cameraData =0;
    Bitmap bmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }
    private void initialize()
    {
        image_view = new ImageView(this);
        Capture = new Button(this);
        set_wallpaper = new Button(this);
        Capture.setOnClickListener(this);
        set_wallpaper.setOnClickListener(this);
    }
/*
        /*File imgFile = new File("//mnt//sdcard//DCIM//Camera//IMG_20150303_165911.jpg");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) findViewById(R.id.imageView);

            myImage.setImageBitmap(myBitmap);

        }*/

/*
        String path = Environment.getExternalStorageDirectory() + "";

        File imgFile = new File(path);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView imageView = (ImageView) findViewById(R.id.image_camera);
            imageView.setImageBitmap(myBitmap);
        }
*/
    @Override
    public void onClick(View v) {
       //testing Camera
        switch (v.getId()){
            case R.id.button:
                try {
                    getApplicationContext().setWallpaper(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.TakePic:
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, cameraData);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            bmp = (Bitmap) extras.get("data");
            image_view.setImageBitmap(bmp);
        }
    }
}