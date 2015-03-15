package preprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.BitmapCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BoundingBox {

    private static Bitmap[] orig_lines;
    private static int num_components;
    public static Bitmap[] getWords(Bitmap img, Bitmap source, int line_num)
    {
        Map<Integer, BoundingBoxVertices> bounding_box = createBoundingBox(img);
        /*System.out.println("Before sorting");
        for (int i = 1; i < num_components; i++)
        {
            int x1 = bounding_box.get(i).x1;
            int y1 = bounding_box.get(i).y1;
            int x2 = bounding_box.get(i).x2;
            int y2 = bounding_box.get(i).y2;
            System.out.println("(" + x1 + ", " + y1 + ") & ("+ x2 + ", " + y2 + ") ");
        }*/
        //sort(bounding_box);
        /*System.out.println("After sorting");
        for (int i = 1; i < num_components; i++)
        {
            int x1 = bounding_box.get(i).x1;
            int y1 = bounding_box.get(i).y1;
            int x2 = bounding_box.get(i).x2;
            int y2 = bounding_box.get(i).y2;
            System.out.println("(" + x1 + ", " + y1 + ") & ("+ x2 + ", " + y2 + ") ");
        }*/

        Set<Integer> keys = bounding_box.keySet();
        Bitmap[] orig_words = new Bitmap[keys.size() + 1];

        for(int key : keys)
        {
            int x1, y1, x2, y2;
            x1 = bounding_box.get(key).x1;
            y1 = bounding_box.get(key).y1;
            x2 = bounding_box.get(key).x2;
            y2 = bounding_box.get(key).y2;

            if(x1 - 2 > 0)
                x1 -= 2;
            if(y1 - 2 > 0)
                y1 -= 2;
            if(x2 + 2 < img.getHeight())
                x2 += 2;
            if(y2 + 2 < img.getWidth())
                y2 += 2;

            int w = y2 - y1;
            int h = x2 - x1;

            orig_words[key] = Bitmap.createBitmap(w + 10 , h + 10, Bitmap.Config.ARGB_8888);
            for(int i = 0; i < (x2-x1) + 10; i++)
                for(int j = 0; j < (y2-y1) + 10; j++)
                {
                    orig_words[key].setPixel(j, i, Color.WHITE);
                }
            for(int x = x1, i = 5; x < x2; x++, i++)
            {
                for(int y = y1, j = 5; y < y2; y++, j++)
                {
                    int color = orig_lines[line_num].getPixel(y, x);
                    orig_words[key].setPixel(j, i, Color.rgb(Color.red(color), Color.green(color), Color.blue(color)));
                }
            }
        }
        return orig_words;
    }

    public static List<Bitmap> getCharacters(Bitmap img)
    {
        Map<Integer, BoundingBoxVertices> bounding_box = createBoundingBox(img);
        Set<Integer> keys = bounding_box.keySet();

        List<Bitmap> characters = new ArrayList<Bitmap>();

        for(int key : keys)
        {
            int x1, y1, x2, y2;
            x1 = bounding_box.get(key).x1;
            y1 = bounding_box.get(key).y1;
            x2 = bounding_box.get(key).x2;
            y2 = bounding_box.get(key).y2;

            if(x1 - 1 > 0)
                x1 -= 1;
            if(y1 - 1 > 0)
                y1 -= 1;
            if(x2 + 1 < img.getHeight())
                x2 += 1;
            if(y2 + 1 < img.getWidth())
                y2 += 1;

            if(x2 - x1 < 0 || y2 - y1 < 0)
                continue;


            Bitmap character = Bitmap.createBitmap((y2-y1) + 10 , (x2-x1) + 10, Bitmap.Config.ARGB_8888);

            for(int x = x1, i = 5; x < x2; x++, i++)
            {
                for(int y = y1, j = 5; y < y2; y++, j++)
                {
                    int color = img.getPixel(y, x);
                    character.setPixel(j, i, Color.rgb(Color.red(color), Color.red(color), Color.red(color)));
                }
            }
            characters.add(character);
        }
        return characters;
    }

    public static Bitmap[] getLines(Bitmap img, Bitmap source, Bitmap source_2)
    {
        Map<Integer, BoundingBoxVertices> bounding_box = createBoundingBox(img);
        Set<Integer> keys = bounding_box.keySet();

        Bitmap[] lines = new Bitmap[keys.size() + 1];
        orig_lines = new Bitmap[keys.size() + 1];

        for(int key : keys)
        {
            int x1 = bounding_box.get(key).x1,
                    y1 = bounding_box.get(key).y1,
                    x2 = bounding_box.get(key).x2,
                    y2 = bounding_box.get(key).y2;

            if(x1 - 2 > 0)
                x1 -= 2;
            if(y1 - 2 > 0)
                y1 -= 2;
            if(x2 + 2 < img.getHeight())
                x2 += 2;
            if(y2 + 2 < img.getWidth())
                y2 += 2;

            int line_width = y2 - y1;
            int line_height = x2 - x1;
            if(line_width < 0 || line_height < 0)
            {
                continue;
            }
            int image_size = (img.getWidth() - 2) * (img.getHeight() - 2);
            int line_size = line_height * line_width;
            if(line_size > image_size)
            {
                continue;
            }

            lines[key] = Bitmap.createBitmap((y2-y1) + 10 , (x2-x1) + 10, Bitmap.Config.ARGB_8888);
            orig_lines[key] = Bitmap.createBitmap((y2-y1) + 10 , (x2-x1) + 10, Bitmap.Config.ARGB_8888);
            for(int i = 0; i < (x2-x1) + 10; i++)
                for(int j = 0; j < (y2-y1) + 10; j++)
                {
                    orig_lines[key].setPixel(j, i, Color.WHITE);
                }
            for(int x = x1, i = 5; x < x2; x++, i++)
            {
                for(int y = y1, j = 5; y < y2; y++, j++)
                {
                    int color = source.getPixel(y, x);
                    lines[key].setPixel(j, i, Color.rgb(Color.red(color), Color.green(color), Color.blue(color)));
                    color = source_2.getPixel(y, x);
                    orig_lines[key].setPixel(j, i, Color.rgb(Color.red(color), Color.green(color), Color.blue(color)));
                }
            }
        }
        return lines;
    }

    private static Map<Integer, BoundingBoxVertices> createBoundingBox(Bitmap img)
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
        num_components = cc.next_label+1;
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
        return bounding_box;
    }


    private static void sort(Map<Integer, BoundingBoxVertices> bb)
    {
        System.out.println("Before sorting");
        for (int i = 1; i < num_components; i++)
        {
            int x1 = bb.get(i).x1;
            int y1 = bb.get(i).y1;
            int x2 = bb.get(i).x2;
            int y2 = bb.get(i).y2;
            System.out.println("(" + x1 + ", " + y1 + ") & ("+ x2 + ", " + y2 + ") ");
        }

        for(int c = 1; c <(num_components-1); c++)
        {
            for (int d = 1; d < (num_components-c-1); d++)
            {
                if(bb.get(d).y1 > bb.get(d+1).y1)
                {
                    BoundingBoxVertices tmp = new BoundingBoxVertices(0,0);
                    tmp.x1 = bb.get(d).x1;
                    tmp.y1 = bb.get(d).y1;
                    tmp.x2 = bb.get(d).x2;
                    tmp.y2 = bb.get(d).y2;
                    bb.get(d).x1 = bb.get(d+1).x1;
                    bb.get(d).y1 = bb.get(d+1).y1;
                    bb.get(d).x2 = bb.get(d+1).x2;
                    bb.get(d).y2 = bb.get(d+1).y2;
                    bb.get(d+1).x1 = tmp.x1;
                    bb.get(d+1).y1 = tmp.y1;
                    bb.get(d+1).x2 = tmp.x2;
                    bb.get(d+1).y2 = tmp.y2;
                }
            }
        }


        System.out.println("After sorting");
        for (int i = 1; i < num_components; i++)
        {
            int x1 = bb.get(i).x1;
            int y1 = bb.get(i).y1;
            int x2 = bb.get(i).x2;
            int y2 = bb.get(i).y2;
            System.out.println("(" + x1 + ", " + y1 + ") & ("+ x2 + ", " + y2 + ") ");
        }
    }

}
