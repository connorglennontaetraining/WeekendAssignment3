package com.gmail.at.connorglennon.weekendassignment3.data.network;

import com.gmail.at.connorglennon.weekendassignment3.data.model.ParkingSpace;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Connor Glennon on 01/12/2017.
 */

public interface IRequest {
    /**
     * Interface method that uses Retrofit2 to GET parking space
     * data from the API.
     * @param lat latitude with which to search against.
     * @param lon longitude with which to search against.
     * @return a RxJava Observable containing a list of ParkingSpace objects.
     */
    @GET("search")
    Observable<List<ParkingSpace>>
        requestParkingSpaces(@Query(ApiConstants.QUERY_LAT) double lat,
                         @Query(ApiConstants.QUERY_LON) double lon);


    @GET(ApiConstants.PATH_ID)
    Observable<ParkingSpace> requestParkingSpace(@Path(ApiConstants.PARAM_ID) int paramId);

    @POST(ApiConstants.PATH_ID + ApiConstants.RESERVE)
    Observable<ParkingSpace> reserveParkingSpace(@Path(ApiConstants.PARAM_ID) int paramId);
}
