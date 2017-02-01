package com.example.hannah.guessanimalapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;


public class SoundActivity extends ActionBarActivity {

    //SeekBar and text letting the user know the music volume
    // when adjusting the volume bar

    SeekBar seekBar;
    TextView mediaText, mediaText2;
    Button ok, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        mediaText = (TextView)findViewById(R.id.mediaText);
        mediaText2 = (TextView)findViewById(R.id.mediaText2);

        ok = (Button) findViewById(R.id.okButton);
        cancel = (Button) findViewById(R.id.cancelButton);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                mediaText.setText("Media Volume:");
                mediaText2.setText(i +"/20");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    //closes the current activity when cancel/ok are clicked
    //navigates back to main menu
    public void okClick(View view) {
        finish();
    }

    public void cancelClick(View view) {
        finish();
    }

    //när vi ska kika på hur vi connectar ljuden
    //http://stackoverflow.com/questions/10134338/using-seekbar-to-control-volume-in-android

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sound, menu);
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
