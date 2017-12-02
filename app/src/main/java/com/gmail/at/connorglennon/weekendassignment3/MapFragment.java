package com.gmail.at.connorglennon.weekendassignment3;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.data.network.ServerConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    Unbinder mButterknifeUnbinder;
    List<ParkingSpace> mParkingSpaces;

    @BindView(R.id.etLatitude)
    EditText etLatitude;
    @BindView(R.id.etLongitude)
    EditText etLongitude;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mButterknifeUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        ServerConnection.getServerConnection()
                .requestParkingSpaces(TestingData.LAT, TestingData.LON)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(parkingSpaces -> {
                    mParkingSpaces = parkingSpaces;
                    loadMap();
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterknifeUnbinder.unbind();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        Map<Marker, ParkingSpace> parkingSpaceMap;
        parkingSpaceMap = new HashMap<>();

        for (ParkingSpace parkingSpace: mParkingSpaces){
            double lat = Double.parseDouble(parkingSpace.getLat());
            double lon = Double.parseDouble(parkingSpace.getLng());
            Log.i(this.getClass().getSimpleName(), "onMapReady: " + lat + ", " + lon);
            Marker marker = mGoogleMap.addMarker(
                    new MarkerOptions().position(
                            new LatLng(lat, lon)));

            if(!parkingSpace.getIsReserved()){
                marker.setIcon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }

            parkingSpaceMap.put(marker, parkingSpace);
        }

        ParkingSpaceInfoWindowAdapter parkingSpaceInfoWindowAdapter;
        parkingSpaceInfoWindowAdapter = new ParkingSpaceInfoWindowAdapter(getContext(),
                parkingSpaceMap,
                R.layout.map_parking_space_info_window);

        mGoogleMap.setInfoWindowAdapter(parkingSpaceInfoWindowAdapter);

        LatLng position = new LatLng(TestingData.LAT, TestingData.LON);
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(14.0f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    void initViews(){
        etLatitude.setEnabled(false);
        etLatitude.setText(Double.toString(TestingData.LAT));
        etLongitude.setEnabled(false);
        etLongitude.setText(Double.toString(TestingData.LON));
    }

    private void loadMap(){
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragmentMap);
        supportMapFragment.getMapAsync(this);
    }
}
