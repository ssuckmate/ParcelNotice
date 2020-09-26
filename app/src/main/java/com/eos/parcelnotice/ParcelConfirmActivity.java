package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.eos.parcelnotice.adapter.ParcelConfirmAdapter;

public class ParcelConfirmActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_confirm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.parcel_confirm_toolbar);
        setSupportActionBar(toolbar);

        //컨텐츠 변경시 Holder의 사이즈를 체크하는데, 그걸 해제하는 기능. 이거 설정하면 성능 향상 가능!!
        recyclerView = findViewById(R.id.parcel_confirm_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ParcelConfirmAdapter();
        recyclerView.setAdapter(adapter);
    }
}