package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.ActivityEarnBinding;

public class EarnActivity extends AppCompatActivity {
    ActivityEarnBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_earn);

        binding.dailyReward.setOnClickListener(v ->
                startActivity(new Intent(EarnActivity.this,DailyRewardActivity.class)));

    }
}