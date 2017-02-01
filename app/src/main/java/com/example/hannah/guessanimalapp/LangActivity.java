package com.example.hannah.guessanimalapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;


public class LangActivity extends ActionBarActivity {

    RadioButton radioEng, radioSwe;
    RadioGroup rb;
    Button ok, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang);


        ok = (Button) findViewById(R.id.okButton);
        cancel = (Button) findViewById(R.id.cancelButton);
        rb = (RadioGroup) findViewById(R.id.radioLang);
        radioEng = (RadioButton) findViewById(R.id.button_english);
        radioSwe = (RadioButton) findViewById(R.id.button_swedish);


        //fullösning som ändå fungerar
        //sets the radio buttons to correct checked by comparing
        //a string of a radio button to swedish "Avbryt"
        if(cancel.getText().equals("Avbryt")) {
            radioSwe.setChecked(true);
        } else {
            radioEng.setChecked(true);
        }

        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(radioSwe.isChecked()) {

                    LanguageHelper.changeLocale(LangActivity.this.getResources(), "sv");
                    Toast.makeText(LangActivity.this, "svenska valt", Toast.LENGTH_LONG).show();


                } else if(radioEng.isChecked()) {

                    LanguageHelper.changeLocale(LangActivity.this.getResources(), "eng");
                    Toast.makeText(LangActivity.this, "engelska valt", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*
    * closes the current activity when button-ok is clicked
    * refreshes last used activity with language changes
    * overridePendingTransition(0, 0) makes transition between old
    * and refreshed activity smooth
    */
    public void okClick(View view) {
        finish();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if(bundle.getString("curr_act").equals("mainMenu")) {
                Intent refresh = new Intent(this, MainMenu.class);
                overridePendingTransition(0, 0);
                startActivity(refresh);

                Intent intent = new Intent("finish_activity");
                sendBroadcast(intent);

            } else if(bundle.getString("curr_act").equals("startPlay")) {
                Intent refresh2 = new Intent(this, StartPlayActivity.class);
                overridePendingTransition(0, 0);
                startActivity(refresh2);

                Intent intent2 = new Intent("finish_activity_play");
                sendBroadcast(intent2);

            }
        }
    }

    //cancels the current activity when button-cancel is clicked
    public void cancelClick(View view) {
        finish();
        if(cancel.getText().equals("Avbryt")) {
            radioSwe.setChecked(true);
        } else {
            radioEng.setChecked(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lang, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
