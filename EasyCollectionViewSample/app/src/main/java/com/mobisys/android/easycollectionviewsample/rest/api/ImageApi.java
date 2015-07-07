package com.mobisys.android.easycollectionviewsample.rest.api;

import com.mobisys.android.easycollectionviewsample.rest.RestCallback;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.MultipartTypedOutput;

public interface ImageApi {
    @POST("/api/image/upload")
    public void uploadImage(@Body MultipartTypedOutput multipartTypedOutput, RestCallback<String> restCallback);

}
