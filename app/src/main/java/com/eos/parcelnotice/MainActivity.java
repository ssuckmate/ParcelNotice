package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        btnParcelConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ParcelConfirmActivity.class);
                startActivity(intent);
            }
        });
    }
}