package com.example.art.augmentedrealitytranslator_gp;;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity implements OnClickListener {
    Button Capture;

    ImageView image_view ;
    Intent i;
    final static int cameraData =0;
    Bitmap bmp;
    List<List<Bitmap>> words_characters;

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
        Capture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.TakePic:
                System.out.println(v.getId());
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
            //////////////////////////////////////////////
            Translate translate = new Translate();

            String str = translate.get_translation(bmp, this);
            System.out.println("Translation: " + str);
            /*words_characters = translate.get_translation(bmp, this);
            for (List<Bitmap> characters : words_characters)
            {
                for (Bitmap image : characters) {
                    //image_view.setImageBitmap(image);
                    save_image(image);
                }
            }
            save_image(translate.hough_line_test);
            for (Bitmap im : translate.word_hough_test)
            {
                if (im != null)
                {
                    save_image(im);
                }
            }*/
            /*Bitmap[] images = translate.get_translation(bmp);

            for (Bitmap image : images)
                if(image != null)
                    save_image(image);*/
            //System.out.println(images[1].getHeight() + " X  "+  images[1].getWidth());

            /////////////////////////////////////////////////////////
        }


    }


    public void save_image(final Bitmap ph) {
        String root = Environment.getExternalStorageDirectory().toString();
        File newDir = new File(root + "/saved_images");
        newDir.mkdirs();
        Random gen = new Random();
        int n = 10000;
        n = gen.nextInt(n);
        String photo_name = "photo22-" + n + ".jpg";
        File file = new File(newDir, photo_name);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            ph.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getApplicationContext(), "saved to your folder", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }



}