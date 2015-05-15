package feature_extraction;

import android.graphics.Bitmap;
import android.graphics.Color;


public class MainFile {
	/*public static BufferedImage imageCrop(BufferedImage input){
		int height = input.getHeight();
		int width = input.getWidth();
		int hend = -1, hstart = Integer.MAX_VALUE, wend = -1, wstart = Integer.MAX_VALUE;
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				if (input.getRGB(j, i) == Color.BLACK.getRGB()) {
					if(hstart > i){
						hstart = i;
					}
					if(wstart > j){
						wstart = j;
					}
					if(hend < i){
						hend = i;
					}
					if(wend < j){
						wend = j;
					}
				} 
			}
		}
		int heightNew = hend - hstart; 
		int widthNew = wend - wstart; 
		BufferedImage biCrop = new BufferedImage( widthNew, heightNew, input.getType());
		for(int i = hstart; i<hend; i++){
			for(int j=wstart; j<wend; j++){
				biCrop.setRGB(j-wstart, i-hstart, input.getRGB(j, i));
			}
		}
		return biCrop;
	}*/
	/*public static BufferedImage thinnedImage(BufferedImage input){
		int[][] imageData = new int[input.getHeight()][input.getWidth()];
        Color c;
        for (int y = 0; y < imageData.length; y++) {
            for (int x = 0; x < imageData[y].length; x++) {
                if (input.getRGB(x, y) == Color.BLACK.getRGB()) {
                    imageData[y][x] = 1;
                } else {
                    imageData[y][x] = 0;
                }
            }
        }
//        User Zhang Suen Thinning Algorithm
        ThinningService thinningService = new ThinningService();
        thinningService.doZhangSuenThinning(imageData);
        for (int y = 0; y < imageData.length; y++) { 
            for (int x = 0; x < imageData[y].length; x++) {

                if (imageData[y][x] == 1) {
                    input.setRGB(x, y, Color.BLACK.getRGB());
 
                } else {
                    input.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }
        return input;
	}*/
	

	/*public static double[] getTamura(BufferedImage input, boolean print){
		ColorProcessor imagec = new ColorProcessor(input);
        Tamura descriptor = new Tamura();
        if(print){
			System.out.println("Sift: " + descriptor.getDescription());
		}
        descriptor.run(imagec);
        List<double[]> features = descriptor.getFeatures();
        for(int i=0; i<features.size(); i++){
        	for(int j=0 ;j<features.get(i).length; j++)
        		if(print){
        			System.out.println(j+ ": " + features.get(i)[j]);
        		}
        }
       
		return features.get(0);
	}
	public static double[] getHaralick(BufferedImage input, boolean print){
        ColorProcessor imagec = new ColorProcessor(input);
        Haralick descriptor = new Haralick();
        descriptor.run(imagec);
        List<double[]> features = descriptor.getFeatures();
        for(int i=0; i<features.size(); i++){
        	for(int j=0 ;j<features.get(i).length; j++)
        		if(print){
        			System.out.println(j+ ": " + features.get(i)[j]);
        		}
        }
		return features.get(0);
	}
	public static double[] getEccentricity(BufferedImage input, boolean print){
		 ColorProcessor imagec = new ColorProcessor(input);
	        Eccentricity descriptor = new Eccentricity();
	        descriptor.run(imagec);
	        if(print){
	        	System.out.println(descriptor.getDescription());
	        }
	        List<double[]> features = descriptor.getFeatures();
	        for(int i=0; i<features.size(); i++){
	        	for(int j=0 ;j<features.get(i).length; j++)
	        		if(print){
	        			System.out.println(j+ ": " + features.get(i)[j]);
	        		}
	        }
			return features.get(0);
	}
	
	public static double[] getHistogram(BufferedImage input, boolean print) throws IOException{
        ColorProcessor image = new ColorProcessor(input);
        // load the properties from the default properties file
        // change the histogram to span just 2 bins
        // and let's just extract the histogram for the RED channel as 
        // pixel value is same for all color channels
        LibProperties prop = LibProperties.get();
        prop.setProperty(LibProperties.HISTOGRAMS_BINS, 2);
        prop.setProperty(LibProperties.HISTOGRAMS_TYPE, "Red");
        // initialize the descriptor, set the properties and run it
        Histogram descriptor = new Histogram();
        descriptor.setProperties(prop);
        descriptor.run(image);
        // obtain the features
        List<double[]> features = descriptor.getFeatures();
        // print the features to system out
        for (int i=0; i<features.size(); i++){
            for(int j=0; j<features.get(i).length; j++){
            	if(print){
            		System.out.println(features.get(i)[j]);
            	}
            }
        }
        return features.get(0);
	}*/
	
