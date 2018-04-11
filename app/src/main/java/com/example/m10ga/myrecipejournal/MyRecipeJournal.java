package com.example.m10ga.myrecipejournal;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by m10ga on 2018-04-10.
 */

public class MyRecipeJournal extends Application {

    @Override
    public void onCreate() {
        super.onCreate( );
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //[16]
    }
}
