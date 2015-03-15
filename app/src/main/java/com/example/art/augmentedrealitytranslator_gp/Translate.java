package com.example.art.augmentedrealitytranslator_gp;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import ocr.Test_data;
import preprocessing.SegmentText;

/**
 * Created by Islam on 3/11/2015.
 */
public class Translate {

    /*private Bitmap orig_img;
    private String text;*/
    public Bitmap hough_line_test;
    public Bitmap[] word_hough_test;

    public String /*Bitmap[] */get_translation(Bitmap orig_img, Context context)
    {
        //System.out.println(orig_img.getHeight() + " X  "+  orig_img.getWidth());
        String translated_text = " ";
        SegmentText segment = new SegmentText(orig_img);
        List<List<Bitmap>> words_characters = segment.getWords();
        hough_line_test = segment.line_hough_test;
        word_hough_test = segment.word_hough_image_test;
        translated_text = Test_data.recognize(words_characters, context);
        return  translated_text;
    }
}
