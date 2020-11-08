package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.eos.parcelnotice.adapter.ParcelConfirmAdapter;
import com.eos.parcelnotice.data.ParcelData;
import com.eos.parcelnotice.retrofit.ParcelApi;

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
    private Callback<List<ParcelData>> retrofitCallback;
    private List<ParcelData> parcels;
    public static Call<List<ParcelData>> callGetParcels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_confirm);

        initToolbar();
        initRecyclerView();
        initCallback();
        initRetrofit();
        callGetParcels.enqueue(retrofitCallback);
    }

    void initRecyclerView(){
        recyclerView = findViewById(R.id.parcel_confirm_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
    void initToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.parcel_confirm_toolbar);
        setSupportActionBar(toolbar);
    }
    void initRetrofit(){
        callGetParcels = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ParcelApi.class)
                .get_parcels();
    }
    void initCallback(){
        retrofitCallback = new Callback<List<ParcelData>>() {
            @Override
            public void onResponse(Call<List<ParcelData>> call, Response<List<ParcelData>> response) {
                parcels = response.body();
                recyclerView.setLayoutManager(layoutManager);
                adapter = new ParcelConfirmAdapter(parcels);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<ParcelData>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
    }



    @Override
    protected void onResume() {
        super.onResume();
    }
}