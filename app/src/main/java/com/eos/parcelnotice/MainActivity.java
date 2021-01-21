package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eos.parcelnotice.data.FloorData;
import com.eos.parcelnotice.data.UserData;
import com.eos.parcelnotice.databinding.ActivityMainBinding;
import com.eos.parcelnotice.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static UserData myInfo;
    UserApi userApi;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setActivity(this);

        init();

        binding.buttonMainParcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ParcelConfirmActivity.class);
                startActivity(intent);
            }
        });

        binding.buttonMainLaundryRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LaundryConfirmActivity.class);
                startActivity(intent);
            }
        });

        binding.buttonMainReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RewardActivity.class);
                startActivity(intent);
            }

        });


        binding.buttonMainNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        pref = getSharedPreferences("token",0);
        userApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApi.class);

        Call<UserData> callUser = userApi.get_myInfo(pref.getString("token",""));
        callUser.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                myInfo = response.body();
                initFloor();
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {

            }
        });
    }

    private void initFloor(){
        Call<FloorData> callFloor = userApi.get_floor(pref.getString("token",""));
        callFloor.enqueue(new Callback<FloorData>() {
            @Override
            public void onResponse(Call<FloorData> call, Response<FloorData> response) {
                myInfo.setFloor(response.body().getFloor());
            }

            @Override
            public void onFailure(Call<FloorData> call, Throwable t) {
            }
        });

    }

    public static UserData getMyInfo(){
        return myInfo;
    }
}