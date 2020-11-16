package com.eos.parcelnotice.retrofit;

import com.eos.parcelnotice.data.TokenVO;
import com.eos.parcelnotice.data.UserData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthApi {
    @GET("users/")
    Call<UserData> get_users();
    @GET("users/{id}")
    Call<UserData> get_user(@Path("id") String userId);
    @POST("auth/join")
    Call<Object> join(@Body JsonObject jsonObject);
    @POST("auth/login")
    Call<TokenVO> login(@Body JsonObject jsonObject);
}
