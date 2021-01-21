package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.eos.parcelnotice.data.PointData;
import com.eos.parcelnotice.databinding.ActivityRewardBinding;
import com.eos.parcelnotice.retrofit.PointApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RewardActivity extends AppCompatActivity {

    private int plusTotal;
    private int minusTotal;
    ActivityRewardBinding binding;
    List<PointData> plusPoints;
    List<PointData> minusPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_reward);
        binding.setActivity(this);

        init();

    }

    private void init() {
        plusTotal = 0;
        minusTotal = 0;
        plusPoints = new ArrayList<>();
        minusPoints = new ArrayList<>();


        Call<List<PointData>> callPoint = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PointApi.class)
                .get_point(getSharedPreferences("token",0).getString("token",""));
        callPoint.enqueue(new Callback<List<PointData>>() {
            @Override
            public void onResponse(Call<List<PointData>> call, Response<List<PointData>> response) {
                List<PointData> points = response.body();
                PointData point;
                for(int i=0; i<points.size();i++){
                    point = points.get(i);
                    if(point.getType().equals("상점")){
                        plusTotal += point.getAmount();
                        plusPoints.add(point);
                    }
                    else{
                        minusTotal += point.getAmount();
                        minusPoints.add(point);
                    }
                }
                binding.EditTextPlusPoint.setText(plusTotal+"점");
                binding.EditTextMinusPoint.setText(minusTotal+"점");

                initPlus();
                initMinus();
                if(points.size()==0) Toast.makeText(RewardActivity.this,"등록된 상벌점이 없습니다.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<PointData>> call, Throwable t) { }
        });
    }

    private void initPlus(){

        for(int i = 0; i < plusPoints.size(); i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            PointData plusPoint = plusPoints.get(i);

            TextView tv_arrival = new TextView(this);
            SimpleDateFormat transFormat = new SimpleDateFormat("yy.MM.dd");

            tv_arrival.setText(transFormat.format(plusPoint.getCreatedAt()));
            tv_arrival.setGravity(Gravity.CENTER);
            tv_arrival.setTextSize(18);
            tv_arrival.setHeight(100);
            tableRow.addView(tv_arrival);

            TextView tv_point = new TextView(this);
            tv_point.setText(Integer.toString(plusPoint.getAmount()));
            tv_point.setGravity(Gravity.CENTER);
            tv_point.setTextSize(18);
            tv_point.setHeight(100);
            tableRow.addView(tv_point);

            TextView tv_reason = new TextView(this);
            tv_reason.setText(plusPoint.getReason());
            tv_reason.setGravity(Gravity.CENTER);
            tv_reason.setTextSize(18);
            tv_reason.setHeight(100);
            tableRow.addView(tv_reason);

            binding.TableLayoutPlus.addView(tableRow);
        }
    }

    private void initMinus(){

        for(int i = 0; i < minusPoints.size(); i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            PointData minusPoint = minusPoints.get(i);

            TextView tv_arrival = new TextView(this);
            SimpleDateFormat transFormat = new SimpleDateFormat("yy.MM.dd");

            tv_arrival.setText(transFormat.format(minusPoint.getCreatedAt()));
            tv_arrival.setGravity(Gravity.CENTER);
            tv_arrival.setTextSize(18);
            tv_arrival.setHeight(100);
            tableRow.addView(tv_arrival);

            TextView tv_point = new TextView(this);
            tv_point.setText(Integer.toString(minusPoint.getAmount()));
            tv_point.setGravity(Gravity.CENTER);
            tv_point.setTextSize(18);
            tv_point.setHeight(100);
            tableRow.addView(tv_point);

            TextView tv_reason = new TextView(this);
            tv_reason.setText(minusPoint.getReason());
            tv_reason.setGravity(Gravity.CENTER);
            tv_reason.setTextSize(18);
            tv_reason.setHeight(100);
            tableRow.addView(tv_reason);

            binding.TableLayoutMinus.addView(tableRow);
        }
    }

}