package com.gmail.at.connorglennon.weekendassignment3.view.search;

import com.gmail.at.connorglennon.weekendassignment3.data.IDataManager;
import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.data.model.Reservation;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.ui.base.BasePresenter;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.utils.rx2.SchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Connor Glennon on 03/12/2017.
 */

public class Presenter<V extends IView> extends BasePresenter<V> implements IPresenter<V>{
    public Presenter(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onCallGetParkingSpaces(double lat, double lon) {
        getCompositeDisposable()
                .add(getDataManager()
                        .requestParkingSpaces(lat, lon)
                            .observeOn(getSchedulerProvider().ui())
                            .subscribeOn(getSchedulerProvider().io())
                            .subscribe(parkingSpaces -> {
                                getMvpView().onFetchDataSuccess(parkingSpaces);
                            }, throwable -> {
                                throwable.printStackTrace();
                                getMvpView().onFetchDataError(throwable.toString());
                            }));
    }

    @Override
    public void onSaveReservation(ParkingSpace parkingSpace) {
        getDataManager().saveReservation(parkingSpace);
    }
}
