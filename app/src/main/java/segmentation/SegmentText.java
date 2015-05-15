package segmentation;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import image.Converter;
import image.SobelFilter;

public class SegmentText {

	/*private int height;
	private int width;

    //test
    public Bitmap houghed_lines;

    public  List<Bitmap> words_img = new ArrayList<Bitmap>();*/
    public static Bitmap[] lines_img;

    public static List<List<Bitmap>>  /*Bitmap[] */ getWords(Bitmap img, Bitmap origi_img)
    {
        Bitmap[] lines =  BoundingBox.getWords(img, origi_img, false);
        lines_img = new Bitmap[lines.length];
        SobelFilter sobel = new SobelFilter();
        List<List<Bitmap>> word_char = new ArrayList<>();
        for (int i = 0; i < lines.length; i++)
        {
            int line_width = lines[i].getWidth();
            int line_height = lines[i].getHeight();

            Bitmap binary_img = Converter.GrayscaleToBin(lines[i]);
            Bitmap edge_img = sobel.detectEdges(binary_img);
            HoughTransform hough_transform = new HoughTransform(30, 120, edge_img, line_height, line_width);
            Bitmap line_hough_image = hough_transform.getHoughImage(2, 25);
            lines_img[i] = line_hough_image;
            Bitmap[] words = BoundingBox.getWords(line_hough_image, binary_img, false);

            for (int word_num = 0; word_num < words.length; word_num++)
            {
                if (words[word_num].getWidth() - lines[i].getWidth() > 5)
                    continue;
                int h = words[word_num].getHeight();
                int w = words[word_num].getWidth();
                int count = 0;
                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        if (Color.red(words[word_num].getPixel(x, y)) < 50)
                            count++;
                    }
                }
                if (count < 10)
                    continue;
                if (w < 20 || h < 20)
                    continue;

                Bitmap complement_img = Bitmap.createBitmap(w,h,words[word_num].getConfig());

                for (int y = 0; y < h; y++)
                {
                    for (int x = 0; x < w; x++)
                    {
                        if (Color.red(words[word_num].getPixel(x, y)) < 50)
                        {
                            complement_img.setPixel(x, y, Color.WHITE);
                        }
                        else
                        {
                            complement_img.setPixel(x, y, Color.BLACK);
                        }
                    }
                }

                Bitmap[] characters = BoundingBox.getWords(complement_img, words[word_num], true); // Segment Characters from each word
                List<Bitmap> tmp = new ArrayList<>();
                for (int char_num = 0; char_num < characters.length; char_num++)
                {
                    if (characters[char_num].getWidth() > 20 && characters[char_num].getHeight() > 20)
                        tmp.add(characters[char_num]);
                }
                //List<Bitmap> tmp = new ArrayList<>();
                //tmp.add(words[word_num]);
                word_char.add(tmp);
            }
        }

        return word_char;
    }


}
