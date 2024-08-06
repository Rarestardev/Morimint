package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.ActivityAdvertisingBinding;

public class AdvertisingActivity extends AppCompatActivity {
    ActivityAdvertisingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_advertising);


        binding.cooperation.setOnClickListener(v ->
            Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show());

    }
}