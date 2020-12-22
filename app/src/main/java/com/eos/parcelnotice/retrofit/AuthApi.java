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
    @POST("user/auth/join")
    Call<Object> join(@Body JsonObject jsonObject);
    @POST("user/auth/login")
    Call<TokenVO> login(@Body JsonObject jsonObject);
}
