package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.eos.parcelnotice.data.DummyData;
import com.eos.parcelnotice.data.NoticeData;
import com.eos.parcelnotice.databinding.ActivityNoticeBinding;
import com.eos.parcelnotice.retrofit.NoticeApi;
import com.eos.parcelnotice.retrofit.UserApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NoticeActivity extends AppCompatActivity {
    ActivityNoticeBinding binding;
    String notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_notice);
        binding.setActivity(this);
        init();
    }

    private void init() {
        SharedPreferences preferences = getSharedPreferences("token",0);

        Call<ArrayList<NoticeData>> noticeDataCall = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NoticeApi.class)
                .get_notice(preferences.getString("token",""));
        noticeDataCall.enqueue(new Callback<ArrayList<NoticeData>>() {
            @Override
            public void onResponse(Call<ArrayList<NoticeData>> call, Response<ArrayList<NoticeData>> response) {
                notice = "\n"+response.body().get(0).getContents();
                binding.editTextNotice.setText(notice);
            }

            @Override
            public void onFailure(Call<ArrayList<NoticeData>> call, Throwable t) {

            }
        });

    }
}