package com.example.hannah.guessanimalapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;


public class StartPlayActivity extends ActionBarActivity {


    ImageButton menuPop;
    Button easyButton, mediumButton, advButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_play);

        easyButton = (Button) findViewById(R.id.easy_button);
        mediumButton = (Button) findViewById(R.id.medium_button);
        advButton = (Button) findViewById(R.id.advanced_button);

        menuPop = (ImageButton) findViewById(R.id.menu_logo);
        menuPop.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {

                PopupMenu pop = new PopupMenu(StartPlayActivity.this, menuPop);
                pop.getMenuInflater().inflate(R.menu.popup_menu, pop.getMenu());


                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.sound_settings:
                                Intent intent = new Intent(StartPlayActivity.this, SoundActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.language_settings:
                                String value = "startPlay";
                                Intent intent2 = new Intent(StartPlayActivity.this, LangActivity.class);
                                intent2.putExtra("curr_act", value);
                                startActivity(intent2);
                                break;
                            case R.id.help_settings:
                                Intent intent3 = new Intent(StartPlayActivity.this, HelpActivity.class);
                                startActivity(intent3);
                                break;
                        }
                        return true;
                    }
                });
                pop.show();//showing popup menu
            }
        });

        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity_play")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish_activity_play"));

    }

    public void easyPlay(View view) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        startActivity(intent);
    }

    public void mediumPlay(View view) {
        Intent intent = new Intent(this, MediumPlay.class);
        startActivity(intent);
    }

    public void advPlay(View view) {
        Intent intent = new Intent(this, MediumPlay.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_play, menu);
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
