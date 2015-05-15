package com.example.art.augmentedrealitytranslator_gp;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import manager.Program;
import segmentation.SegmentText;


public class MainActivity extends ActionBarActivity implements OnClickListener {
    Button Capture;
    private Uri mUri;

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
                //System.out.println(v.getId());
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(Environment.getExternalStorageDirectory(),  "photo.jpg");
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                mUri = Uri.fromFile(f);


                startActivityForResult(i, cameraData);
                break;
        }

    }

    //Convert Picture to Bitmap
    private static Bitmap pictureDrawable2Bitmap(Picture picture) {
        PictureDrawable pd = new PictureDrawable(picture);
        Bitmap bitmap = Bitmap.createBitmap(pd.getIntrinsicWidth(), pd.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawPicture(pd.getPicture());
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            getContentResolver().notifyChange(mUri, null);
            ContentResolver cr = getContentResolver();

            try {
                bmp = android.provider.MediaStore.Images.Media.getBitmap(cr, mUri);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            //////////////////////////////////////////////

            Program program = new Program(this);

            /*List<Bitmap> result = program.start(bmp);
            int j = 0;
            for (Bitmap characters : result) {
                //image_view.setImageBitmap(image);
                save_image(characters, 0, j);
                j++;
            }*/

            //System.out.println(program.start(bmp));

            //Bitmap[] result = program.start(bmp);
            //Bitmap result = program.start(bmp);
            //save_image(result,0,0);

            List<List<Bitmap>> result = program.start(bmp);

            int j = 0;
            int i = 0;
            for (List<Bitmap> word : result) {
                for (Bitmap characters : word) {
                    //image_view.setImageBitmap(image);
                    save_image(characters, i, j);
                    j++;
                }
                j = 0;
                i++;
            }

            Bitmap[] lines = SegmentText.lines_img;
            for (int k = 0; k < lines.length; k++)
            {
                save_image(lines[k], i, k);
            }
            /*int j = 0;
            for (int i = 0; i < result.length; i++)
            {
                save_image(result[i],0,j);
                j++;
            }*/

            /*Bitmap resized = Bitmap.createScaledBitmap(bmp, 400, 300  , true);
            save_image(bmp,1,1);
            save_image(resized,2,2);*/
            //Translate translate = new Translate();

            /*String str = translate.get_translation(bmp, this);
            System.out.println("Translation: " + str);*/
            /*SegmentText segment = new SegmentText();
            words_characters = segment.getWords(bmp);
            int i = 1, j = 1;
            for (List<Bitmap> characters : words_characters)
            {
                for (Bitmap image : characters) {
                    //image_view.setImageBitmap(image);
                    save_image(image,i,j);
                    j++;
                }
                j = 0;
                i++;
            }*/
            //save_image(segment.houghed_lines,1,1);
            /*Bitmap[] lines = segment.lines_img;
            for(Bitmap line : lines)
            {
                if (line != null)
                {
                    save_image(line,i++, j++);
                }
            }*/
           /* List<Bitmap> words = segment.words_img;
            for(Bitmap word : words)
            {
                if (word != null)
                {
                    save_image(word,i++, j++);
                }
            }*/


        }


    }


    public void save_image(final Bitmap ph, int i, int j) {
        String root = Environment.getExternalStorageDirectory().toString();
        File newDir = new File(root + "/saved_images");
        newDir.mkdirs();
        String photo_name = "Letter" + i + " " + j + " " + ".jpg";
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