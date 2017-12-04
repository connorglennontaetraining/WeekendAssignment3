package com.gmail.at.connorglennon.weekendassignment3.view.reservations;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.at.connorglennon.weekendassignment3.MyApplication;
import com.gmail.at.connorglennon.weekendassignment3.R;
import com.gmail.at.connorglennon.weekendassignment3.data.WA3DataManager;
import com.gmail.at.connorglennon.weekendassignment3.data.model.Reservation;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.ui.base.BaseFragment;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.utils.rx2.AppSchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationsFragment extends BaseFragment implements IReservationsView {

    Unbinder mButterknifeUnbinder;
    @BindView(R.id.rvReservations)
    RecyclerView mRecyclerView;

    @Inject
    IReservationsPresenter presenter;

    public ReservationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        mButterknifeUnbinder = ButterKnife.bind(this, view);
        injectDagger();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onAttach(getContext());
        presenter.onAttach(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter.onCallGetReservations();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterknifeUnbinder.unbind();
        onDetach();
    }

    @Override
    public void onFetchDataSuccess(List<Reservation> reservations) {
        mRecyclerView.setAdapter(
                new ReservationsAdapter(reservations, R.layout.card_reservation, getContext()));
    }

    @Override
    public void onFetchDataError(String message) {
        onError(message);
    }

    void injectDagger(){
        ((MyApplication) MyApplication.getApplication()).getReservationsPresenterComponent().inject(this);
    }
}
