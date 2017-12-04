package com.gmail.at.connorglennon.weekendassignment3.view.reservations;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.at.connorglennon.weekendassignment3.R;
import com.gmail.at.connorglennon.weekendassignment3.data.model.Reservation;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Connor Glennon on 03/12/2017.
 */

public class ReservationsAdapter extends RecyclerView.Adapter {

    List<Reservation> reservationList;
    int layoutId;
    Context context;

    public ReservationsAdapter(List<Reservation> reservationList, int layoutId, Context context) {
        this.reservationList = reservationList;
        this.layoutId = layoutId;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReservationViewHolder reservationViewHolder = (ReservationViewHolder) holder;
        Reservation reservation = reservationList.get(position);
        reservationViewHolder.tvReservationName.setText(reservation.getName());
        reservationViewHolder.tvReservationDate.setText(reservation.getDate());
        ((ReservationViewHolder) holder).view.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ReservationViewHolder extends RecyclerView.ViewHolder{
        View view;

        TextView tvReservationName;
        TextView tvReservationDate;

        public ReservationViewHolder(View view) {
            super(view);
            this.view = view;

            this.tvReservationName = view.findViewById(R.id.tvReservationName);
            this.tvReservationDate = view.findViewById(R.id.tvReservationDate);
        }
    }
}
