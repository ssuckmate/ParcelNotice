package com.eos.parcelnotice.retrofit;

import com.eos.parcelnotice.data.UserData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApi {
    @GET("users/")
    Call<UserData> get_users();
    @GET("users/{id}")
    Call<UserData> get_user(@Path("id") String userId);
}
