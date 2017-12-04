package com.gmail.at.connorglennon.weekendassignment3.view.search;

import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.data.model.Reservation;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.ui.base.MvpPresenter;

/**
 * Created by Connor Glennon on 03/12/2017.
 */

public interface IPresenter<V extends IView> extends MvpPresenter<V>{
    void onCallGetParkingSpaces(double lat, double lon);
    void onSaveReservation(ParkingSpace parkingSpace);
}
