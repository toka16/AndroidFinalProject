package com.example.user.finalproject;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.user.finalproject.Activities.Log_in_Activity;
import com.example.user.finalproject.Server.ServerHelper;
import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.Category;
import com.example.user.finalproject.model.Menu;
import com.example.user.finalproject.model.News;
import com.example.user.finalproject.model.Product;

import java.text.ParseException;
import java.util.ArrayList;


public class App extends Application{
    public static final String SHARED_PREFERENCES_NAME = "final_project_shared_preferences";
    private static final String SHARED_PREFERENCES_COOKIE = "cookie";

    @Override
    public void onCreate(){
        super.onCreate();

        SharedPreferences pref = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String cookie = pref.getString(SHARED_PREFERENCES_COOKIE, "");
        final ServerHelper helper = ServerHelper.getInstance(cookie);
        if(cookie.equals("")) {
            Intent intent = new Intent(this, Log_in_Activity.class);
            startActivity(intent);
        }

    }
}
