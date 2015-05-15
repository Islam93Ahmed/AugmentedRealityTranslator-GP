package image;

import android.graphics.Bitmap;
import android.graphics.Color;

public class SobelFilter {

    private final int[][] hor_kernel = {{-1, -2, -1},{0, 0, 0},{1, 2, 1}};
	private final int[][] ver_kernel = {{-1, 0, 1},{-2, 0, 2},{-1, 0, 1}};

    public Bitmap detectEdges(Bitmap origImage)
    {
        int width = origImage.getWidth();
        int height = origImage.getHeight();
        Bitmap padedImage = padByZero(origImage);
        Bitmap edgeImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int x = 0, y = 0;
        for(int i = 1; i < height+1; i++)
        {
            for(int j = 1; j < width+1; j++)
            {
                int y_sum = sumOfProducts(ver_kernel, i, j, padedImage), x_sum = sumOfProducts(hor_kernel, i, j, padedImage);
                int color = ((int)Math.ceil(Math.sqrt((y_sum * y_sum) + (x_sum * x_sum))) > 128) ? 255 : 0;
                edgeImage.setPixel(y, x, Color.rgb(color, color, color));
                y++;
            }
            x++;
            y = 0;
        }
        return edgeImage;
    }

    private int sumOfProducts(int[][] kernel, int i, int j, Bitmap img)
    {
        int sum = 0;
        int pixel = Color.red(img.getPixel(j - 1, i - 1));
        sum += pixel * kernel[0][0];
        pixel = Color.red(img.getPixel(j, i -1));
        sum += pixel * kernel[0][1];
        pixel = Color.red(img.getPixel(j+1, i-1));
        sum += pixel * kernel[0][2];
        pixel = Color.red(img.getPixel(j-1, i));
        sum += pixel  * kernel[1][0];
        pixel = Color.red(img.getPixel(j, i));
        sum += pixel * kernel[1][1];
        pixel = Color.red(img.getPixel(j+1, i));
        sum += pixel  * kernel[1][2];
        pixel = Color.red(img.getPixel(j-1, i+1));
        sum += pixel * kernel[2][0];
        pixel = Color.red(img.getPixel(j, i+1));
        sum += pixel  * kernel[2][1];
        pixel = Color.red(img.getPixel(j+1, i+1));
        sum += pixel * kernel[2][2];
        return sum;
    }

	private Bitmap padByZero( Bitmap origImage)
	{
        int width = origImage.getWidth();
        int height = origImage.getHeight();
        Bitmap paddedImage = Bitmap.createBitmap(width+2, height+2, Bitmap.Config.ARGB_8888);

		int x = 0, y = 0;
		for(int i = 1; i < height+1; i++)
		{
			for(int j = 1; j < width+1; j++)
			{
                paddedImage.setPixel(j, i, origImage.getPixel(y, x));
				y++;
			}
			y = 0;
			x++;
		}
		return paddedImage;
	}
}
