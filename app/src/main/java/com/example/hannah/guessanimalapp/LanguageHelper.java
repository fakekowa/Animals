package com.example.hannah.guessanimalapp;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by Hannah on 06/12/16.
 */
public class LanguageHelper {
    // https://blog.guillaumeagis.eu/change-language/ här får vi referera till denna tror jag..

    public static void changeLocale(Resources res, String locale) {

        Configuration conf;
        conf = new Configuration(res.getConfiguration());

        switch (locale) {
            case "sv":
                conf.locale = new Locale("sv");
                break;
            default:
                conf.locale = Locale.ENGLISH;
                break;
        }
        res.updateConfiguration(conf, res.getDisplayMetrics());

    }
}
