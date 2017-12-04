package com.gmail.at.connorglennon.weekendassignment3.view.search;

import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.ui.base.MvpView;

import java.util.List;

/**
 * Created by Connor Glennon on 03/12/2017.
 */

public interface ISearchView extends MvpView {
    void onFetchDataSuccess(List<ParkingSpace> parkingSpaces);
    void onFetchDataError(String message);
}