	public static double[][] getMatrix(Bitmap input){
		int height = input.getHeight(); 
		int width = input.getWidth();
		double [][] array = new double[height][width];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				if(input.getPixel(j, i) == Color.BLACK){
					array[i][j] = 0;
				}
				else{
					array[i][j] = 1;
				}
			}
		}
		return array;
	}
	
	public static double[][]zoning(double[][] matrix, int noOfZones){
		int length = matrix[0].length; 
		int zoneSize = length/noOfZones;
		double[][] zonedMatrix = new double[noOfZones][noOfZones];
		for(int i=0; i<length; i++){
			for(int j=0; j<length; j++){
//				System.out.println(i/zoneSize + " " + j/zoneSize);
				if(matrix[i][j] == 0){
					zonedMatrix[i/zoneSize][j/zoneSize]++;
				}
			}
		}
	/*	for(int i=0;i<zonedMatrix.length;i++)
			for(int j=0;j<zonedMatrix[i].length;j++)
				System.out.println(zonedMatrix[i][j]);*/
		return zonedMatrix;
	}
	
	public static double[] linearize(double[][] matrix){
		int width = matrix[0].length; 
		int heigth = matrix.length;
		int count = 0;
		double[] array = new double[width*heigth];
		for(int i=0; i<heigth; i++){
			for(int j=0; j<width; j++){
				array[count++] = matrix[i][j];
			}
		}
		double max=-1;
		for(int i=0;i<array.length;i++)
			if(array[i]>max){max=array[i];}
		
		for(int a=0;a<array.length;a++)
			array[a]/=max;
		return array;
		
	}

	public static int[] linearizeInt(double[][] matrix){
		int width = matrix[0].length; 
		int heigth = matrix.length;
		int count = 0;
		int[] array = new int[width*heigth];
		for(int i=0; i<heigth; i++){
			for(int j=0; j<width; j++){
				array[count++] = (int) matrix[i][j];
			}
		}
		return array;
		
	}
	
	public static double[] getPointFeature13(double[][]matrix){
		int height = matrix.length; 
		int width = matrix[0].length; 
		double[][] array = new double[4][2];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				if(matrix[i][j] == 0){
					array[i/8][j/16]++; 
				}
			}
		}
		double[] featurePointArray = new double[13];
		double [] f = linearize(array);
		for(int i=0; i<f.length; i++){
			featurePointArray[i] = f[i];
		}
		featurePointArray[8]= array[1][0] + array[1][1];
		featurePointArray[9] = array[2][0] + array[2][1];
		double f11 =0 ;
		double f12 =0 ;
		for(int i=0; i<4; i++){ 
			featurePointArray[10] =+ array[i][0];
			featurePointArray[11] =+ array[i][1];
		}
		featurePointArray[12] = f11 + f12;
		/*
		for(int i=0;i<featurePointArray.length;i++)
			System.out.println(featurePointArray[i]);*/
		return featurePointArray;
	}
	
	public static double[] initializeArray(int length, int number){
		double[] array = new double[length];
		for(int i=0; i<length; i++){
			array[i] = number; 
		}
		return array;
	}
	
	public static double[] concatenateArrays(double[] ... arrays){
		int totalSize = 0;
		for(double[] array: arrays){
			totalSize += array.length;
		}
		double[] concatenatedArray = new double[totalSize];
		int count=0;
		for(double[] array: arrays){
			for(int i=0; i<array.length; i++){
				concatenatedArray[count++] = array[i];
			}
		}
		return concatenatedArray;
	}
	
	public static double[] getContourFeature(double[][] matrix){
		int length = matrix.length;
		double[] x1array = initializeArray(length, length);//new double[length];
		double[] x2array = new double[length];
		double[] y1array = initializeArray(length, length);//new double[length];
		double[] y2array = new double[length];
		
		for(int i=0; i<length; i++){
			for(int j=0; j<length; j++){
				if(matrix[i][j] == 0){
					if(i>y2array[j]){
						y2array[j] = i; 
					}
					if(j>x2array[i]){
						x2array[i] = j;
					}
					if(i<y1array[j]){
						y1array[j] = i;
					}
					if(j<x1array[i]){
						x1array[i] = j;
					}
				}
			}
		}
		double[] concatenatedArray = concatenateArrays(x1array, x2array, y1array, y2array);
		/*for(int i=0;i<concatenatedArray.length;i++)
			System.out.println(concatenatedArray[i]);*/
		return concatenatedArray;
	}
	
	public static double[] getHistogramAxes(double[][] matrix){
		int dimension = matrix.length;
		double[] array = new double[dimension *2 ];
		double[] xaxis = new double[dimension];
		double[] yaxis = new double[dimension];
		
		for(int i=0; i<dimension; i++){
			for(int j=0; j<dimension; j++){
				if(matrix[i][j] == 0){
					xaxis[i] ++;
					yaxis[j] ++;
				}
			}
		}
		for(int i=0; i<dimension; i++){
			array[i+13] = xaxis[i];
		}
		for(int j=0; j<dimension; j++){
			array[j+13] = yaxis[j];
		}
		
		
		for(int i=0;i<array.length;i++)
			System.out.println(array[i]);
		return array;
	}
	/**
	 * @param args
	 * @throws java.net.URISyntaxException
	 * @throws java.io.IOException
	 */
	/*public static void main(String args[]) throws URISyntaxException, IOException{

		
		
	

			int noOfZones = 4;

				{
				
					 BufferedImage image = null;
					try
					    {
					      
					       image = ImageIO.read(new File("C:/Users/Himaa/Desktop/characters/characters/a (12).png"));//26,4
					      
					       
					      // work with the image here ...
					    } 
					    catch (Exception e)
					    {
					    	System.out.println("Error with input image pixels");
					    }
	//				Resize the image
					image = ImageResize.resize(image);*/
					
	//				Binary thinning algorithm
