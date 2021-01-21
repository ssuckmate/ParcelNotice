package com.eos.parcelnotice.retrofit;

import com.eos.parcelnotice.data.PointData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface PointApi {
    @GET("user/point")
    Call<List<PointData>> get_point(@Header("token") String token);
}
