package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.EditText;

import com.eos.parcelnotice.data.DummyData;
import com.eos.parcelnotice.databinding.ActivityNoticeBinding;


public class NoticeActivity extends AppCompatActivity {
    EditText ed_notice;
    DummyData dummyData = new DummyData();
    ActivityNoticeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_notice);

        binding.editTextNotice.setText(DummyData.Notice);


    }
}