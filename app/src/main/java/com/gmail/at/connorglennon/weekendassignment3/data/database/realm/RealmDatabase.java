package com.gmail.at.connorglennon.weekendassignment3.data.database.realm;

import com.gmail.at.connorglennon.weekendassignment3.data.database.IDatabase;
import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.data.model.Reservation;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Connor Glennon on 02/12/2017.
 */

public class RealmDatabase implements IDatabase {
    static RealmDatabase mDatabase;

    public static RealmDatabase getDatabase() {
        if (mDatabase == null){
            synchronized (IDatabase.class){
                mDatabase = new RealmDatabase();
            }
        }
        return mDatabase;
    }
    Realm mRealm;

    private RealmDatabase(){
        mRealm = Realm.getDefaultInstance();
    }


    @Override
    public void saveReservation(ParkingSpace parkingSpace) {
        mRealm.executeTransaction(realm -> mRealm.copyToRealmOrUpdate(
                new Reservation(parkingSpace.getId(), parkingSpace.getName())));
    }

    @Override
    public List<Reservation> loadAllReservations() {
        RealmResults<Reservation> reservationRealmResults;
        reservationRealmResults = mRealm.where(Reservation.class).findAll();
        if(reservationRealmResults != null) {
            return reservationRealmResults.subList(0, reservationRealmResults.size());
        }
        return null;
    }
}
