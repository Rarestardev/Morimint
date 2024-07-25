package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.ActivityReferralBinding;

public class ReferralActivity extends AppCompatActivity {
    ActivityReferralBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_referral);


        binding.totalInvite.setText(getIntent().getIntExtra("TotalReferral",0) + " Referral");

        int total_bonus = getIntent().getIntExtra("TotalReferral",0) * 50000;

        binding.totalBonus.setText("+" + total_bonus);
    }
}