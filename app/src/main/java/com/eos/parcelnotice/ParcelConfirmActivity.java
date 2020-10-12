package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.eos.parcelnotice.adapter.ParcelConfirmAdapter;
import com.eos.parcelnotice.data.ParcelData;
import com.eos.parcelnotice.retrofit.ParcelApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParcelConfirmActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Retrofit retrofit;
    private ParcelApi parcelApi;
    private Call<List<ParcelData>> callGetParcels;
    private Callback<List<ParcelData>> retrofitCallback;
    private List<ParcelData> parcels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_confirm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.parcel_confirm_toolbar);
        setSupportActionBar(toolbar);

        //컨텐츠 변경시 Holder의 사이즈를 체크하는데, 그걸 해제하는 기능. 이거 설정하면 성능 향상 가능!!
        recyclerView = findViewById(R.id.parcel_confirm_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        adapter = new ParcelConfirmAdapter(parcels);




        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        parcelApi = retrofit.create(ParcelApi.class);
        callGetParcels  = parcelApi.get_parcels();
        retrofitCallback = new Callback<List<ParcelData>>() {
            @Override
            public void onResponse(Call<List<ParcelData>> call, Response<List<ParcelData>> response) {
                Log.d("HELLHELLO", "onResponse: " + response.body().toString());
                parcels = response.body();
                recyclerView.setLayoutManager(layoutManager);
                adapter = new ParcelConfirmAdapter(parcels);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<ParcelData>> call, Throwable t) {
                Log.d("HELLHELLO", "onFailure: " + t.getMessage());
            }
        };
        callGetParcels.enqueue(retrofitCallback);
    }



    @Override
    protected void onResume() {
        super.onResume();
    }
}