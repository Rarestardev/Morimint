package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.DailyRewardViewModel;

public class DailyRewardActivity extends AppCompatActivity {
    RecyclerView dailyRewardRecyclerView;
    RelativeLayout dailyBonus;
    DailyRewardViewModel dailyRewardViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_reward);

        dailyRewardRecyclerView = findViewById(R.id.dailyRewardRecyclerView);
        dailyBonus = findViewById(R.id.dailyBonus);

        dailyRewardViewModel = new ViewModelProvider(this).get(DailyRewardViewModel.class);
        dailyRewardViewModel.GetDataDailyReward(dailyRewardRecyclerView,this);

        dailyBonus.setOnClickListener(v -> startActivity(new Intent(DailyRewardActivity.this,DailyBonusActivity.class)));
    }
}