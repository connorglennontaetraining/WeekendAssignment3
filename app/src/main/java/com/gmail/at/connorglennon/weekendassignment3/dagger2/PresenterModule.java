package com.gmail.at.connorglennon.weekendassignment3.dagger2;

import com.gmail.at.connorglennon.weekendassignment3.data.WA3DataManager;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.utils.rx2.AppSchedulerProvider;
import com.gmail.at.connorglennon.weekendassignment3.view.reservations.IReservationsPresenter;
import com.gmail.at.connorglennon.weekendassignment3.view.reservations.ReservationsPresenter;
import com.gmail.at.connorglennon.weekendassignment3.view.search.ISearchPresenter;
import com.gmail.at.connorglennon.weekendassignment3.view.search.SearchPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Connor Glennon on 04/12/2017.
 */

@Module
public class PresenterModule {
    @Provides
    IReservationsPresenter getReservationsPresenter(){
        return new ReservationsPresenter(
                new WA3DataManager(),
                new AppSchedulerProvider(),
                new CompositeDisposable());
    }

    @Provides
    ISearchPresenter getSearchPresenter(){
        return new SearchPresenter(
                new WA3DataManager(),
                new AppSchedulerProvider(),
                new CompositeDisposable());
    }
}
