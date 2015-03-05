package preprocessing;

import java.util.HashMap;
import java.util.Map;

public class BoundingBox {

    public static int components_num;
	public static Map<Integer, BoundingBoxVertices> create_bounding_box(int[][] img, int height, int width)
	{
		int[] oneRowImage = new int[height * width];
		int index = 0;
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				oneRowImage[index] = img[i][j];
				index++;
			}
		}
		ConnectComponent cc = new ConnectComponent();
		int[] labeledImage = cc.compactLabeling(oneRowImage, height, width, true);
        components_num = cc.getMaxLabel();
		index = 0;
		Map<Integer, BoundingBoxVertices> box_vertices = new HashMap<Integer, BoundingBoxVertices>();
		int[][] buffer_label_image = new int[height][width];
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				/*Color color = new Color(labeledImage[index]*20, labeledImage[index]*20, labeledImage[index]*20); 
				label_image.setRGB(j, i, color.getRGB());*/
				buffer_label_image[i][j] = labeledImage[index];
				int label = buffer_label_image[i][j];
				if(label > 0)
				{
					if(box_vertices.get(label) == null)
						box_vertices.put(label, new BoundingBoxVertices(i, j));
					else
					{
						if(i < box_vertices.get(label).x1)
							box_vertices.get(label).x1 = i;
						else if(i > box_vertices.get(label).x2)
							box_vertices.get(label).x2 = i;
						if( j < box_vertices.get(label).y1)
							box_vertices.get(label).y1 = j;
						else if(j > box_vertices.get(label).y2)
							box_vertices.get(label).y2 = j;
					}
				}
				index++;
			}
		}
		return box_vertices;
	}
}
