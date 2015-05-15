package character_recognition;

import android.content.Context;
import android.graphics.Bitmap;

import feature_extraction.MainFile;
import multilayerperceptron.BackPropagation;

/**
 * Created by Islam on 4/26/2015.
 */
public class CharRecog {
    private  static BackPropagation pg_2 = null;


    public static void init_detector(Context con)
    {
        pg_2= new BackPropagation(con);
        pg_2.set_weights("train.txt");

    }
    public static char test(Bitmap image)
    {
        Bitmap resized = Bitmap.createScaledBitmap(image, 24, 24, true);
        double[][] pixels = MainFile.getMatrix(resized);
        double[][] zonedMatrix = MainFile.zoning(pixels,12);
        double[] linearZonedMatrix = MainFile.linearize(zonedMatrix);
        int winner = pg_2.test(linearZonedMatrix);
        return conTochar(winner);
    }
    private static char conTochar(int winner)
    {
        char ch = 0;
        if(winner==0){ch='0';}
        else if(winner==1){ch='1';}
        else if(winner==2){ch='2';}
        else if(winner==3){ch='3';}
        else if(winner==4){ch='4';}
        else if(winner==5){ch='5';}
        else if(winner==6){ch='6';}
        else if(winner==7){ch='7';}
        else if(winner==8){ch='8';}
        else if(winner==9){ch='9';}
        else if(winner==10||winner==36	){ch='a';}
        else if(winner==11||winner==37){ch='b';}
        else if(winner==12||winner==38){ch='c';}
        else if(winner==13||winner==39){ch='d';}
        else if(winner==14||winner==40){ch='e';}
        else if(winner==15||winner==41){ch='f';}
        else if(winner==16||winner==42){ch='g';}
        else if(winner==17||winner==43){ch='h';}
        else if(winner==18||winner==44){ch='i';}
        else if(winner==19||winner==45){ch='j';}
        else if(winner==20||winner==46){ch='k';}
        else if(winner==21||winner==47){ch='l';}
        else if(winner==22||winner==48){ch='m';}
        else if(winner==23||winner==49){ch='n';}
        else if(winner==24||winner==50){ch='o';}
        else if(winner==25||winner==51){ch='p';}
        else if(winner==26||winner==52){ch='q';}
        else if(winner==27||winner==53){ch='r';}
        else if(winner==28||winner==54){ch='s';}
        else if(winner==29||winner==55){ch='t';}
        else if(winner==30||winner==56){ch='u';}
        else if(winner==31||winner==57){ch='v';}
        else if(winner==32||winner==58){ch='w';}
        else if(winner==33||winner==59){ch='x';}
        else if(winner==34||winner==60){ch='y';}
        else if(winner==35||winner==61){ch='z';}


        return ch;
    }
}
