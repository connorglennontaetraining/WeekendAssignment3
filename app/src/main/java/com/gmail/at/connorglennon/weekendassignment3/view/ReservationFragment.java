package com.gmail.at.connorglennon.weekendassignment3.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.at.connorglennon.weekendassignment3.R;
import com.gmail.at.connorglennon.weekendassignment3.data.database.realm.RealmDatabase;
import com.gmail.at.connorglennon.weekendassignment3.data.model.Reservation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {

    Unbinder mButterknifeUnbinder;
    @BindView(R.id.rvReservations)
    RecyclerView mRecyclerView;

    public ReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        mButterknifeUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Reservation> reservationList = RealmDatabase.getDatabase().loadAllReservations();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(
                new ReservationAdapter(reservationList, R.layout.card_reservation, getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterknifeUnbinder.unbind();
    }
}
