package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.eos.parcelnotice.adapter.LaundryAdapter;
import com.eos.parcelnotice.adapter.LaundryFloorAdapter;
import com.eos.parcelnotice.data.DormitoryData;
import com.eos.parcelnotice.data.LaundryData;
import com.eos.parcelnotice.data.UserData;
import com.eos.parcelnotice.databinding.ActivityLaundryConfirmBinding;
import com.eos.parcelnotice.retrofit.DormitoryApi;
import com.eos.parcelnotice.retrofit.LaundryApi;
import com.eos.parcelnotice.retrofit.UserApi;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.eos.parcelnotice.MainActivity.getMyInfo;


public class LaundryConfirmActivity extends AppCompatActivity {
    LaundryFloorAdapter floorAdapter;
    static ActivityLaundryConfirmBinding binding;
    DormitoryData dormitory;
    private static SharedPreferences pref;
    private static String baseUrl;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_laundry_confirm);
        binding.setActivity(this);

        pref = getSharedPreferences("token",0);
        baseUrl = getString(R.string.base_url);
        context = LaundryConfirmActivity.this;

       init();

    }

    public static void setLaundryAdapter(LaundryAdapter laundryAdapter) {
        binding.laundryConfirmGridView.setAdapter(laundryAdapter);
    }

    public static void resetAdapter(ArrayList<Integer> floors, int currentFloor){
        binding.laundryConfirmRecyclerView.setAdapter(new LaundryFloorAdapter(context,floors,currentFloor));
    }


    private void init(){
        Call<DormitoryData> callDormitory = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DormitoryApi.class)
                .get_dormitory(getToken());
        Callback<DormitoryData> callbackDormitory = new Callback<DormitoryData>() {
            @Override
            public void onResponse(Call<DormitoryData> call, Response<DormitoryData> response) {
                dormitory = response.body();
                ArrayList<Integer> floors = new ArrayList<>();
                for(int i=0; i<dormitory.getStory();i++){
                    floors.add(i+1);
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                binding.laundryConfirmRecyclerView.setLayoutManager(layoutManager);
                floorAdapter = new LaundryFloorAdapter(getApplicationContext(),floors,getMyInfo().getFloor());
                binding.laundryConfirmRecyclerView.setAdapter(floorAdapter);
            }

            @Override
            public void onFailure(Call<DormitoryData> call, Throwable t) {
                Toast.makeText(LaundryConfirmActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
        callDormitory.enqueue(callbackDormitory);

    }


    public static String getToken(){
        return pref.getString("token","");
    }

    public static String getBaseUrl(){
        return baseUrl;
    }

    public static void showTimePicker(TimePickerDialog.OnTimeSetListener listener){
        TimePickerDialog tpd = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,listener,0,0, true);
        tpd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tpd.setTitle("세탁시간을 입력하세요");
        tpd.show();
    }
}