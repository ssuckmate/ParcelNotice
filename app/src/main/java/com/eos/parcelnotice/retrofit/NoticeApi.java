package com.eos.parcelnotice.retrofit;

import com.eos.parcelnotice.data.NoticeData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface NoticeApi {
    @GET("user/notice")
    Call<ArrayList<NoticeData>> get_notice(@Header("token") String token);
}
