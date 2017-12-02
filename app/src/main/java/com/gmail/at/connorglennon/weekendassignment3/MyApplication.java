package com.gmail.at.connorglennon.weekendassignment3;

import android.app.Application;

/**
 * Created by Connor Glennon on 01/12/2017.
 */

public class MyApplication extends Application {
    public static Application application;

    public static Application getApplication(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
