package preprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;
import java.util.Map;
import java.util.Set;

public class SegmentText {
	
	private int[][] orig_image;
    private Bitmap BI;
	private int height;
	private int width;
	
	public SegmentText(Bitmap image)
	{
		this.height = image.getHeight();
		this.width = image.getWidth();
		orig_image = rgbToGray(image);
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

	public Bitmap[] getWords()
	{
		Thresholder thresholder = new Thresholder();
		int[][] binarized_img = thresholder.threshold(orig_image, height, width);
		SobelFilter sobel = new SobelFilter();
		int[][] edge_img = sobel.detectEdges(binarized_img, height, width);

        /*Bitmap bm = toBitmap(binarized_img);
        return bm;*/

		HoughTransform hough_transform = new HoughTransform(30, 120, edge_img, height, width);
		int[][] hough_image = hough_transform.getHoughImage(2, 6);

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
            {
                if(edge_img[i][j] > 0)
                    hough_image[i][j] = edge_img[i][j];
            }

      /* Bitmap bm = toBitmap(hough_image);
        return bm;*/

        Bitmap EI = toBitmap(edge_img);
        Bitmap BI = toBitmap(binarized_img);
        Bitmap HI = toBitmap(hough_image);

		Bitmap[] words = BoundingBox.getWords(HI, EI, BI, true);

        return words ;
	}
    public Bitmap[] getCharacters(Bitmap word)
    {
        Bitmap[] characters = BoundingBox.getCharacters(word, BI);
        return characters;
    }

    public  Bitmap test()
    {
        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                bm.setPixel(j, i, Color.rgb(orig_image[i][j], orig_image[i][j], orig_image[i][j]));
            }
        }

        return Thresholder.GrayscaleToBin(bm);
    }
    private Bitmap toBitmap(int[][] img)
    {
        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                bm.setPixel(j, i, Color.rgb(img[i][j], img[i][j], img[i][j]));
            }
        }
        return bm;
    }

}
