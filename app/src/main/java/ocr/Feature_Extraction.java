package ocr;

import java.util.List;


public class Feature_Extraction {
	  private static int posx, posy, orginali, orginalj;
      private static int pxl,pxl2;
      private static int check1, check2, check3,check4;
	
 
	//get first white pixel and break 
	 public static void Get_first_White(int [][]bit,int width,int height)

	{
		 posx = 0; posy = 0;

         for (int i = height-1; i >0; i--)
         {
             for (int j = 0; j < width; j++)
             {
                 //Color c;
                // c = bit.GetPixel(j, i);
                 if (bit [j][i]==1)
                 {
                      System.out.print("first pixels is"+i+""+j);
                     posy = i; posx = j;
                     orginali = j; orginalj = i;
                     break;
                 }
             }
             if (posx != 0 && posy != 0) { break; }  
 


         }
	
	
	
	
	
	}
	 // clock wise get feature vector
	 @SuppressWarnings("null")
	public static void Get_FEATURE(int [][]bit,List<Integer>  feature)
	 {
		 //save current color (White/black)
        int  current ;
         
         
         
         do
         {
        	 current = bit[posx][ posy];
             get_neiup(bit,posx,posy);
             get_niehol(bit, posx, posy);
             while ((check1 ==1 ||  check2 == 1 || check3 == 1)&&check4==0)
             {
                 if ((current==1) && (check1 == 1))
                 {

                    //   bit.SetPixel(posx, posy,white);
                     posx--;
                     posy--;
                     feature.add(3);
                     current = bit[posx][ posy];
                     get_neiup(bit, posx, posy);
                     get_niehol(bit, posx, posy);
                     
                 }
                 else if ((current==1) && (check2 == 1))
                 {
                   //  bit.SetPixel(posx, posy, white);
                     posy--;
                     feature.add(3);
                     current = bit[posx][ posy];
                     get_neiup(bit, posx, posy);
                     get_niehol(bit, posx, posy);
                 }
                 else if ((current == 1) && (check3 == 1))
                 {
                  //   bit.SetPixel(posx, posy, white);
                     posx++;
                     posy--;
                     feature.add(3);
                     current = bit[posx][ posy];
                     get_neiup(bit, posx, posy);
                     get_niehol(bit, posx, posy);
                 }
             }


             current = bit[posx][ posy];
            get_neihr(bit,posx,posy);
            get_nieveup(bit, posx, posy);
            while ((check1 == 1 || check2 == 1 || check3 == 1)&&check4==0)
         {
             if ((current == 1) && (check1 == 1))
            {

            //    bit.SetPixel(posx, posy, white);
                posx++;
                posy--;
                feature.add(1);
                current = bit[posx][ posy];
                get_neihr(bit, posx, posy);
                get_nieveup(bit, posx, posy);
            }
             else if ((current == 1) && (check2 == 1)) 
            {
             //   bit.SetPixel(posx, posy, white);
                posx++;
                feature.add(1);
                current = bit[posx][ posy];
                get_neihr(bit, posx, posy);
                get_nieveup(bit, posx, posy);
            }
             else if ((current == 1) && (check3 == 1))
            {
              //  bit.SetPixel(posx, posy, white);
                posx++;
                posy++;
                feature.add(1);
                current = bit[posx][ posy];
                get_neihr(bit, posx, posy);
                get_nieveup(bit, posx, posy);
            }
         }


            current = bit[posx][ posy];
               get_neivdo(bit,posx,posy);
               get_niehor(bit, posx, posy);
         while((check1==1||check2==1||check3==1)&&check4==0)
         {
             if ((current == 1) && (check1 == 1))
               {

               //    bit.SetPixel(posx, posy, white);
                   posx++;
                   posy++;
                   feature.add(7);
                   current = bit[posx][ posy];
                   get_neivdo(bit, posx, posy);
                   get_niehol(bit, posx, posy);
               }
             else if ((current == 1) && (check2 == 1))
               {
                  // bit.SetPixel(posx, posy, white);
                   posy++;
                   feature.add(7);
                   current = bit[posx][ posy];
                   get_neivdo(bit, posx, posy);
                   get_niehol(bit, posx, posy);

               }
             else if ((current == 1) && (check3 == 1))
               {
                 //  bit.SetPixel(posx, posy, white);
                   posx--;
                   posy++;
                   feature.add(7);
                   current = bit[posx][ posy];
               get_neivdo(bit,posx,posy);
               get_niehol(bit, posx, posy);

               }
         }

         current = bit[posx][ posy];
          get_neileft(bit,posx,posy);
          get_nieverdo(bit, posx, posy);
         while((check1==1||check2==1||check3==1)&&check4==0)
         {
          if (posx == orginali && posy == orginalj)
          { break; }
          else if ((current == 1) && (check1 == 1))
          {

             // bit.SetPixel(posx, posy, white);
              posx--;
              posy++;
              feature.add(5);
              current = bit[posx][ posy];
              get_neileft(bit, posx, posy);
              get_nieverdo(bit, posx, posy);

          }
          else if ((current == 1) && (check2 == 1))
          {
            //  bit.SetPixel(posx, posy, white);
              posx--;
              feature.add(5);
              current = bit[posx][ posy];
              get_neileft(bit, posx, posy);
              get_nieverdo(bit, posx, posy);
          
          }
          else if ((current == 1) && (check3 == 1))
          {
           //   bit.SetPixel(posx, posy, white);
              posx--;
              posy--;
              feature.add(5);
              current = bit[posx][ posy];
              get_neileft(bit, posx, posy);
              get_nieverdo(bit, posx, posy);
          }

      }


     
       

             
             



             
             } while (posx != orginali || posy != orginalj);//start from first while pixel and go around the contour and back to same pixel
		 
	 
	 }
         
