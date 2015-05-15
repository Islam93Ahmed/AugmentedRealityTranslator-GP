package detection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import multilayerperceptron.BackPropagation;

public class Detector {

	private  static BackPropagation pg = null;

	public static void init_detector(Context con)
	{	
		pg = new BackPropagation(con);
		pg.set_weights("Weights.txt");
	}
	
	public static int test(Bitmap image)
	{
        Bitmap resized = Bitmap.createScaledBitmap(image, 28, 28, true);
		double[] test_feature = new double[784];
	       
		int feature_num = 0;
		for ( int y = 0; y < resized.getHeight(); y++)
		{
			for ( int x = 0; x< resized.getWidth(); x++)
			{

				//double feature = color.getRed();
				//Data= ((2(one of the feature)-min(feature set))/(max(feature set)-min (feature set))-1)
				//test_feature[feature_num] = ((2 * (feature) - Arrays.s));
				test_feature[feature_num] = ((double)Color.red( resized.getPixel(x, y))) / 255.0f;
				//test_feature[feature_num] = color.getRed();
				feature_num++;
				//System.out.println(color.getRed() + " => " + test_feature[feature_num]);
			}
		}
	       
		return pg.test(test_feature);
 
	}	//endtest
	

}//end class
