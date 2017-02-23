package com.example.hannah.guessanimalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;


public class PlayGameActivity extends AppCompatActivity {
    MediaPlayer mp = new MediaPlayer();
    //http://stackoverflow.com/questions/6945678/storing-r-drawable-ids-in-xml-array
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        final DBHelper ourDBHelper = new DBHelper(this);

        try {

            ourDBHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            ourDBHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }


        Button playButton = (Button) findViewById(R.id.play_button);

        final TypedArray imgs = getResources().obtainTypedArray(R.array.random_imgs);

        final TypedArray buttonImgs = getResources().obtainTypedArray(R.array.button_imgs);

        final TypedArray buttonSound = getResources().obtainTypedArray(R.array.random_sound);

        final TypedArray buttonDesc = getResources().obtainTypedArray(R.array.random_text);

        final TypedArray buttonTitle = getResources().obtainTypedArray(R.array.random_title);

        boolean[] ranArray = new boolean[imgs.length()];


        int winSound = ThreadLocalRandom.current().nextInt(0, 4);

        for (int i = 0; i < buttonImgs.length(); ) {             //Create 4 buttons
            int ranNum = ThreadLocalRandom.current().nextInt(0, imgs.length());
            ImageButton img = (ImageButton) findViewById(buttonImgs.getResourceId(i, -1));

            //As long as ranArray[ranNum] is false, go into this.
            if (!ranArray[ranNum]) {

                if (i == winSound) {
                    winSound(buttonSound, ranNum, img, buttonDesc, buttonTitle);
                    playButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mp.start();
                        }
                    });

                }
                img.setImageResource(imgs.getResourceId(ranNum, -1));
                img.setId(imgs.getResourceId(ranNum, -1));
                i++;
            }
            //Gör nuvarande djurposition i arrayen till true så den inte används igen.
            ranArray[ranNum] = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_game, menu);
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
    public void winSound(TypedArray buttonSound, final int ranNum, ImageButton img, final TypedArray buttonDesc, final TypedArray buttonTitle) {
        final Intent refresh = new Intent(this, PlayGameActivity.class);
        mp = MediaPlayer.create(this, buttonSound.getResourceId(ranNum, -1));
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Experimental shitznitz 2017-01-30
                //på onClick skapas en dialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(PlayGameActivity.this);
                //dialogen är möjlig att cancel-era
                dialog.setCancelable(true);
                //dialogen hämtar rätt rad i random_text array och sätter som meddelande i dialogen
                //int a är en referens till ranNum, att anv ranNum blir en random text så icke
                dialog.setMessage(buttonDesc.getResourceId(ranNum, -1));
                //sätter titel på dialogfönstret
                dialog.setTitle(buttonTitle.getResourceId(ranNum, -1));
                //sätter en positiv knapp, när den klickas refreshas aktiviteten - nytt spel
                //rätt språk av string hämtas
                dialog.setNegativeButton(getString(R.string.exit_game), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dialog.setPositiveButton(getString(R.string.next_game), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        startActivity(refresh);
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
                mp.start();
                mp.release();
            }

        });
    }
}