package com.eos.parcelnotice.retrofit;

import com.eos.parcelnotice.data.ParcelData;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ParcelApi {

    @POST("parcels/")
    Call<ParcelData> post_user();
    @GET("parcels/")
    Call<List<ParcelData>> get_parcels();
    @GET("parcles/{id}")
    Call<ParcelData> get_user(@Path("id") int parcelId);
}
