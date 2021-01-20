package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eos.parcelnotice.adapter.ParcelConfirmAdapter;
import com.eos.parcelnotice.data.ParcelData;
import com.eos.parcelnotice.data.ParcelDataList;
import com.eos.parcelnotice.data.ResponseData;
import com.eos.parcelnotice.databinding.ActivityParcelConfirmBinding;
import com.eos.parcelnotice.retrofit.ParcelApi;
import com.eos.parcelnotice.retrofit.UserApi;
import com.eos.parcelnotice.data.UserData;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParcelConfirmActivity extends AppCompatActivity {
    private static SharedPreferences pref;
    private static ParcelApi parcelApi;
    static ActivityParcelConfirmBinding binding;
    private static ParcelConfirmAdapter adapter;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_parcel_confirm);
        binding.setActivity(this);

        pref = getSharedPreferences("token",0);
        context = ParcelConfirmActivity.this;

        init();
    }

    private void init() {
        parcelApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ParcelApi.class);

/*        Call<List<ParcelData>> callGetParcels =parcelApi.get_parcels(getToken());
        Callback<List<ParcelData>> retrofitCallback = new Callback<List<ParcelData>>() {
            @Override
            public void onResponse(Call<List<ParcelData>> call, Response<List<ParcelData>> response) {
                binding.parcelConfirmRecyclerview.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ParcelConfirmActivity.this);
                binding.parcelConfirmRecyclerview.setLayoutManager(layoutManager);
                adapter = new ParcelConfirmAdapter(response.body());
                binding.parcelConfirmRecyclerview.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<ParcelData>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        };*/

        Call<ParcelDataList> callGetParcels = parcelApi.get_parcels(getToken());
        Callback<ParcelDataList> retrofitCallback = new Callback<ParcelDataList>() {
            @Override
            public void onResponse(Call<ParcelDataList> call, Response<ParcelDataList> response) {
                binding.parcelConfirmRecyclerview.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ParcelConfirmActivity.this);
                binding.parcelConfirmRecyclerview.setLayoutManager(layoutManager);
                adapter = new ParcelConfirmAdapter(response.body().getParcels());
                binding.parcelConfirmRecyclerview.setAdapter(adapter);
                if(response.body().getParcels().size()==0) Toast.makeText(context,"도착한 택배가 없습니다.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ParcelDataList> call, Throwable t) {

            }
        };


        callGetParcels.enqueue(retrofitCallback);

    }


    public static String getToken(){
        return pref.getString("token","");
    }

    public static void changeParcelStatus(JsonObject jsonObject){
        Call<ResponseData> call = parcelApi.change_parcel_status(getToken(),jsonObject);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
        binding.parcelConfirmRecyclerview.setAdapter(adapter);
    }
    public static void deleteParcel(int id){
        Call<ResponseData> call = parcelApi.delete_parcel(getToken(),id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
            }
        });
        binding.parcelConfirmRecyclerview.setAdapter(adapter);
        if(adapter.getItemCount()==0) Toast.makeText(context,"도착한 택배가 없습니다.",Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onResume() {
        super.onResume();
    }
}