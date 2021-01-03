package com.eos.parcelnotice.retrofit;

import com.eos.parcelnotice.data.DormitoryData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface DormitoryApi {
    @GET("/sagam/dormitory")
    Call<DormitoryData> get_dormitory(@Header("token") String token);
}
