package com.eos.parcelnotice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eos.parcelnotice.R;

import java.util.Timer;

public class LaundryView extends LinearLayout {
    TextView tvLaundryMachine,tvLaundryUse,tvLaundryUser, tvLaundryTIme;
    Button btnLaundry;
    volatile CountDownTimer timer;


    public CountDownTimer getTimer() {
        return timer;
    }

    public synchronized void setTimer(CountDownTimer cdt) {
        this.timer = timer;
    }

    public LaundryView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_laundry_confirm_view,this,true);

        tvLaundryMachine = (TextView) findViewById(R.id.textView_laundry_machine);
        tvLaundryUse = (TextView) findViewById(R.id.textView_laundry_use);
        tvLaundryUser = (TextView) findViewById(R.id.textView_laundry_user);
        tvLaundryTIme = (TextView) findViewById(R.id.textView_laundry_time);
        btnLaundry = (Button) findViewById(R.id.laundry_button);
        timer = null;
    }

    public void setLaundryMachine(String machine){
        tvLaundryMachine.setText(machine);
    }

    public void setLaundryUse(String use){
        if(use.equals("사용중")) tvLaundryUse.setTextColor(Color.parseColor("#FF0000"));
        else tvLaundryUse.setTextColor(Color.parseColor("#14A534"));
        tvLaundryUse.setText(use);
    }

    public void setLaundryUser(String user){
        tvLaundryUser.setText(user);
    }

    public void setLaundryButton(String use){
        btnLaundry.setText(use);
    }

    public void setLaundryTime(String time){
        tvLaundryTIme.setText(time);
    }
}
