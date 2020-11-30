package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnParcelConfirm, btnLaundryConfirm, btnRewardConfirm, btnNoticeConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnParcelConfirm = findViewById(R.id.button_main_parcel);
        btnLaundryConfirm = findViewById(R.id.button_main_laundry_room);
        btnRewardConfirm = findViewById(R.id.button_main_reward);
        btnNoticeConfirm = findViewById(R.id.button_main_notice);
        Intent i = getIntent();
        final String userID = i.getStringExtra("userID");

        btnParcelConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ParcelConfirmActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        btnLaundryConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LaundryConfirmActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        btnRewardConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RewardActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }

        });


        btnNoticeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });
    }
}