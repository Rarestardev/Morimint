package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.rarestardev.morimint.Adapters.DailyCheckAdapter;
import com.rarestardev.morimint.Model.DailyCheckModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.ActivityDailyBonusBinding;

import java.util.ArrayList;
import java.util.List;

public class DailyCheckActivity extends AppCompatActivity {
    ActivityDailyBonusBinding binding;
    List<DailyCheckModel> dailyCheckModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_daily_bonus);
        setDailyChecks();
    }


    private void setDailyChecks(){
        for (int i = 1; i <= 30; i++){
            dailyCheckModel.add(new DailyCheckModel(i,i * 1000,i,""));
        }

        DailyCheckAdapter dailyCheckAdapter = new DailyCheckAdapter(this,dailyCheckModel);
        binding.rewardRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        binding.rewardRecyclerView.setHasFixedSize(true);
        binding.rewardRecyclerView.refreshDrawableState();
        binding.rewardRecyclerView.setAdapter(dailyCheckAdapter);
    }
}