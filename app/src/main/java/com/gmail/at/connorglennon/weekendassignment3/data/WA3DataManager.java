package com.gmail.at.connorglennon.weekendassignment3.data;

import com.gmail.at.connorglennon.weekendassignment3.data.database.IDatabase;
import com.gmail.at.connorglennon.weekendassignment3.data.database.realm.RealmDatabase;
import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.data.model.Reservation;
import com.gmail.at.connorglennon.weekendassignment3.data.network.IRequest;
import com.gmail.at.connorglennon.weekendassignment3.data.network.ServerConnection;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Connor Glennon on 03/12/2017.
 */

public class WA3DataManager extends IDataManager {

    IDatabase database;
    IRequest request;

    public WA3DataManager() {
        this.database = RealmDatabase.getDatabase();
        this.request = ServerConnection.getServerConnection();
    }

    @Override
    public void saveReservation(ParkingSpace parkingSpace) {
        database.saveReservation(parkingSpace);
    }

    @Override
    public List<Reservation> loadAllReservations() {
        return database.loadAllReservations();
    }

    @Override
    public Observable<List<ParkingSpace>> requestParkingSpaces(double lat, double lon) {
        return request.requestParkingSpaces(lat, lon);
    }

    @Override
    public Observable<ParkingSpace> requestParkingSpace(int paramId) {
        return request.requestParkingSpace(paramId);
    }

    @Override
    public Observable<ParkingSpace> reserveParkingSpace(int paramId) {
        return request.reserveParkingSpace(paramId);
    }
}
