package segmentation;

public class BoundingBoxVertices {
	protected int x1;
	protected int y1;
	
	protected int x2;
	protected int y2;
	
	public BoundingBoxVertices(int x, int y)
	{
		this.x1 = x;
		this.y1 = y;
		this.x2 = 0;
		this.y2 = 0;
	}
	
}
