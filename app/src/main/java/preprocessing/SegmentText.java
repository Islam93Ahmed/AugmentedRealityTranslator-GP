package preprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SegmentText {
	
	private int[][] orig_image;
   // private Bitmap BI;
	private int height;
	private int width;

    public Bitmap line_hough_test;
	public Bitmap[] word_hough_image_test;

	public SegmentText(Bitmap image)
    {
        this.height = image.getHeight();
        this.width = image.getWidth();
        orig_image = rgbToGray(image);
        word_hough_image_test = new Bitmap[10];
    }

	private int[][] rgbToGray(Bitmap image)
	{
		int[][] gray = new int[height][width];
		for (int x = 0; x < height; x++)
		{
			for (int y = 0; y < width; y++)
			{
                int rgb = image.getPixel(y, x);
				Color color = new Color();
				int red = color.red(rgb);
				int green = color.green(rgb);
				int blue = color.blue(rgb);
				gray[x][y] = (int)(0.59f * (float)red + 0.30f * (float)green +  0.11f * (float)blue);
			}
		}
		return gray;
	}

	public List<List<Bitmap>> /*Bitmap[]*/ getWords()
	{
        //int counter = 0;
		Thresholder thresholder = new Thresholder();
        int[][] binarized_img = thresholder.threshold(orig_image, height, width);
        SobelFilter sobel = new SobelFilter();
        int[][] edge_img = sobel.detectEdges(binarized_img, height, width);

        HoughTransform hough_transform_lines = new HoughTransform(85, 95, edge_img, height, width);
        int[][] hough_image_lines  = hough_transform_lines.getHoughImage(10, 50);
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
            {
                if(edge_img[i][j] > 0)
                    hough_image_lines[i][j] = edge_img[i][j];
            }
        Bitmap EI_lines = toBitmap(edge_img, width, height);
        Bitmap HI_lines = toBitmap(hough_image_lines, width, height);
        Bitmap BI_lines = toBitmap(binarized_img, width, height);
        Bitmap[] lines = BoundingBox.getLines(HI_lines, EI_lines, BI_lines);
        line_hough_test = HI_lines;
        //return EI_lines;

        ///////////////////////////////OKAY//////////////////////////////////////////

       List<List<Bitmap>> word_characters = new ArrayList<List<Bitmap>>();
       for (int line_num = 1; line_num < lines.length; line_num++)
       {
           if(lines[line_num] == null)
               continue;
           int line_width = lines[line_num].getWidth();
           int line_height = lines[line_num].getHeight();
           int[][] line_buffer = toIntBuffer(lines[line_num]);
           HoughTransform hough_transform = new HoughTransform(30, 120, line_buffer, line_height, line_width);
           int[][] hough_image = hough_transform.getHoughImage(2, 6);
           for (int i = 0; i < lines[line_num].getHeight(); i++)
               for (int j = 0; j < lines[line_num].getWidth(); j++) {
                    if (line_buffer[i][j] > 0)
                        hough_image[i][j] = line_buffer[i][j];
               }
           ////////////////////////////////OKAY///////////////////////////////////
            Bitmap line_edge_image = toBitmap(line_buffer, line_width, line_height);
            Bitmap line_hough_image = toBitmap(hough_image, line_width, line_height);
            word_hough_image_test[line_num] = line_edge_image;
           Bitmap[] words = BoundingBox.getWords(line_hough_image, line_edge_image, line_num);
           int num_words = words.length;

           /*if (num_words >= 4)
               return words;*/

           for (int word_num = 1; word_num < num_words; word_num++) {

               Bitmap new_image = blackToWhite(words[word_num]);
               List<Bitmap> characters = BoundingBox.getCharacters(new_image);
               List<Bitmap> tmp = new ArrayList<Bitmap>();
               for (Bitmap character : characters)
               {
                   Bitmap c =  blackToWhite(character);
                   /*int[][] image = Filter.smooth(toIntBuffer(c), c.getHeight(), c.getWidth());
                   c = toBitmap(image, c.getWidth(), c.getHeight());
                   c = threshold(c);*/
                   tmp.add(c);
               }
               word_characters.add(tmp);
           }
       }
        /*return null;*/
        return word_characters;
    }

    private Bitmap blackToWhite(Bitmap bm)
    {
        Bitmap newImage = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
        for (int x = 0; x < bm.getHeight(); x++)
            for (int y = 0; y < bm.getWidth(); y++)
            {
                int color = bm.getPixel(y, x);
                if(Color.red(color) > 0)
                    newImage.setPixel(y, x, Color.rgb(0, 0, 0));
                else
                    newImage.setPixel(y, x, Color.rgb(255, 255, 255));
            }
        return newImage;
    }

    private Bitmap threshold(Bitmap bm)
    {
        Bitmap newImage = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
        for (int x = 0; x < bm.getHeight(); x++)
            for (int y = 0; y < bm.getWidth(); y++)
            {
                int color = bm.getPixel(y, x);
                int red = Color.red(color);
                if(red < 170)
                    newImage.setPixel(y, x, Color.rgb(0, 0, 0));
                else
                    newImage.setPixel(y, x, Color.rgb(255, 255, 255));
            }
        return newImage;
    }

    private Bitmap toBitmap(int[][] img, int _width, int _height)
    {
        Bitmap bm = Bitmap.createBitmap(_width, _height, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < _height; i++)
        {
            for (int j = 0; j < _width; j++)
            {
                bm.setPixel(j, i, Color.rgb(img[i][j], img[i][j], img[i][j]));
            }
        }
        return bm;
    }

    private int[][] toIntBuffer(Bitmap image)
    {
        int[][] gray = new int[image.getHeight()][image.getWidth()];
        for (int x = 0; x < image.getHeight(); x++)
        {
            for (int y = 0; y < image.getWidth(); y++)
            {
                int rgb = image.getPixel(y, x);
                Color color = new Color();
                gray[x][y] = color.red(rgb);
            }
        }
        return gray;
    }


}
