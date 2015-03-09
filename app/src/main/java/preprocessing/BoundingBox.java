package preprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.BitmapCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BoundingBox {

    public static int words_num;
    public static int char_num;
    public static Bitmap[] orig_words;
    public static Bitmap[] getWords(Bitmap img, Bitmap source, Bitmap source_2, boolean flag)
    {
        int width = img.getWidth();
        int height = img.getHeight();
        System.out.println(width + " " + height);
        int[] oneRowImage = new int[height * width];
        int index = 0;
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                int color = img.getPixel(j, i);
                oneRowImage[index] = Color.red(color);
                index++;
            }
        }
        ConnectComponent cc = new ConnectComponent();
        int[] labeledImage = cc.compactLabeling(oneRowImage, height, width, true);
        index = 0;

        Map<Integer, BoundingBoxVertices> bounding_box = new HashMap<Integer, BoundingBoxVertices>();
        int[][] buffer_label_image = new int[height][width];
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                buffer_label_image[i][j] = labeledImage[index];
                int label = buffer_label_image[i][j];
                if(label > 0)
                {
                    if(bounding_box.get(label) == null)
                        bounding_box.put(label, new BoundingBoxVertices(i, j));
                    else
                    {
                        if(i < bounding_box.get(label).x1)
                            bounding_box.get(label).x1 = i;
                        else if(i > bounding_box.get(label).x2)
                            bounding_box.get(label).x2 = i;
                        if( j < bounding_box.get(label).y1)
                            bounding_box.get(label).y1 = j;
                        else if(j > bounding_box.get(label).y2)
                            bounding_box.get(label).y2 = j;
                    }
                }
                index++;
            }
        }
        Set<Integer> keys = bounding_box.keySet();
        words_num = keys.size();
        Bitmap[] words = new Bitmap[cc.next_label + 1];
        if(flag)
            orig_words = new Bitmap[cc.next_label + 1];
        for(int key : keys)
        {
            int x1 = bounding_box.get(key).x1,
                    y1 = bounding_box.get(key).y1,
                    x2 = bounding_box.get(key).x2,
                    y2 = bounding_box.get(key).y2;

            source.setPixel(y1, x1, Color.GREEN);
            source.setPixel(y2, x2, Color.GREEN);

            int w = y2 - y1;
            int h = x2 - x1;

            words[key] = Bitmap.createBitmap(w + 10, h + 10, Bitmap.Config.ARGB_8888);
            if(flag)
            {
                orig_words[key] = Bitmap.createBitmap((y2-y1) + 10 , (x2-x1) + 10, Bitmap.Config.ARGB_8888);
                for(int i = 0; i < (x2-x1) + 10; i++)
                    for(int j = 0; j < (y2-y1) + 10; j++)
                    {
                        orig_words[key].setPixel(j, i, Color.WHITE);
                    }
            }
            for(int x = x1, i = 5; x < x2; x++, i++)
            {
                for(int y = y1, j = 5; y < y2; y++, j++)
                {
                    int color = source.getPixel(y, x);
                    words[key].setPixel(j, i, Color.rgb(Color.red(color), Color.red(color), Color.red(color)));
                    if(flag)
                    {
                        color = source_2.getPixel(y, x);
                        orig_words[key].setPixel(j, i, Color.rgb(Color.red(color), Color.green(color), Color.blue(color)));
                    }
                }
            }
        }
        return words;
    }

    public static Bitmap[] getCharacters(Bitmap img, Bitmap source)
    {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] oneRowImage = new int[height * width];
        int index = 0;
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                int color = img.getPixel(j, i);
                oneRowImage[index] = Color.red(color);
                index++;
            }
        }
        ConnectComponent cc = new ConnectComponent();
        int[] labeledImage = cc.compactLabeling(oneRowImage, height, width, true);
        index = 0;

        Map<Integer, BoundingBoxVertices> bounding_box = new HashMap<Integer, BoundingBoxVertices>();
        int[][] buffer_label_image = new int[height][width];
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                buffer_label_image[i][j] = labeledImage[index];
                int label = buffer_label_image[i][j];
                if(label > 0)
                {
                    if(bounding_box.get(label) == null)
                        bounding_box.put(label, new BoundingBoxVertices(i, j));
                    else
                    {
                        if(i < bounding_box.get(label).x1)
                            bounding_box.get(label).x1 = i;
                        else if(i > bounding_box.get(label).x2)
                            bounding_box.get(label).x2 = i;
                        if( j < bounding_box.get(label).y1)
                            bounding_box.get(label).y1 = j;
                        else if(j > bounding_box.get(label).y2)
                            bounding_box.get(label).y2 = j;
                    }
                }
                index++;
            }
        }
        Set<Integer> keys = bounding_box.keySet();
        char_num = keys.size();
        Bitmap[] words = new Bitmap[cc.next_label + 1];

        for(int key : keys)
        {
            int x1 = bounding_box.get(key).x1 - 2,
                    y1 = bounding_box.get(key).y1 - 2,
                    x2 = bounding_box.get(key).x2 + 2,
                    y2 = bounding_box.get(key).y2 + 2;

            words[key] = Bitmap.createBitmap((y2-y1) + 10 , (x2-x1) + 10, Bitmap.Config.ARGB_8888);
            for(int i = 0; i < (x2-x1) + 10; i++)
                for(int j = 0; j < (y2-y1) + 10; j++)
                {
                    words[key].setPixel(j, i, Color.WHITE);
                }
            for(int x = x1, i = 5; x < x2; x++, i++)
            {
                for(int y = y1, j = 5; y < y2; y++, j++)
                {
                    int color = source.getPixel(y, x);
                    words[key].setPixel(j, i, Color.rgb(Color.red(color), Color.red(color), Color.red(color)));
                }
            }
        }
        return words;
    }
}
