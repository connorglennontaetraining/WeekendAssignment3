package com.gmail.at.connorglennon.weekendassignment3.data.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Connor Glennon on 02/12/2017.
 */

public class Reservation extends RealmObject {
    @PrimaryKey
    String date; // Not an ideal primary key on a real situation.

    int parkingSpaceId;
    String name;

    public Reservation() {
    }

    public Reservation(int parkingSpaceId, String name) {
        this.parkingSpaceId = parkingSpaceId;
        Date date = Calendar.getInstance(Locale.ENGLISH).getTime();
        this.date = date.toString();
        this.name = name;
    }

    public int getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(int parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
