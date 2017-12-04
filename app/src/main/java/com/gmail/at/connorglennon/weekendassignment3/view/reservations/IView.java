package com.gmail.at.connorglennon.weekendassignment3.view.reservations;

import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.data.model.Reservation;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.ui.base.MvpView;

import java.util.List;

/**
 * Created by Connor Glennon on 03/12/2017.
 */

public interface IView extends MvpView {
    void onFetchDataSuccess(List<Reservation> reservations);
    void onFetchDataError(String message);
}
