package com.gmail.at.connorglennon.weekendassignment3.view.reservations;

import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.ui.base.MvpPresenter;

/**
 * Created by Connor Glennon on 03/12/2017.
 */

public interface IPresenter<V extends IView> extends MvpPresenter<V>{
    void onCallGetReservations();
}
