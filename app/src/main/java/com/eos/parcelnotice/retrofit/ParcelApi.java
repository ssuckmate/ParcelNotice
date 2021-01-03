package com.eos.parcelnotice.retrofit;

import com.eos.parcelnotice.data.ParcelData;
import com.google.gson.JsonObject;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ParcelApi {


    @GET("user/parcel/myParcels")
    Call<List<ParcelData>> get_parcels(@Header("token") String token,@Body JsonObject jsonObject);
    @PUT("user/parcel/changeStatus")
    Call change_parcel_status(@Header("token") String token,@Body JsonObject jsonObject);
    @DELETE("user/parcel/delete")
    Call delete_parcel(@Header("token") String token,@Body JsonObject jsonObject);
}
