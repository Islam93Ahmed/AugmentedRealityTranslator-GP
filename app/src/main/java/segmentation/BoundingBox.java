package segmentation;

import android.graphics.Bitmap;
import android.graphics.Color;
import image.ConnectComponent;

public class BoundingBox {


    public static Bitmap[] getWords(Bitmap edge_img, Bitmap binary_img, boolean flag)
    {
        BoundingBoxVertices[] bounding_box = createBoundingBox(edge_img, flag);
        Bitmap[] words = new Bitmap[bounding_box.length - 1];
        int index = 0;
        for(int k = 1; k < bounding_box.length; k++)
        {
            int x1, y1, x2, y2;
            x1 = bounding_box[k].x1;
            y1 = bounding_box[k].y1;
            x2 = bounding_box[k].x2;
            y2 = bounding_box[k].y2;

            if(x1 - 2 > 0)
                x1 -= 2;
            if(y1 - 2 > 0)
                y1 -= 2;
            if(x2 + 2 < edge_img.getHeight())
                x2 += 2;
            if(y2 + 2 < edge_img.getWidth())
                y2 += 2;

            int w = y2 - y1;
            int h = x2 - x1;

            //System.out.println("x1= " + x1+ " y1= " + y1 + " x2= " + x2+ " y2= " + y2);
            words[index] = Bitmap.createBitmap(w + 4, h + 4, Bitmap.Config.ARGB_8888);
            for(int i = 0; i < h + 4; i++)
                for(int j = 0; j < w + 4; j++)
                {
                    words[index].setPixel(j, i, Color.WHITE);
                }
            for(int x = x1, i = 2; x < x2; x++, i++)
            {
                for(int y = y1, j = 2; y < y2; y++, j++)
                {
                    int color = binary_img.getPixel(y, x);
                    words[index].setPixel(j, i, Color.rgb(Color.red(color), Color.green(color), Color.blue(color)));
                }
            }
            index++;
        }
        return words;
    }


    // CONVERT THE PARAMETER img FROM Bitmap TO int[][]
    private static BoundingBoxVertices[] createBoundingBox(Bitmap img, boolean flag)
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
        //Map<Integer, BoundingBoxVertices> bounding_box = new HashMap<Integer, BoundingBoxVertices>();
        BoundingBoxVertices[] bounding_box = new BoundingBoxVertices[cc.next_label + 1];
        int[][] buffer_label_image = new int[height][width];
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                buffer_label_image[i][j] = labeledImage[index];
                int label = buffer_label_image[i][j];
                if(label > 0)
                {
                    if(bounding_box[label] == null)
                        bounding_box[label] = new BoundingBoxVertices(i, j);
                    /*else
                    {*/
                        if(i < bounding_box[label].x1)
                            bounding_box[label].x1 = i;
                        else if(i > bounding_box[label].x2)
                            bounding_box[label].x2 = i;
                        if( j < bounding_box[label].y1)
                            bounding_box[label].y1 = j;
                        else if(j > bounding_box[label].y2)
                            bounding_box[label].y2 = j;
                    //}
                }
                index++;
            }
        }

        /*for (int i = 1; i < cc.next_label+1; i++)
        {
            System.out.println("x1= " + bounding_box[i].x1+ " y1= " + bounding_box[i].y1 + " x2= " + bounding_box[i].x2+ " y2= " + bounding_box[i].y2);
        }
        System.out.println("---------------------------------------------------------");*/
        if (flag) {
            BoundingBoxVertices[] sorted = sort(bounding_box);
            sort_verticies(bounding_box);
        }
      /*  for (int i = 1; i < cc.next_label+1; i++)
        {
            System.out.println("x1= " + sorted[i].x1+ " y1= " + sorted[i].y1 + " x2= " + sorted[i].x2+ " y2= " + sorted[i].y2);
        }*/
        return bounding_box;
    }



    private static int sort_verticies (BoundingBoxVertices[] bb)
    {
        int[] index = new int[bb.length];
        int[] y = new int[bb.length];
        System.out.println("Index   Y");
        for (int i = 1; i < bb.length; i++)
        {
            index[i] = i;
            y[i] = bb[i].y1;
            System.out.println(index[i] +"  "+  y[i]);
        }
        InsertionSort(y, index);
        System.out.println("After Sorting");
        for (int i = 1; i < bb.length; i++)
        {
            System.out.println(index[i] +"  "+  y[i]);
        }
        return 0;
    }


    public static void InsertionSort( int[ ] num, int[] index)
    {
        int j;                     // the number of items sorted so far
        int key;                // the item to be inserted
        int i;
        int tmp;

        for (j = 2; j < num.length; j++)    // Start with 1 (not 0)
        {
            key = num[ j ];
            tmp = index[j];
            for(i = j - 1; (i >= 1) && (num[ i ] > key); i--)   // Smaller values are moving up
            {
                num[ i+1 ] = num[ i ];
                index[i + 1] = index[i];
            }
            num[ i+1 ] = key;    // Put the key in its proper location
            index[i+1] = tmp;
        }
    }

    private static BoundingBoxVertices[] sort(BoundingBoxVertices[] bb)
    {
        int size = bb.length;
        BoundingBoxVertices[] sorted = new BoundingBoxVertices[size];
        BoundingBoxVertices[] bb_array = new BoundingBoxVertices[size];
        for (int i = 1; i < size; i++)
        {
            bb_array[i] = new BoundingBoxVertices(0, 0);
            bb_array[i].x1 = bb[i].x1;
            bb_array[i].y1 = bb[i].y1;
            bb_array[i].x2 = bb[i].x2;
            bb_array[i].y2 = bb[i].y2;
        }
        for(int i = 1; i < bb_array.length - 1; i++)
        {
            for (int j = 2; j < bb_array.length - i; j++)
            {
                if(bb_array[j-1].y1 < bb_array[j].y1)
                {
                    BoundingBoxVertices tmp = new BoundingBoxVertices(0,0);
                    tmp.x1 = bb_array[j-1].x1;
                    tmp.y1 = bb_array[j-1].y1;
                    tmp.x2 = bb_array[j-1].x2;
                    tmp.y2 = bb_array[j-1].y2;
                    bb_array[j-1].x1 = bb_array[j].x1;
                    bb_array[j-1].y1 = bb_array[j].y1;
                    bb_array[j-1].x2 = bb_array[j].x2;
                    bb_array[j-1].y2 = bb_array[j].y2;
                    bb_array[j].x1 = tmp.x1;
                    bb_array[j].y1 = tmp.y1;
                    bb_array[j].x2 = tmp.x2;
                    bb_array[j].y2 = tmp.y2;
                }
            }
        }
        for (int i = 1; i < size; i++)
        {
            sorted[i] = new BoundingBoxVertices(0,0);
            sorted[i].x1 = bb_array[i].x1;
            sorted[i].y1 = bb_array[i].y1;
            sorted[i].x2 = bb_array[i].x2;
            sorted[i].y2 = bb_array[i].y2;
        }
       /* for (int i = 1; i < size; i++)
        {
            System.out.println("x1= " + sorted[i].x1+ " y1= " + sorted[i].y1 + " x2= " + sorted[i].x2+ " y2= " + sorted[i].y2);
        }*/
        return sorted;
    }

}
