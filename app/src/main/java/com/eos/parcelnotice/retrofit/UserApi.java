package com.eos.parcelnotice.retrofit;

import com.eos.parcelnotice.data.UserData;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserApi {
    @GET("user")
    Call<UserData> get_user(@Header("token") String token);
}
