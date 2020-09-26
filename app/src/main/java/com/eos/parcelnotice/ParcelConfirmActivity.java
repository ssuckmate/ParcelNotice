package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class ParcelConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_confirm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.parcel_confirm_toolbar);
        setSupportActionBar(toolbar);
    }
}