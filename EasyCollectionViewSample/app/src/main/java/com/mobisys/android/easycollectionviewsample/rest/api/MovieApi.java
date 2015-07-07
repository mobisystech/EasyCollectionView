package com.mobisys.android.easycollectionviewsample.rest.api;

import com.mobisys.android.easycollectionviewsample.model.Movie;
import com.mobisys.android.easycollectionviewsample.model.MovieWrapper;
import com.mobisys.android.easycollectionviewsample.rest.RestCallback;

import retrofit.http.GET;
import retrofit.http.Query;

public interface MovieApi {
    @GET("/movie/upcoming")
    public void getMovieList(@Query("api_key") String api_key, RestCallback<MovieWrapper> restCallback);

}
