package preprocessing;

public class SobelFilter {

	private final int[][] hor_kernel = {{-1, -2, -1},{0, 0, 0},{1, 2, 1}};
	private final int[][] ver_kernel = {{-1, 0, 1},{-2, 0, 2},{-1, 0, 1}};
	
	
	public int[][] detectEdges(int[][] origImage, int height, int width)
	{
		int[][] padedImage = padByZero(origImage, height, width);
		int[][] edgeImage = new int[height][width];
		int[][] horEdgeImage = detectHorizontalEdges(padedImage, height, width);
		int[][] verEdgeImage = detectVerticalEdges(padedImage, height, width);
		/*int[][] diag1EdgeImage = detectDiagonal1Edges(padedImage, height, width);
		int[][] diag2EdgeImage = detectDiagonal2Edges(padedImage, height, width);*/
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				if(verEdgeImage[i][j] < 0)
					verEdgeImage[i][j] *= -1;
				if(horEdgeImage[i][j] < 0)
					horEdgeImage[i][j] *= -1;
				
				edgeImage[i][j] = verEdgeImage[i][j] | horEdgeImage[i][j];
			}
		}
		
		return edgeImage;
	}
	
	private int[][] padByZero(int[][] origImage, int height, int width)
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
	
	private int[][] detectHorizontalEdges(int[][] origImage, int height, int width)
	{
		int[][] edgeImage  = new int[height][width]; 
		int x = 0, y = 0;
		for(int i = 1; i < height+1; i++)
		{
			for(int j = 1; j < width+1; j++)
			{
				int sum = 0;
				sum += origImage[i-1][j-1] * hor_kernel[0][0];
				sum += origImage[i-1][j]   * hor_kernel[0][1];
				sum += origImage[i-1][j+1] * hor_kernel[0][2];
				sum += origImage[i][j-1]   * hor_kernel[1][0];
				sum += origImage[i][j]     * hor_kernel[1][1];
				sum += origImage[i][j+1]   * hor_kernel[1][2];
				sum += origImage[i+1][j-1] * hor_kernel[2][0];
				sum += origImage[i+1][j]   * hor_kernel[2][1];
				sum += origImage[i+1][j+1] * hor_kernel[2][2];
				edgeImage[x][y] = sum;
				y++;
			}
			x++;
			y = 0;
		}
		return edgeImage;
	}
	
	private int[][] detectVerticalEdges(int[][] origImage, int height, int width)
	{
		int[][] edgeImage  = new int[height][width]; 
		int x = 0, y = 0;
		for(int i = 1; i < height+1; i++)
		{
			for(int j = 1; j < width+1; j++)
			{
				int sum = 0;
				sum += origImage[i-1][j-1] * ver_kernel[0][0];
				sum += origImage[i-1][j]   * ver_kernel[0][1];
				sum += origImage[i-1][j+1] * ver_kernel[0][2];
				sum += origImage[i][j-1]   * ver_kernel[1][0];
				sum += origImage[i][j]     * ver_kernel[1][1];
				sum += origImage[i][j+1]   * ver_kernel[1][2];
				sum += origImage[i+1][j-1] * ver_kernel[2][0];
				sum += origImage[i+1][j]   * ver_kernel[2][1];
				sum += origImage[i+1][j+1] * ver_kernel[2][2];
				edgeImage[x][y] = sum;
				y++;
			}
			x++;
			y = 0;
		}
		return edgeImage;
	}
}
