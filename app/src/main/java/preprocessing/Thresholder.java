package preprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Thresholder {
	public int[][] threshold(int[][] image, int height, int width)
	{
		int[][] new_image = new int[height][width];
        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                bm.setPixel(j, i, Color.rgb(image[i][j], image[i][j], image[i][j]));
            }
        }
        Bitmap bin_image = GrayscaleToBin(bm);
        for (int x = 0; x < bin_image.getHeight(); x++)
        {
            for (int y = 0; y < bin_image.getWidth(); y++)
            {
                int color = bin_image.getPixel(y, x);
                new_image[x][y] =  Color.red(color);
            }
        }
		return new_image;
	}


    public static Bitmap GrayscaleToBin(Bitmap bm2) {
        Bitmap bm;
        bm = bm2.copy(Bitmap.Config.RGB_565, true);
        final int width = bm.getWidth();
        final int height = bm.getHeight();

        int pixel1, pixel2, pixel3, pixel4, A, R;
        int[] pixels;
        pixels = new int[width * height];
        bm.getPixels(pixels, 0, width, 0, 0, width, height);
        int size = width * height;
        int s = width / 8;
        int s2 = s >> 1;
        double t = 0.15;
        double it = 1.0 - t;
        int[] integral = new int[size];
        int[] threshold = new int[size];
        int i, j, diff, x1, y1, x2, y2, ind1, ind2, ind3;
        int sum = 0;
        int ind = 0;
        while (ind < size) {
            sum += pixels[ind] & 0xFF;
            integral[ind] = sum;
            ind += width;
        }
        x1 = 0;
        for (i = 1; i < width; ++i) {
            sum = 0;
            ind = i;
            ind3 = ind - s2;
            if (i > s) {
                x1 = i - s;
            }
            diff = i - x1;
            for (j = 0; j < height; ++j) {
                sum += pixels[ind] & 0xFF;
                integral[ind] = integral[(int) (ind - 1)] + sum;
                ind += width;
                if (i < s2) continue;
                if (j < s2) continue;
                y1 = (j < s ? 0 : j - s);
                ind1 = y1 * width;
                ind2 = j * width;

                if (((pixels[ind3] & 0xFF) * (diff * (j - y1))) < ((integral[(int) (ind2 + i)] - integral[(int) (ind1 + i)] - integral[(int) (ind2 + x1)] + integral[(int) (ind1 + x1)]) * it)) {
                    threshold[ind3] = 0x00;
                } else {
                    threshold[ind3] = 0xFFFFFF;
                }
                ind3 += width;
            }
        }

        y1 = 0;
        for (j = 0; j < height; ++j) {
            i = 0;
            y2 = height - 1;
            if (j < height - s2) {
                i = width - s2;
                y2 = j + s2;
            }

            ind = j * width + i;
            if (j > s2) y1 = j - s2;
            ind1 = y1 * width;
            ind2 = y2 * width;
            diff = y2 - y1;
            for (; i < width; ++i, ++ind) {

                x1 = (i < s2 ? 0 : i - s2);
                x2 = i + s2;

                // check the border
                if (x2 >= width) x2 = width - 1;

                if (((pixels[ind] & 0xFF) * ((x2 - x1) * diff)) < ((integral[(int) (ind2 + x2)] - integral[(int) (ind1 + x2)] - integral[(int) (ind2 + x1)] + integral[(int) (ind1 + x1)]) * it)) {
                    threshold[ind] = 0x00;
                } else {
                    threshold[ind] = 0xFFFFFF;
                }
            }
        }
   /*---------------------------------------------------------------------------*/
        bm.setPixels(threshold, 0, width, 0, 0, width, height);

        return bm;
    }
}
