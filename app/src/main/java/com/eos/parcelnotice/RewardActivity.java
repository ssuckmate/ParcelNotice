package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;

public class RewardActivity extends AppCompatActivity {

    private TableLayout plusTable;
    private TableLayout minusTable;
    private int plusTotal;
    private int minusTotal;
    private EditText ed_plusTotal;
    private EditText ed_minusTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reward);

        plusTotal = 0;
        minusTotal = 0;

        for(int i = 0; i<DummyData.plusPoints.size(); i++){
            plusTotal += DummyData.plusPoints.get(i).getPoint();
        }

        for(int i = 0; i<DummyData.minusPoints.size(); i++){
            minusTotal += DummyData.minusPoints.get(i).getPoint();
        }

        ed_plusTotal = (EditText)findViewById(R.id.EditText_plus_point);
        ed_minusTotal = (EditText)findViewById(R.id.EditText_minus_point);

        ed_plusTotal.setText(plusTotal+"점");
        ed_minusTotal.setText(minusTotal+"점");

        plusTable = (TableLayout)findViewById(R.id.TableLayout_Plus);

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

            plusTable.addView(tableRow);
        }


        minusTable = (TableLayout)findViewById(R.id.TableLayout_Minus);

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

            minusTable.addView(tableRow);
        }

    }
}