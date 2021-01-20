package com.eos.parcelnotice.retrofit;

import com.eos.parcelnotice.data.ParcelData;
import com.eos.parcelnotice.data.ParcelDataList;
import com.eos.parcelnotice.data.ResponseData;
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
import retrofit2.http.Query;

public interface ParcelApi {


    @GET("user/parcel/myParcels")
    Call<ParcelDataList> get_parcels(@Header("token") String token);
    @PUT("user/parcel/changeStatus")
    Call<ResponseData> change_parcel_status(@Header("token") String token, @Body JsonObject jsonObject);
    @DELETE("user/parcel/delete")
    Call<ResponseData> delete_parcel(@Header("token") String token,@Query("parcel") int parcel);
}
