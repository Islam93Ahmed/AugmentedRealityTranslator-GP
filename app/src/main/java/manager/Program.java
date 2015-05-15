package manager;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import character_recognition.CharRecog;
import detection.SlidingWindowMethod;
import image.Converter;
import segmentation.SegmentText;

/**
 * Created by Islam on 4/20/2015.
 */
public class Program {

    public Program(Context con)
    {
        SlidingWindowMethod.init(con);
        CharRecog.init_detector(con);
        System.out.println("Weights Loaded");
    }

    public List<List<Bitmap>> start(Bitmap origi_img)
    {
        Bitmap gray_img = Converter.rgbToGray(Bitmap.createScaledBitmap(origi_img, 600, 400, true));
        //Bitmap gray_img = Converter.rgbToGray(origi_img);
        Bitmap lines_img = SlidingWindowMethod.get_text(gray_img);
        List<List<Bitmap>> words = SegmentText.getWords(lines_img, gray_img);

        String text = "";
        for (List<Bitmap> word : words) {
            //text = "";
            for (Bitmap character : word) {
                /*int output;
                char ch = ' ';*/
                char output = CharRecog.test(character);

                text += output;
            }
            text += " ";
        }
        System.out.println(text);


        return words;
    }



}
