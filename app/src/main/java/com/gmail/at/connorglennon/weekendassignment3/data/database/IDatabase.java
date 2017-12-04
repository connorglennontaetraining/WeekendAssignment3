package com.gmail.at.connorglennon.weekendassignment3.data.database;

import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.data.model.Reservation;

import java.util.List;

/**
 * Created by Connor Glennon on 02/12/2017.
 */

public interface IDatabase {
    void saveReservation(ParkingSpace parkingSpace);
    List<Reservation> loadAllReservations();
}
