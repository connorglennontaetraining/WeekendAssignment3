package com.gmail.at.connorglennon.weekendassignment3;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Base64;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Connor Glennon on 01/12/2017.
 */

public class MyApplication extends Application {
    public static Application application;

    public static Application getApplication(){
        return application;
    }

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        sharedPreferences = getSharedPreferences("connorglennontaetraining.at.gmail.com.weekendassignment2", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        Realm.init(this);

        if(sharedPreferences.getString("ENCKEY", null) == null)
        {
            byte[] key = new byte[64];
            new SecureRandom().nextBytes(key);
            String enckey = Base64.encodeToString(key, Base64.DEFAULT);
            sharedPreferencesEditor.putString("ENCKEY", enckey).apply();
        }

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .encryptionKey(Base64.decode(sharedPreferences.getString("ENCKEY", null), Base64.DEFAULT))
                .build();

        Realm.setDefaultConfiguration(configuration);
    }
}
