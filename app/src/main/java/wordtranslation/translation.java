package wordtranslation;

/**
 * Created by kamal on 3/8/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.art.augmentedrealitytranslator_gp.R;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
public class translation  extends Activity implements SpellCheckerSession.SpellCheckerSessionListener {

    Button button;
    TextView textView;
    TextView textView2;
    private SpellCheckerSession mScs;
    EditText editText;
    String translatedText;
    String corrected_Text= " ";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translation);
        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.edittext1);
        textView2 = (TextView)findViewById(R.id.textView2);
    }
    public void onResume() {
        super.onResume();
        final TextServicesManager tsm = (TextServicesManager) getSystemService(
                Context.TEXT_SERVICES_MANAGER_SERVICE);
        mScs = tsm.newSpellCheckerSession(null, null, this, true);
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mScs != null) {
            mScs.close();
        }
    }
    public void go(View view){
        Toast.makeText(getApplicationContext(), editText.getText().toString(),
                Toast.LENGTH_SHORT).show();

        //input string  
        String input = editText.getText().toString();
        String[] words = input.split(" ");
        for(int i=0;i<words.length;i++)
        {
            mScs.getSuggestions(new TextInfo(words[i]), 1);
        }
    }
    public void onGetSuggestions(final SuggestionsInfo[] arg0) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arg0.length; ++i) {
            // Returned suggestions are contained in SuggestionsInfo
            final int len = arg0[i].getSuggestionsCount();
            sb.append('\n');
            for (int j = 0; j < len; ++j)
            {
                sb.append("," + arg0[i].getSuggestionAt(j));
            }
            sb.append(" (" + len + ")");
        }
        runOnUiThread(new Runnable() {
            public void run() {
                for(int i=0;i<arg0.length;i++ )
                {
                    corrected_Text +=(arg0[i].getSuggestionAt(0));
                    corrected_Text +=" ";
                }
                textView.setText(corrected_Text);
                new MyAsyncTask() {
                    protected void onPostExecute(Boolean result) {
                        textView2.setText(translatedText);
                    }
                }.execute();
            }
        });
    }
    class MyAsyncTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Void... arg0) {
            Translate.setClientId("KamalAbdelKader");
            Translate.setClientSecret("1F5EBm6jyQheV518t8vss2in3OGvkjIufqif/KEDMc8=");
            try {
		//translated text here(after auto correction
                translatedText = Translate.execute(corrected_Text, Language.ENGLISH, Language.ARABIC);

            } catch (Exception e) {
                e.printStackTrace();
                translatedText = e.toString();
            }
            return null;
        }
    }
    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] arg0) {
        // TODO Auto-generated method stub

    }
}