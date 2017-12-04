package com.gmail.at.connorglennon.weekendassignment3.view.search;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gmail.at.connorglennon.weekendassignment3.MyApplication;
import com.gmail.at.connorglennon.weekendassignment3.R;
import com.gmail.at.connorglennon.weekendassignment3.TestingData;
import com.gmail.at.connorglennon.weekendassignment3.data.WA3DataManager;
import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;
import com.gmail.at.connorglennon.weekendassignment3.data.network.ServerConnection;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.ui.base.BaseFragment;
import com.gmail.at.connorglennon.weekendassignment3.mindorks.utils.rx2.AppSchedulerProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements ISearchView, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener{

    @Inject
    ISearchPresenter presenter;

    GoogleMap mGoogleMap;
    Unbinder mButterknifeUnbinder;
    List<ParkingSpace> mParkingSpaces;
    Map<Marker, ParkingSpace> parkingSpaceMap;

    @BindView(R.id.fragmentConstraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.etLatitude)
    EditText etLatitude;
    @BindView(R.id.etLongitude)
    EditText etLongitude;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mButterknifeUnbinder = ButterKnife.bind(this, view);
        injectDagger();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onAttach(getContext());

        presenter.onAttach(this);

        initViews();
        loadData();
    }

    void initViews(){
        etLatitude.setEnabled(false);
        etLatitude.setText(Double.toString(TestingData.LAT));
        etLongitude.setEnabled(false);
        etLongitude.setText(Double.toString(TestingData.LON));
    }

    void injectDagger(){
        ((MyApplication) MyApplication.getApplication()).getSearchPresenterComponent().inject(this);
    }

    void loadData(){
        presenter.onCallGetParkingSpaces(TestingData.LAT, TestingData.LON);
    }

    private void loadMap(){
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragmentMap);
        if(mGoogleMap != null) mGoogleMap.clear();
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mButterknifeUnbinder.unbind();
        presenter.onDetach();
        onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.style_json));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        mGoogleMap = googleMap;

        mGoogleMap.setOnInfoWindowClickListener(this);

        parkingSpaceMap = new HashMap<>();

        for (ParkingSpace parkingSpace: mParkingSpaces){
            double lat = Double.parseDouble(parkingSpace.getLat());
            double lon = Double.parseDouble(parkingSpace.getLng());
            Log.i(this.getClass().getSimpleName(), "onMapReady: " + lat + ", " + lon);

            Marker marker = mGoogleMap.addMarker(
                    new MarkerOptions().position(
                            new LatLng(lat, lon)));

            marker.setTitle(parkingSpace.getId() + "");

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
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(18.0f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if(parkingSpaceMap.containsKey(marker)){
            if(parkingSpaceMap.get(marker).getIsReserved()){
                Snackbar.make(constraintLayout, "You can't book a reserved space.", Snackbar.LENGTH_LONG)
                        .show();
                return;
            }
        }
        ServerConnection.getServerConnection()
                .reserveParkingSpace(Integer.parseInt(marker.getTitle()))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(parkingSpace -> {
            showMessage("Parking Space Reserved");
            loadData();
            presenter.onSaveReservation(parkingSpace);
        }, throwable -> {
            onError(throwable.getMessage());
        });
    }

    @Override
    public void onFetchDataSuccess(List<ParkingSpace> parkingSpaces) {
        Log.d(this.getClass().getSimpleName(), "onFetchDataSuccess");
        mParkingSpaces = parkingSpaces;
        loadMap();
    }

    @Override
    public void onFetchDataError(String message) {
        Log.d(this.getClass().getSimpleName(), "onFetchDataError");
        onError(message);
    }
}
