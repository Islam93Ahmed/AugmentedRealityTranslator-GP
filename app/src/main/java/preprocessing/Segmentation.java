package preprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;
import java.util.Map;

public class Segmentation {
	
	private int[][] orig_image;
	private int height;
	private int width;
	
	public Segmentation(Bitmap image)
	{
		this.height = image.getHeight();
		this.width = image.getWidth();
		orig_image = rgbToGray(image);
	}
	public int[][] rgbToGray(Bitmap image)
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
		HoughTransform hough_transform = new HoughTransform(30, 120, edge_img, height, width);
		int[][] hough_image = hough_transform.getHoughImage(5, 10);
		Map<Integer, BoundingBoxVertices> word_bounding_box = BoundingBox.create_bounding_box(hough_image, height, width);
		Bitmap[] words = new Bitmap[BoundingBox.components_num];
        return words;
	}
}
