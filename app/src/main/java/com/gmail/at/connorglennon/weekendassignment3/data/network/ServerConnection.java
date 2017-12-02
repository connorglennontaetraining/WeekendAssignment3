package com.gmail.at.connorglennon.weekendassignment3.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.gmail.at.connorglennon.weekendassignment3.MyApplication;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class ServerConnection {
    public static final int CACHE_SIZE = 10 * 1024 * 1024;
    public static final int MAX_STALE = 60 * 60 * 1;
    public static final int MAX_AGE = 60;
    public static final String HEADER_NAME = "Cache-Control";

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    static IRequest request;

    public static IRequest getServerConnection() {
        // Location of the cache.
        File httpCacheDirectory = new File(MyApplication.getApplication().getCacheDir(),  "responses");
        // Initialise the cache.
        Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);

        okHttpClient = new OkHttpClient.Builder() // get http client builder.
                .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                .addInterceptor(OFFLINE_INTERCEPTOR)
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit.create(IRequest.class);
    }

    /**
     * Interceptor to cache data and maintain it for MAX_AGE time (seconds).
     *
     * If the same network request is sent within a minute,
     * the response is retrieved from cache.
     */
    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = chain -> {
        Response originalResponse = chain.proceed(chain.request()); //Gets the intercepted request.
        String cacheControl = originalResponse.header(HEADER_NAME);

        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            Log.i("Values", "REWRITE_RESPONSE_CACHE");
            return originalResponse.newBuilder()
                    .header(HEADER_NAME, "public, max-age=" + MAX_AGE)
                    .build();
        } else {
            Log.i("Values", "REWRITE_RESPONSE_INTERCEPTOR");
            return originalResponse;
        }
    };

    /**
     *
     */
    private static final Interceptor OFFLINE_INTERCEPTOR = chain -> {
        Request request = chain.request(); //Gets the intercepted request.

        if (!isOnline()) {
            Log.d(TAG, "rewriting request");


            request = request.newBuilder()
                    .header(HEADER_NAME, "public, only-if-cached, max-stale=" + MAX_STALE)
                    .build();
        }
        return chain.proceed(request);
    };

    public static boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) MyApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
