package com.gmail.at.connorglennon.weekendassignment3.view.reservations;

import com.gmail.at.connorglennon.weekendassignment3.data.IDataManager;
import com.gmail.at.connorglennon.weekendassignment3.data.model.Reservation;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.ui.base.BasePresenter;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.utils.rx2.SchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Connor Glennon on 03/12/2017.
 */

public class ReservationsPresenter<V extends IReservationsView> extends BasePresenter<V> implements IReservationsPresenter<V> {
    public ReservationsPresenter(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onCallGetReservations() {
        List<Reservation> reservations = getDataManager().loadAllReservations();
        if(reservations == null){
            getMvpView().onFetchDataError("Failed to load data");
        }
        else {
            getMvpView().onFetchDataSuccess(reservations);
        }
    }
}
