package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.eos.parcelnotice.adapter.ParcelConfirmAdapter;
import com.eos.parcelnotice.data.ParcelData;
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
    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Callback<List<ParcelData>> retrofitCallback;
    private List<ParcelData> parcels;
    private Call<List<ParcelData>> callGetParcels;
    private Call<UserData> callGetUserData;
    private Callback<UserData> callback;
    private static UserData userData;
    private static SharedPreferences pref;
    private static ParcelApi parcelApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_confirm);

        //pref = getSharedPreferences("setting",0);

        initToolbar();
        initRecyclerView();
        //
        adapter = new ParcelConfirmAdapter();
        recyclerView.setAdapter(adapter);
        //
        //initCallback();
        //initRetrofit();
       // callGetParcels.enqueue(retrofitCallback);
       // callGetUserData.enqueue(callback);
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
        parcelApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ParcelApi.class);

        JsonObject json = new JsonObject();
        json.addProperty("token",getToken());
        callGetUserData = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApi.class)
                .get_user(json);
        callGetParcels =parcelApi.get_parcels(json);
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
        callback = new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                userData = response.body();
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
    }


    public static String getToken(){
        return pref.getString("token","");
    }

    public static void changeParcelStatus(JsonObject jsonObject){
        parcelApi.change_parcel_status(jsonObject);
        recyclerView.setAdapter(adapter);
    }
    public static void deleteParcel(JsonObject jsonObject){
        parcelApi.delete_parcel(jsonObject);
        recyclerView.setAdapter(adapter);
    }

    public static String getUserName(){
        return userData.getName();
    }
    public static void changedView(){
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}