package com.example.hannah.guessanimalapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ThreadLocalRandom;


public class PlayGameActivity extends ActionBarActivity {

    Button playButton;
    int ranNum;
    //Experimental shitznitz 2017-01-30
    int counter;
    MediaPlayer mp = new MediaPlayer();

//Hejsan Pontus
    //http://stackoverflow.com/questions/6945678/storing-r-drawable-ids-in-xml-array

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        final Intent refresh = new Intent(this, PlayGameActivity.class);
        playButton = (Button) findViewById(R.id.play_button);

        TypedArray imgs = getResources().obtainTypedArray(R.array.random_imgs);
        TypedArray buttonImgs = getResources().obtainTypedArray(R.array.button_imgs);
        TypedArray buttonSound = getResources().obtainTypedArray(R.array.random_sound);

        //Hannah
        final TypedArray buttonDesc = getResources().obtainTypedArray(R.array.random_text);
        final TypedArray buttonTitle = getResources().obtainTypedArray(R.array.random_title);
        //        en boolean array lika lång som imgs arrayen för att undvika att randomisera samma position 2 ggr
        boolean[] ranArray = new boolean[imgs.length()];
        //Random int mellan 0-4 som används en gång under forloopen för att tilldela en av knapparna ett ljud
        int ranSound = ThreadLocalRandom.current().nextInt(0, 4);
        //Experimental shitznitz 2017-01-30
        counter = 0;
        //Int som innehåller vilka djur som skapas, för att skicka till lib att låsa upp, skickas med intent
        //Forloop för att skapa 4 knappar (buttonImgs.length är 4 alltid)
        for(int i = 0; i < buttonImgs.length();) {
            //int ranNum slumpar 0-längden av antalet djur och tilldelar djuret i ranNums position i arrayen till knappen
            ranNum = ThreadLocalRandom.current().nextInt(0, imgs.length());
            final int a = ranNum;
            //Skapa en ny imagebutton objekt.
            ImageButton img = (ImageButton) findViewById(buttonImgs.getResourceId(i, -1));
//            TextView animalText = (TextView) findViewById(buttonDesc.getResourceId(i, -1));
            //Så länge ranArray[ranNum] är satt till falskt dvs aldrig använts så gå in här
            if(ranArray[ranNum] == false) {
                //En av gångerna kommer siffran i loopen stämma överrens med siffran i ranSound, gör detta till vinnarknappen
                if(i == ranSound) {
                    //Skapa vinnande ljudet & vinnande gå till nästa spel
                    mp = MediaPlayer.create(this, buttonSound.getResourceId(ranNum, -1));
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Experimental shitznitz 2017-01-30
                            counter++;
                            //på onClick skapas en dialog
                            AlertDialog.Builder dialog = new AlertDialog.Builder(PlayGameActivity.this);
                            //dialogen är möjlig att cancel-era
                            dialog.setCancelable(true);
                            //dialogen hämtar rätt rad i random_text array och sätter som meddelande i dialogen
                            //int a är en referens till ranNum, att anv ranNum blir en random text så icke
                            dialog.setMessage(buttonDesc.getResourceId(a, -1));
                            //sätter titel på dialogfönstret
                            dialog.setTitle(buttonTitle.getResourceId(a, -1));
                            //sätter en positiv knapp, när den klickas refreshas aktiviteten - nytt spel
                            //rätt språk av string hämtas
                            dialog.setPositiveButton(getString(R.string.next_game), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                    startActivity(refresh);
                                }
                            });
                            //Experimental shitznitz 2017-01-30
                            if(counter == 3) {
                                AlertDialog win = dialog.create();
                                win.setTitle("Du har vunnit och låst upp nya djur i ditt bibliotek!");
                                win.setMessage("Vill du fortsätta eller vill du sluta spela?");
                                win.show();
                                mp.start();
                                mp.release();
                                finish();
                            }
                            AlertDialog alert = dialog.create();
                            alert.show();
                            mp.start();
                            mp.release();
                        }
                    });
                    playButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mp.start();
                        }
                    });

                }
                i++;
                //sätt bild och id på knappen till nuvarande djur
                img.setImageResource(imgs.getResourceId(ranNum, -1));
                img.setId(imgs.getResourceId(ranNum, -1));
            }
            //Gör nuvarande djurposition i arrayen till true så den inte används igen.
            ranArray[ranNum] = true;
        }


//
//        for(int i = 0; i < buttonImgs.length(); i++) {
//            ImageButton img = (ImageButton) findViewById(buttonImgs.getResourceId(i, -1));
//            img.setImageResource(imgs.getResourceId(i, -1));
//            img.setId(imgs.getResourceId(i, -1));
//        }

//        image1.setImageResource(imgs.getResourceId(0, -1));
//        image2.setImageResource(imgs.getResourceId(1, -1));
//        image3.setImageResource(imgs.getResourceId(2, -1));
//        image4.setImageResource(imgs.getResourceId(3, -1));

//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.catmeow);
//        playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mp.start();
//            }
//        });

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
}
