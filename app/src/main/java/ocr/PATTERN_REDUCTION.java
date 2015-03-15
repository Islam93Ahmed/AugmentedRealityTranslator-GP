package ocr;

import java.util.ArrayList;
import java.util.List;

public class PATTERN_REDUCTION {
	public static void REDUCTION(List<Integer>feature,List<Integer>new_feature)
    {
		int i = 0, j = 0;
        while (i <= feature.size() - 4)//4 with 3
        {
            if ((feature.get(j) == feature.get(i + 2)) && (feature.get(j + 1) == feature.get(i + 3)))
            {
                if (new_feature.isEmpty())
                {
                    new_feature.add(feature.get(j));
                    new_feature.add(feature.get(j + 1));
                }

                else if (new_feature.get(new_feature.size()-1) != feature.get(j + 1) && new_feature.get(new_feature.size() - 1) != feature.get(j))
                {
                    new_feature.add(feature.get(j));
                    new_feature.add(feature.get(j + 1));
                }

            }
                

            else
            {
                j = i + 2;
                new_feature.add(feature.get(i + 2));
                new_feature.add(feature.get(i + 3));
            }
            i += 2;
        }

      
    }

}
