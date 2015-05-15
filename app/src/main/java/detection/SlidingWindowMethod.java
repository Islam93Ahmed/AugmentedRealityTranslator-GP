package detection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;


public class SlidingWindowMethod {

	static Detector detector;

    public static void init(Context con)
    {
        Detector.init_detector(con);
    }

    public static Bitmap get_text(Bitmap origi_img) {
        /*Bitmap gray_img = Converter.rgbToGray(origi_img);
        Bitmap resized_img = Bitmap.createScaledBitmap(gray_img, 400, 300, true);*/
        Bitmap result = slideWindow(origi_img);
        return result;
    }

	
	// sliding window
	public static Bitmap slideWindow(Bitmap image)
	{
		int w = image.getWidth();
		int h = image.getHeight();

		Bitmap result = Bitmap.createBitmap(w, h, image.getConfig());
		//int index = 0;
		int win_width = 60;
		int win_height = 60;
		
		for (int k = 0; k < 2; k++)
		{
			for (int i = 0; i + win_height < h; i += win_width/2)
			{
				for (int j = 0; j + win_width < w; j += 8)
				{
                    Bitmap subimage = Bitmap.createBitmap(image, j, i, win_width, win_height);
					int output = detector.test(subimage);
					if (output == 0)
					{
						for (int y = i; y <  i + win_height; y++)
							for (int x = j; x < j + win_width; x++)
							{
                                result.setPixel(x, y, Color.rgb(255, 255, 255));
							}
					}
				}
			}
			win_width += 10;
			win_height += 10;
		}
		
		//System.out.println("Finished");
		return result;
	}
	
	

}
