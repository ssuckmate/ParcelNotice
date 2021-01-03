package com.eos.parcelnotice.retrofit;

import com.eos.parcelnotice.data.LaundryData;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface LaundryApi {
    @PUT("user/washer/changeStatus")
    Call change_laundry_status(@Header("token") String token,@Body JsonObject jsonObject);
    @GET("user/washer/floor")
    Call<ArrayList<LaundryData>> get_laundry_list(@Header("token") String token,@Body JsonObject jsonObject);
}
