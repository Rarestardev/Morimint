package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.ActivityAdvertisingBinding;

public class AdvertisingActivity extends AppCompatActivity {
    private ActivityAdvertisingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_advertising);


        binding.cooperation.setOnClickListener(v ->{
            binding.adsInfo.setVisibility(View.GONE);
            binding.registerAds.setVisibility(View.VISIBLE);
        });

    }

    @Override
    public void onBackPressed() {
        if (binding.adsInfo.getVisibility() == View.VISIBLE){
            super.onBackPressed();
        }else {
            binding.registerAds.setVisibility(View.GONE);
            binding.adsInfo.setVisibility(View.VISIBLE);
        }

    }
}