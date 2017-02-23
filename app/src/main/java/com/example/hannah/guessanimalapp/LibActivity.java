package com.example.hannah.guessanimalapp;

import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.button;

public class LibActivity extends AppCompatActivity {
    //Variables for number of rows to create and columns
    private static final int NUM_COLS= 1;
    private static final int NUM_ROWS = 4;
    List<String> animals = new ArrayList<>();
    AlertDialog alertDialog;
    TypedArray images;
    int a = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib);

        final DBHelper ourDBHelper = new DBHelper(this);

        try {

            ourDBHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            ourDBHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }
        animals = ourDBHelper.getAnimals();
        //Add Strings array animals to list animalarr
        //Add integer-array images to typedarray image here
        images = getResources().obtainTypedArray(R.array.random_imgs);
        //Alertdialog create
        alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme)).create();
        //On running the program, run populate buttons code
        populateButtons();
    }
    //Method for populating buttons
    private void populateButtons() {
        //Create a new table layout and assign it the tableForButton layout
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        //Loop for creating rows, using the int NUM_ROWS
        for (int row = 0; row < animals.size(); row++) {
            TableRow tableRow = new TableRow(this);
            TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f);
            /*tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));*/
            params.setMargins(0,20,0,0);
            tableRow.setLayoutParams(params);
            table.addView(tableRow);

            //Loop for creating new columns of buttons in the table
            for (int col = 0; col < NUM_COLS; col++) {
                a++;
                //Button button = new Button(this);
                Button button = new Button(this);
                button.setBackgroundResource(R.drawable.button_style_main);
                button.setHeight(50);
                button.setWidth(50);
                button.setMaxHeight(50);
                button.setMaxWidth(50);
                button.setTextSize(20);
//                button.setBackgroundResource(images.getResourceId(a, -1));
                button.setText(animals.get(a));
                button.setId(a);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(v);
                    }
                });
                tableRow.addView(button);

            }
        }
        //Do something with the ids assigned


    }

    private void gridButtonClicked(View view) {
        String btnText = ((Button)view).getText().toString();
        Toast.makeText(this, btnText, Toast.LENGTH_SHORT).show();
        alertDialog.setTitle("hejhej");
        alertDialog.setMessage("The " + btnText + " Is an animal");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OKAAAAY",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
