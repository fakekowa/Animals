package com.example.hannah.guessanimalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class ExtrasActivity extends AppCompatActivity {

    ImageButton menuPop;
    Button packageButton;
    ImageView imageLogo;
    TextView extrasText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);

        imageLogo = (ImageView) findViewById(R.id.extrasLogo);
        imageLogo.getLayoutParams().width = 270;
        extrasText = (TextView) findViewById(R.id.extras_textView);

        packageButton = (Button) findViewById(R.id.extras_button);
        packageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExtrasActivity.this,"Yikes! You will be able to get packages in the future!", Toast.LENGTH_LONG).show();
            }
        });

        menuPop = (ImageButton) findViewById(R.id.menu_logo);
        menuPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu pop = new PopupMenu(ExtrasActivity.this, menuPop);
                pop.getMenuInflater().inflate(R.menu.popup_menu, pop.getMenu());


                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.sound_settings:
                                Intent intent = new Intent(ExtrasActivity.this, SoundActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.language_settings:
                                String value = "mainMenu";
                                Intent intent2 = new Intent(ExtrasActivity.this, LangActivity.class);
                                intent2.putExtra("curr_act", value);
                                startActivity(intent2);
                                break;
                            case R.id.help_settings:
                                Intent intent3 = new Intent(ExtrasActivity.this, HelpActivity.class);
                                startActivity(intent3);
                                break;
                        }

                        return true;
                    }
                } );

                pop.show();//showing popup menu
            }
        });
    }


}
