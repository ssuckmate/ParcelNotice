package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.style.DynamicDrawableSpan;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.eos.parcelnotice.data.DummyData;
import com.eos.parcelnotice.data.RewardData;
import com.eos.parcelnotice.databinding.ActivityRewardBinding;

import java.text.SimpleDateFormat;

public class RewardActivity extends AppCompatActivity {

    private int plusTotal;
    private int minusTotal;
    ActivityRewardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_reward);
        binding.setActivity(this);

        plusTotal = 0;
        minusTotal = 0;

        for(int i = 0; i<DummyData.plusPoints.size(); i++){
            plusTotal += DummyData.plusPoints.get(i).getPoint();
        }

        for(int i = 0; i<DummyData.minusPoints.size(); i++){
            minusTotal += DummyData.minusPoints.get(i).getPoint();
        }


        binding.EditTextPlusPoint.setText(plusTotal+"점");
        binding.EditTextMinusPoint.setText(minusTotal+"점");


        for(int i = 0; i < DummyData.plusPoints.size(); i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv_arrival = new TextView(this);
            RewardData rd_plus = DummyData.plusPoints.get(DummyData.plusPoints.size()-1-i);
            SimpleDateFormat transFormat = new SimpleDateFormat("yy.MM.dd");

            tv_arrival.setText(transFormat.format(rd_plus.getArrival()));
            tv_arrival.setGravity(Gravity.CENTER);
            tv_arrival.setTextSize(18);
            tableRow.addView(tv_arrival);

            TextView tv_point = new TextView(this);
            tv_point.setText(Integer.toString(rd_plus.getPoint()));
            tv_point.setGravity(Gravity.CENTER);
            tv_point.setTextSize(18);
            tableRow.addView(tv_point);

            TextView tv_reason = new TextView(this);
            tv_reason.setText(rd_plus.getReason());
            tv_reason.setGravity(Gravity.CENTER);
            tv_reason.setTextSize(18);
            tableRow.addView(tv_reason);

            binding.TableLayoutPlus.addView(tableRow);
        }



        for(int i = 0; i < DummyData.minusPoints.size(); i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tv_arrival = new TextView(this);
            RewardData rd_minus= DummyData.minusPoints.get(DummyData.minusPoints.size()-1-i);
            SimpleDateFormat transFormat = new SimpleDateFormat("yy.MM.dd");

            tv_arrival.setText(transFormat.format(rd_minus.getArrival()));
            tv_arrival.setGravity(Gravity.CENTER);
            tv_arrival.setTextSize(18);
            tableRow.addView(tv_arrival);

            TextView tv_point = new TextView(this);
            tv_point.setText(Integer.toString(rd_minus.getPoint()));
            tv_point.setGravity(Gravity.CENTER);
            tv_point.setTextSize(18);
            tableRow.addView(tv_point);

            TextView tv_reason = new TextView(this);
            tv_reason.setText(rd_minus.getReason());
            tv_reason.setGravity(Gravity.CENTER);
            tv_reason.setTextSize(18);
            tableRow.addView(tv_reason);

            binding.TableLayoutMinus.addView(tableRow);
        }

    }
}