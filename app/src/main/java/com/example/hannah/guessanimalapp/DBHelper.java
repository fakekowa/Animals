package com.example.hannah.guessanimalapp;

/**
 * Created by pontus on 2017-02-21.
 */
//Hejhej
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteAssetHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "";
    /* Default systemväg för applikations databas */
    private static String DB_PATH = "/data/data/com.example.hannah.guessanimalapp/databases/";

    private static String DB_NAME = "animals.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    public DBHelper(Context context) {

        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    /**
     * Skapar en tom databas på systemet och gör om det till vår egna
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            System.out.println("Database exists");
            this.getWritableDatabase();
            /* Databasen existerar, gör inget! */
        }
        dbExist = checkDataBase();

        if (!dbExist) {

            /* Om databasen inte finns så skapar vi en till som overridar vår default i systemet*/

            this.getReadableDatabase();
            this.getWritableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new RuntimeException(e);

            }
        }

    }


    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

        } catch (SQLiteException e) {

            //database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE
        );

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    public void insertAnimals(String name)
    {
        SQLiteDatabase mydataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id", id);
        contentValues.put("name", name);
        mydataBase.insert("animals", null, contentValues);
        mydataBase.close();
    }

    public List<String> getAnimals() {
        List<String> animals = new ArrayList<>();
        SQLiteDatabase myDatabase = this.getReadableDatabase();
        String query = "SELECT name from animals;";
        Cursor c1 = myDatabase.rawQuery(query, null);
        while (c1.moveToNext()) {
            String animal = c1.getString(c1.getColumnIndex("name"));
            animals.add(animal);
        }
        return animals;
    }

    public void cleanDB() {
        SQLiteDatabase myDatabase = this.getReadableDatabase();
        String query = "DELETE name from animals;";
        myDatabase.rawQuery(query, null);
    }

}
