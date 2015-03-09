package com.example.art.augmentedrealitytranslator_gp;;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;;
import android.widget.ImageView;
import android.view.View.OnClickListener;

import preprocessing.SegmentText;
import preprocessing.SegmentText;


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
        image_view = (ImageView)findViewById(R.id.ReturnedPic);
        Capture = new Button(this);
        set_wallpaper = new Button(this);
        Capture.setOnClickListener(this);
        set_wallpaper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       //testing Camera

       i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       startActivityForResult(i, cameraData);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            bmp = (Bitmap) extras.get("data");
            image_view.setImageBitmap(bmp);
            SegmentText segment = new SegmentText(bmp);
            //Bitmap new_bm =  segment.test();
            Bitmap[] words = segment.getWords();
            image_view.setImageBitmap(words[3]);
        }

    }
}