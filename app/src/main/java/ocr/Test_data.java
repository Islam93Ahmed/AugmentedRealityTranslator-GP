package ocr;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test_data extends Activity {

    public static Context context;

    public static String recognize(List<List<Bitmap>> words_char, Context con) {
        context = con;
        String text = "";
        for (int word_num = 0; word_num < words_char.size(); word_num++) {
            List<Bitmap> characters = words_char.get(word_num);
            for (int char_num = 0; char_num < characters.size(); char_num++) {
                Bitmap char_image = characters.get(char_num);
                String ch = test(char_image);
                text += ch;
            }
        }
        return text;
    }

    private static String test(Bitmap test_image) {
        List<Integer> test_feature = new ArrayList<Integer>();
        String ch = "";

        int w = test_image.getWidth();
        int h = test_image.getHeight();

        int[][] imageData = new int[h][w];
        int[][] imageData1 = new int[w][h];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {

                if (test_image.getPixel(x, y) == Color.BLACK) {
                    imageData[y][x] = 1;
                }
                else
                {
                    imageData[y][x] = 0;
                }
            }
        }

        for (int y = 0; y < imageData.length; y++) {

            for (int x = 0; x < imageData[y].length; x++) {

                if (imageData[y][x] == 0) {
                    test_image.setPixel(x, y, Color.WHITE);
                    imageData1[x][y] = imageData[y][x];
                } else {
                    test_image.setPixel(x, y, Color.BLACK);
                    imageData1[x][y] = imageData[y][x];
                }
            }
        }
        Feature_Extraction.Get_first_White(imageData1, w, h);
        Feature_Extraction.Get_FEATURE(imageData1, test_feature);
        try {
            List<Integer> new_feature = new ArrayList<Integer>();
            if (test_feature.size() > 15) {

                for (int i = 0; i < test_feature.size() - 1; i++) {
                    if (test_feature.get(i) != test_feature.get(i + 1)) {
                        new_feature.add(test_feature.get(i));
                    }
                }
                new_feature.add(test_feature.get(test_feature.size() - 1));
                test_feature.clear();

                PATTERN_REDUCTION.REDUCTION(new_feature, test_feature);
                new_feature.clear();
                for (int k = 0; k < test_feature.size() - 1; k++) {
                    if (test_feature.get(k) != test_feature.get(k + 1))
                        new_feature.add(test_feature.get(k));
                }
                new_feature.add(test_feature.get(test_feature.size() - 1));
                ch = check_test(new_feature);
                //System.out.print(ch);
            } else {
                for (int i = 0; i < test_feature.size() - 1; i++) {
                    if (test_feature.get(i) != test_feature.get(i + 1))
                        new_feature.add(test_feature.get(i));
                }
                new_feature.add(test_feature.get(test_feature.size()-1));

                ch = check_test(test_feature);
                //System.out.print(ch);
            }

        } catch (Exception e) {
            System.out.println("Error with input image pixels");
        }
        return ch;
    }

    @SuppressWarnings("resource")
    private static String check_test(List<Integer> feature) {
        String charac = "";
        String test_feature = "";
        //list to string
        for (int i = 0; i < feature.size(); i++) {
            test_feature += feature.get(i).toString();
        }

        //////////////////////////////////////////////////////////
        //Scanner fileScanner = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    context.getAssets().open("Digits.txt")));
            String line = "";
            do
            {
                //read numbers
                line = reader.readLine();
                //read character
                charac = reader.readLine();
                //match test with line from training
                if(line.length()==feature.size())

                    //if match then return character
                    if(test_feature.equals(line))
                    {
                        return charac ; // will be changed
                    }
            }while (line != null);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;

    }
}

