package com.mobisys.android.easycollectionviewsample.rest;

import android.content.Context;

import com.mobisys.android.easycollectionviewsample.rest.api.ImageApi;
import com.mobisys.android.easycollectionviewsample.rest.api.MovieApi;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClient {

    private static final String HEADER_AUTH_TOKEN = "X-AUTH-TOKEN";
	private static MovieApi mMovieApi;
	private static ImageApi mImageApi;

    private static String BASE_URL = "https://api.themoviedb.org/3";

	private RestClient() {
	}

	public static MovieApi getMovieApi(Context context) {
		if(mMovieApi ==null){
			setupRestClient(context);
		}
		
		return mMovieApi;
	}

	public static ImageApi getImageApi(Context context) {
		if(mImageApi == null){
			setupRestClient(context);
		}

		return mImageApi;
	}

	private static void setupRestClient(final Context context) {
		OkHttpClient okClient = new OkHttpClient();
		
		RestAdapter.Builder builder = new RestAdapter.Builder()
			.setEndpoint(BASE_URL)
			.setConverter(new JacksonConverter())
			.setClient(new OkClient(okClient))
			.setLogLevel(RestAdapter.LogLevel.FULL);

		RestAdapter restAdapter = builder.build();
		mMovieApi = restAdapter.create(MovieApi.class);
		mImageApi = restAdapter.create(ImageApi.class);
	}
	
}
