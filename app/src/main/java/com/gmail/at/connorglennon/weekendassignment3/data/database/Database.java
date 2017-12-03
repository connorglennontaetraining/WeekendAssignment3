package com.gmail.at.connorglennon.weekendassignment3.data.database;

import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.data.model.Reservation;

import java.util.List;

/**
 * Created by Connor Glennon on 02/12/2017.
 */

public abstract class Database {
    abstract public void saveReservation(ParkingSpace parkingSpace);
    abstract public List<Reservation> loadAllReservations();
}
