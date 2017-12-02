package com.gmail.at.connorglennon.weekendassignment3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

/**
 * Created by Connor Glennon on 02/12/2017.
 */

public class ParkingSpaceInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    Context context;
    Map<Marker, ParkingSpace> parkingSpaceMap;
    int layoutId;

    public ParkingSpaceInfoWindowAdapter(Context context,
                                         Map<Marker, ParkingSpace> parkingSpaceMap,
                                         int layoutId){
        this.context = context;
        this.parkingSpaceMap = parkingSpaceMap;
        this.layoutId = layoutId;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = View.inflate(context, layoutId, null);
        if(parkingSpaceMap.containsKey(marker)){
            ParkingSpace parkingSpace = parkingSpaceMap.get(marker);
            TextView tvName = view.findViewById(R.id.tvName);
            tvName.setText(parkingSpace.getName());

            TextView tvReservedUntil = view.findViewById(R.id.tvReservedUntil);
            tvReservedUntil.setText(parkingSpace.getReservedUntil());

            TextView tvMinimumTime = view.findViewById(R.id.tvMinimumTime);
            tvMinimumTime.setText(parkingSpace.getMinReserveTimeMins() + " minutes");

            TextView tvMaximumTime = view.findViewById(R.id.tvMaximumTime);
            tvMaximumTime.setText(parkingSpace.getMaxReserveTimeMins() + " minutes");

            TextView tvCost = view.findViewById(R.id.tvCost);
            tvCost.setText(parkingSpace.getCostPerMinute() + "");
        }
        return view;
    }
}
