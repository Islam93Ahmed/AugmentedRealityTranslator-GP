package segmentation;


import android.graphics.Bitmap;
import android.graphics.Color;

public class HoughTransform {
	
	private AccCell[][] positiveHoughSpace;
	private AccCell[][] negativeHoughSpace;
	private int startTheta;
	private int endTheta;
	private int height;
	private int width;
	private int maxRho;
	//private int[][] image;
	private Bitmap image;
	public HoughTransform(int startTheta, int endTheta, /*int[][] image*/ Bitmap image, int height, int width)
	{
		this.startTheta = startTheta;
		this.endTheta = endTheta;
		this.height = height;
		this.width = width;
		maxRho = (int) Math.sqrt((height * height) + (width * width));
		positiveHoughSpace = new AccCell[180][maxRho];
		negativeHoughSpace = new AccCell[180][maxRho];
		this.image = image;
		calculateHoughSpace();
	}
	
	private void calculateHoughSpace()
	{
		for (int x = 0; x < height; x++)
		{
			for (int y = 0; y < width; y++)
			{
				if(Color.red(image.getPixel(y,x)) == 0)
					continue;
				for (int Theta = 94; Theta <= endTheta; Theta++)
				{
					double thetaInRadian = Theta * (Math.PI * 180);
					int Rho = (int) ((x * Math.cos(thetaInRadian)) + (y * Math.sin(thetaInRadian)));
					//int Rho = (int) ((x * Math.cos(Theta)) + (y * Math.sin(Theta)));
					if (Rho <= maxRho && Rho >= -maxRho)
					{
						if(Rho >= 0)
						{
							if(positiveHoughSpace[Theta][Rho] == null)
								positiveHoughSpace[Theta][Rho] = new AccCell();
							positiveHoughSpace[Theta][Rho].count++;
							positiveHoughSpace[Theta][Rho].x.add(x);
							positiveHoughSpace[Theta][Rho].y.add(y);
						}
						else
						{
							Rho *= -1;
							//System.out.println(Theta + " " + Rho);
							if(negativeHoughSpace[Theta][Rho] == null)
								negativeHoughSpace[Theta][Rho] = new AccCell();
							negativeHoughSpace[Theta][Rho].count++;
							negativeHoughSpace[Theta][Rho].x.add(x);
							negativeHoughSpace[Theta][Rho].y.add(y);
						}
					}
				}
			}
		}
	}
	
	public /*int[][]*/ Bitmap getHoughImage(int pixelsCount, int connectDistance)
	{
		/*int[][] hough_image = new int[height][width];*/
        Bitmap hough_image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		for (int t = startTheta; t <= endTheta; t++)
        {
    		for (int r = 0; r < maxRho; r++)
    		{ 
				 if (positiveHoughSpace[t][r] != null && positiveHoughSpace[t][r].count >= pixelsCount)
				 {
					 for(int i = 0; i < (positiveHoughSpace[t][r].x.size()) - 1; i++)
					 {
						 int x0 = positiveHoughSpace[t][r].x.get(i), y0 = positiveHoughSpace[t][r].y.get(i);
						 int x1 = positiveHoughSpace[t][r].x.get(i+1), y1 = positiveHoughSpace[t][r].y.get(i+1);
						 int d = (int) Math.sqrt(((x0-x1)*(x0-x1)) + ((y0-y1)*(y0-y1)));
						 
						 if(d > 1)
						 {
							 
							 if(d < connectDistance)
							 {
								 if(x0 == x1)
									 horGapBridging(hough_image, x0, y0, x1, y1);
								 gapBridging(hough_image, x0, y0, x1, y1);
							 }
						 }
						 else
                             hough_image.setPixel(y0, x0, Color.rgb(255, 255, 255));

					 }
				 }// End of If
				
			} 	
    		for (int r = 0; r < maxRho; r++)
    		{ 
				 if (negativeHoughSpace[t][r] != null && negativeHoughSpace[t][r].count >= pixelsCount)
				 {
					 for(int i = 0; i < (negativeHoughSpace[t][r].x.size()) - 1; i++)
					 {
						 int x0 = negativeHoughSpace[t][r].x.get(i), y0 = negativeHoughSpace[t][r].y.get(i);
						 int x1 = negativeHoughSpace[t][r].x.get(i+1), y1 = negativeHoughSpace[t][r].y.get(i+1);
						 int d = (int) Math.sqrt(((x0-x1)*(x0-x1)) + ((y0-y1)*(y0-y1)));
						 
						 if(d > 1)
						 {
							 if(d < 50)
								 gapBridging(hough_image, x0, y0, x1, y1);
						 }
						 else
                             hough_image.setPixel(y0, x0, Color.rgb(255, 255, 255));
					 }
				 }// End of If
			} 	
        }	
        return hough_image;
	}
	
	private void gapBridging(/*int[][]*/Bitmap image, int x0, int y0, int x1, int y1)
	{
		 int dx = x1 - x0;
		 int dy = y1 - y0;
		 
		 int D = (2 * dy) - dx; 
		 image.setPixel(y0, x0, Color.rgb(255,255,255));
		 int y = y0;
		 for(int x = x0 + 1; x <= x1; x++)
		 {
			 System.out.println(x + " " + x1);
			 if(D > 0)
			 {
				 y++;
                 image.setPixel(y, x, Color.rgb(255,255,255));
				 D = D + (2*dy-2*dx);
			 }
			 else
			 {
                 image.setPixel(y, x, Color.rgb(255,255,255));
				 D = D + (2*dy);
			 }
		 }
	}
	
	private void horGapBridging(/*int[][]*/Bitmap image, int x0, int y0, int x1, int y1)
	{
		 image.setPixel(y0, x0, Color.rgb(255, 255, 255));
		
		 for(int y = y0 + 1; y <= y1; y++)
		 {
             image.setPixel(y, x0, Color.rgb(255, 255, 255));
		 }
	}
}