//					image = thinnedImage(image);
				//	String output = filename  +".png";
	//				ImageIO.write(image, "png", new File("/" + folderName + "/" +output));
				//	ImageIO.write(image, "png", new File(OutputFolder + "\\" + output));
				//	double[][]matrix = getMatrix(image);
					
			 //     	Haralick feature
			      //	double[] haralickFeatures = getHaralick(image, true);
					//featureObject.addFeature(haralickFeatures);
					
//					Connected Component
//					ConnectComponent cc = new ConnectComponent();
//					Dimension d = new Dimension();
//					d.height = matrix.length; 
//					d.width = matrix.length;
//					cc.compactLabeling(linearizeInt(matrix), d, true);
//					int holes = cc.getMaxLabel();
//					featureObject.addFeature(holes);
//					System.out.println("holes: "  + holes + " " + i);
					
//					Contour Feature work
			//	double[] countourFeature = getContourFeature(matrix);
//					featureObject.addFeature(countourFeature);
					
//					Histogram feature work
				//	double[] histogramAxes = getHistogramAxes(matrix);
//					featureObject.addFeature(histogramAxes);
					
//					13 Point Features work
			//	double[] pointFeature13 = getPointFeature13(matrix);
//					featureObject.addFeature(pointFeature13);
					
	//				Histogram feature prob
//					double[] histogramFeatures = getHistogram(image, false);
//					featureObject.addFeature(histogramFeatures);
					
//	//				Raw moments
			/*		Moments moments = new Moments();
					double rawM00 = Moments.getRawMoment(0, 0, matrix);
					double rawM10 = Moments.getRawMoment(1, 0, matrix);
					double rawM01 = Moments.getRawMoment(0, 1, matrix);
					double rawM11 = Moments.getRawMoment(1, 1, matrix);
					System.out.println("rawM00: " + rawM00 + " rawM10: " + rawM10 + " rawM011: " + rawM01);
			//		featureObject.addFeature(rawM00, rawM10, rawM10);
					
//					centralmoment 00 = rawmoment 00/ central moment 01 = phi/
////					Covariance and Normal Central Moment
			        double normCM10 = Moments.getNormalizedCentralMoment(1, 0, matrix);
	//		        System.out.println("normCM10: " + normCM10);
			        double covariance = Moments.getCovarianceXY(0, 0, matrix);
			        System.out.println("covariance: " + covariance);
			     //   featureObject.addFeature(normCM10, covariance);
			        */
	//		        Zoning
			     //   double[][]zonedMatrix = zoning(matrix,noOfZones);
			      //  double[] linearZonedMatrix = linearize(zonedMatrix);
			   //     featureObject.addFeature(linearZonedMatrix);
			        
//	//		        HU moments
//			        double [] huM = new double[6];
//			        for(int j=1; j<7; j++){
//			        	huM[j-1] =  moments.getHuMoment(matrix, j);
//	//		        	System.out.println("HUMoment: " + huM[j-1]);
//			        }
//			        featureObject.addFeature(huM);
			        
	//		        Scale invariant Transform feature
	//		        double[] sift = getSift(image, true);
	//		        featureObject.addFeature(sift);
			        
	//		        Tamura(Based on Human Perception)
	//		        double[] tamura = getTamura(image, true);
	//		        featureObject.addFeature(tamura);
			        
	//		        Eccentricity
//			        double[] eccentricity = getEccentricity(image, false);
//			        featureObject.addFeature(eccentricity);
			        
	//				Original Image
//					featureObject.addFeature(linearize(matrix));
			  //      featureObject.addClass(Integer.valueOf(classname));
			   //     featureObject.addNewFeature();
				//}
			}
		//	featureObject.createArffFile(featureObject.getHeader(), featureObject.getFeatures());
//		}
		//long endTime = System.currentTimeMillis();
	//	System.out.println("Total time for which the program ran: " + (endTime - startTime));		
	

