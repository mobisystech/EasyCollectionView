package com.mobisys.android.easycollectionviewsample.rest;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public abstract class RestCallback<T> implements Callback<T> {

	public abstract void failure(String restErrors, boolean networkError);
	
	@SuppressWarnings("deprecation")
	@Override
	public void failure(RetrofitError error) {
        Log.d("RestCallback", "error ==> "+error.toString());
        String restErrors = null;
		boolean networkError = false;
		if (error.isNetworkError()) {
            restErrors = "Cannot connect to internet";
            networkError = true;
        }  else {
            if(error.getResponse().getBody()!=null) {
                String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    restErrors = jsonObject.getString("error");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        
		failure(restErrors,networkError);
    }
}
