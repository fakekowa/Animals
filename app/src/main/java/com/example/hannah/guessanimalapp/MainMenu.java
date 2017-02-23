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
import android.widget.ImageView;



public class MainMenu extends ActionBarActivity {

    ImageButton menuPop;
    Button extraButton, libraryButton, startButton;
    ImageView imageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        extraButton = (Button) findViewById(R.id.extras_button);
        libraryButton = (Button) findViewById(R.id.library_button);
        startButton = (Button) findViewById(R.id.start_button);
        imageLogo = (ImageView) findViewById(R.id.imageView);
        imageLogo.getLayoutParams().width = 270;


        //opens a popup menu when clicking the menu icon

        menuPop = (ImageButton) findViewById(R.id.menu_logo);
        menuPop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                PopupMenu pop = new PopupMenu(MainMenu.this, menuPop);
                pop.getMenuInflater().inflate(R.menu.popup_menu, pop.getMenu());


                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.sound_settings:
                                Intent intent = new Intent(MainMenu.this, SoundActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.language_settings:
                                String value = "mainMenu";
                                Intent intent2 = new Intent(MainMenu.this, LangActivity.class);
                                intent2.putExtra("curr_act", value);
                                startActivity(intent2);
                                break;
                            case R.id.help_settings:
                                Intent intent3 = new Intent(MainMenu.this, HelpActivity.class);
                                startActivity(intent3);
                                break;
                        }

                        //Toast.makeText(MainMenu.this,"PLSSSS u clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();

                       // ta bort kommentarer builder.show();
                        return true;
                    }
                } );

                pop.show();//showing popup menu
            }
        });


        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish_activity"));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

    public void toStart(View view) {
        Intent intent = new Intent(MainMenu.this, StartPlayActivity.class);
        startActivity(intent);
    }

    public void toLib(View view) {
        Intent intent = new Intent(MainMenu.this, LibActivity.class);
        startActivity(intent);
    }

    public void extraClick(View view) {
        Intent intent = new Intent(MainMenu.this, ExtrasActivity.class);
        startActivity(intent);
    }

}
