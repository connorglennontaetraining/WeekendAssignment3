package com.gmail.at.connorglennon.weekendassignment3.view.search;

import com.gmail.at.connorglennon.weekendassignment3.data.IDataManager;
import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.ui.base.BasePresenter;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.utils.rx2.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Connor Glennon on 03/12/2017.
 */

public class SearchPresenter<V extends ISearchView> extends BasePresenter<V> implements ISearchPresenter<V> {
    public SearchPresenter(IDataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
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
