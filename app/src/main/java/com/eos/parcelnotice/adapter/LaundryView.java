package com.eos.parcelnotice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eos.parcelnotice.R;

public class LaundryView extends LinearLayout {
    TextView tvLaundryMachine,tvLaundryUse,tvLaundryUser, tvLaundryTime;
    Button btnLaundry;

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
        tvLaundryTime = (TextView) findViewById(R.id.textView_laundry_time);
        btnLaundry = (Button) findViewById(R.id.laundry_button);
    }

    public void setLaundryMachine(String machine){
        tvLaundryMachine.setText(machine);
    }

    public void setLaundryUse(String use){
        if(use.equals("사용중")){
            tvLaundryUse.setTextColor(Color.parseColor("#FF0000"));
            tvLaundryTime.setVisibility(VISIBLE);
        }
        else{
            tvLaundryUse.setTextColor(Color.parseColor("#14A534"));
            tvLaundryTime.setVisibility(INVISIBLE);
        }
        tvLaundryUse.setText(use);
    }

    public void setLaundryUser(String user){
        tvLaundryUser.setText(user);
    }

    public void setLaundryButton(String use){
        btnLaundry.setText(use);
    }

    public void setLaundryTime(String time){
        tvLaundryTime.setText(time);
    }
}
