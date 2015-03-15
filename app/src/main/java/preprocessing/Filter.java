package preprocessing;

import android.graphics.Bitmap;

import java.util.Arrays;

/**
 * Created by Islam on 3/14/2015.
 */
public class Filter {


    public static int[][] smooth(int[][] image, int height, int width)
    {
        int[][] newImage = new int[height][width];
        int[][] padded_image = padByZero(image, height, width);
        for(int x = 1, i = 0; x < height + 1; x++, i++)
        {
            for(int y = 1, j = 0; y < width + 1; y++, j++)
            {
                newImage[i][j] = padded_image[x-1][y-1] + padded_image[x-1][y] + padded_image[x-1][y+1]
                        + padded_image[x][y-1] + padded_image[x][y] + padded_image[x][y+1]
                        + padded_image[x+1][y-1] + padded_image[x+1][y] + padded_image[x+1][y+1];

                newImage[i][j] /= 9;
                //newImage[i][j] = (newImage[i][j] > 128) ? 255 : 0;
                /*int[] neighbors = { padded_image[x-1][y-1] , padded_image[x-1][y] , padded_image[x-1][y+1]
                        , padded_image[x][y-1] ,padded_image[x][y], padded_image[x][y+1]
                        , padded_image[x+1][y-1] , padded_image[x+1][y] , padded_image[x+1][y+1]};
                Arrays.sort(neighbors);
                newImage[i][j] = neighbors[4];*/
            }
        }
        return newImage;
    }
    private static int[][] padByZero(int[][] origImage, int height, int width)
    {
        int[][] padedImage = new int[height+2][width+2];
        for(int i = 0; i < width+2; i++)
            padedImage[0][i] = 0;
        for(int i = 0; i < width+2; i++)
            padedImage[height+1][i] = 0;
        for(int i = 0; i < height+2; i++)
            padedImage[i][0] = 0;
        for(int i = 0; i < height+2; i++)
            padedImage[i][width+1] = 0;

        int x = 0, y = 0;
        for(int i = 1; i < height+1; i++)
        {
            for(int j = 1; j < width+1; j++)
            {
                padedImage[i][j] = origImage[x][y];
                y++;
            }
            y = 0;
            x++;
        }
        return padedImage;
    }
}