	 public static void get_neiup(int [][] bit, int posx, int posy)
     {
         check1 = 0; check2 = 0; check3 = 0;
         pxl = bit[posx][ posy - 1];
         if (pxl==1)
             check2 = 1;
          pxl2 = bit[posx - 1][ posy - 1];
          if (pxl2 == 1)
              check1 = 1;
          pxl2 = bit[posx + 1][ posy - 1];
          if (pxl2 == 1)
              check3 = 1;
            
     }
     public static void get_neileft(int [][] bit, int posx, int posy)
     {
         check1 = 0; check2 = 0; check3 = 0;
         pxl = bit[posx - 1][ posy];
         if (pxl == 1)
             check2 = 1;
         pxl2 = bit[posx - 1][ posy - 1];
         if (pxl2 == 1)
             check3 = 1;
         pxl2 = bit[posx - 1][ posy + 1];
         if (pxl2 == 1)
             check1 = 1;
         

             

     }
 

     public static void get_neihr(int [][] bit, int posx, int posy)
     {
         check1 = 0; check2 = 0; check3 = 0;
         pxl = bit[posx + 1][ posy];
         if (pxl == 1)
             check2=1;
          pxl = bit[posx + 1][ posy - 1];
          if (pxl == 1)
              check1 = 1;
          pxl = bit[posx + 1][ posy + 1];
          if (pxl == 1)
             check3 = 1;
           
     }

    
     public static void get_neivdo(int  [][]bit, int posx, int posy)
     {

         check1 = 0; check2 = 0; check3 = 0;

         pxl = bit[posx][ posy + 1];
         if (pxl == 1) check2 = 1;
          pxl = bit[posx - 1][ posy + 1];
          if (pxl == 1) check3 = 1;
         pxl = bit[posx + 1][ posy + 1];
         if (pxl == 1) check1 = 1;
            
     }
     public static void get_niehor(int [][] bit, int posx, int posy)
     {

         check4 = 1;

         pxl = bit[posx+1][ posy ];
         if (pxl == 0) 
         check4 = 0; 
        
     }
     public static void get_nieveup(int [][] bit, int posx, int posy)
     {

         check4 = 1;

         pxl = bit[posx ][ posy-1];
         if (pxl == 0) 
         check4 = 0;

     }
     public static void get_nieverdo(int [][] bit, int posx, int posy)
     {

         check4 = 1;

         pxl = bit[posx ][ posy+1];
         if (pxl == 0) 
         check4 = 0;

     }
     public static void get_niehol(int [][] bit, int posx, int posy)
     {

         check4 = 1;

         pxl = bit[posx - 1][ posy];
         if (pxl == 0) 
         check4 = 0; 

     }

	 
	 
	 
	 
	 
	 

}
