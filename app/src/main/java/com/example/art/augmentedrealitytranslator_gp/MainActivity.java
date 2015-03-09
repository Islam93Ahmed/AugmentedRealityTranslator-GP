package com.example.art.augmentedrealitytranslator_gp;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextServicesManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import org.w3c.dom.Text;

public class MainActivity extends ActionBarActivity implements OnClickListener, SpellCheckerSession.SpellCheckerSessionListener {
    Button Capture;
    Button Tranlate;
    ImageView image_view;
    Intent i;
    final static int cameraData = 0;
    Bitmap bmp;
    String translatedText = " ";

    TextView text;

    private SpellCheckerSession mScs;


    @Override
    public void onResume() {
        super.onResume();
        final TextServicesManager tsm = (TextServicesManager) getSystemService(
                Context.TEXT_SERVICES_MANAGER_SERVICE);
        mScs = tsm.newSpellCheckerSession(null, null, this, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
         addListenerOnButton();
    }

    private void initialize() {
        image_view = (ImageView) findViewById(R.id.ReturnedPic);
        Capture = new Button(this);
        Capture.setOnClickListener(this);
        Tranlate = (Button) findViewById(R.id.trans);
        text = (TextView) findViewById(R.id.translated);
    }

    @Override
    public void onClick(View v) {
        //testing Camera

        switch (v.getId()) {
            case R.id.TakePic:
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, cameraData);
                break;

/*
            case R.id.trans: {
                final Context context = this;
                Tranlate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, translation.class);
                        startActivity(intent);
                    }
                });
            }
            */
        }
    }
    @Override
    public void onGetSuggestions(final SuggestionsInfo[] results) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < results.length; ++i) {
            // Returned suggestions are contained in Suggesti
            final int len = results[i].getSuggestionsCount();
            sb.append('\n');
            for (int j = 0; j < len; ++j) {
                sb.append("," + results[i].getSuggestionAt(j));
            }
            sb.append(" (" + len + ")");
        }
        runOnUiThread(new Runnable() {
            public void run() {
                // mMainView.append(sb.toString());

                String fin = " ";
                for (int i = 0; i < results.length; i++) {
                    fin += (results[i].getSuggestionAt(0));
                }
                text.setText(fin);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bmp = (Bitmap) extras.get("data");
            image_view.setImageBitmap(bmp);

        }

    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {

    }
    public void addListenerOnButton() {

        final Context context = this;

        Tranlate = (Button) findViewById(R.id.trans);

        Tranlate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, translation.class);
                startActivity(intent);

            }

        });

    }




}
