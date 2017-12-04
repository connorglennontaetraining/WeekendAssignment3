package com.gmail.at.connorglennon.weekendassignment3;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Base64;

import com.gmail.at.connorglennon.weekendassignment3.dagger2.DaggerReservationsPresenterComponent;
import com.gmail.at.connorglennon.weekendassignment3.dagger2.DaggerSearchPresenterComponent;
import com.gmail.at.connorglennon.weekendassignment3.dagger2.ReservationsPresenterComponent;
import com.gmail.at.connorglennon.weekendassignment3.dagger2.SearchPresenterComponent;

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

    ReservationsPresenterComponent reservationsPresenterComponent;
    SearchPresenterComponent searchPresenterComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        sharedPreferences = getSharedPreferences("connorglennontaetraining.at.gmail.com.weekendassignment2", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        Realm.init(this);

        reservationsPresenterComponent = DaggerReservationsPresenterComponent.create();
        searchPresenterComponent = DaggerSearchPresenterComponent.create();

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

    public ReservationsPresenterComponent getReservationsPresenterComponent() {
        return reservationsPresenterComponent;
    }

    public void setReservationsPresenterComponent(ReservationsPresenterComponent reservationsPresenterComponent) {
        this.reservationsPresenterComponent = reservationsPresenterComponent;
    }

    public SearchPresenterComponent getSearchPresenterComponent() {
        return searchPresenterComponent;
    }

    public void setSearchPresenterComponent(SearchPresenterComponent searchPresenterComponent) {
        this.searchPresenterComponent = searchPresenterComponent;
    }
}
