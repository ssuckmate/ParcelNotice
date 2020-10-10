package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.EditText;

import com.eos.parcelnotice.data.DummyData;


public class NoticeActivity extends AppCompatActivity {
    EditText ed_notice;
    DummyData dummyData = new DummyData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ed_notice = (EditText)findViewById(R.id.editText_notice);
        ed_notice.setText(DummyData.Notice);


    }
}