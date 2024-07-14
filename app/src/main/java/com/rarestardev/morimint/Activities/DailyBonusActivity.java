package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.rarestardev.morimint.Adapters.DailyBonusAdapter;
import com.rarestardev.morimint.OfflineModel.DailyBonusModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.ActivityDailyBonusBinding;

import java.util.ArrayList;
import java.util.List;

public class DailyBonusActivity extends AppCompatActivity {
    ActivityDailyBonusBinding binding;
    DailyBonusAdapter adapter;
    List<DailyBonusModel> dailyBonusModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_daily_bonus);

        dailyBonusModels = new ArrayList<>();

        dailyBonusModels.add(new DailyBonusModel("Day 1","1 K",false,false,false));
        dailyBonusModels.add(new DailyBonusModel("Day 2","2 K",true,false,false));
        dailyBonusModels.add(new DailyBonusModel("Day 3","5 K",true,false,false));
        dailyBonusModels.add(new DailyBonusModel("Day 4","10 K",true,false,false));
        dailyBonusModels.add(new DailyBonusModel("Day 5","50 K",true,false,false));
        dailyBonusModels.add(new DailyBonusModel("Day 6","70 K",true,false,false));
        dailyBonusModels.add(new DailyBonusModel("Day 7","90 K",true,false,false));
        dailyBonusModels.add(new DailyBonusModel("Day 8","100 K",true,false,false));
        dailyBonusModels.add(new DailyBonusModel("Day 9","500 K",true,false,false));
        dailyBonusModels.add(new DailyBonusModel("Day 10","1.000.000 M",true,false,true));

        adapter = new DailyBonusAdapter(this,dailyBonusModels);
        binding.rewardRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        binding.rewardRecyclerView.setHasFixedSize(true);
        binding.rewardRecyclerView.refreshDrawableState();
        binding.rewardRecyclerView.bringToFront();
        binding.rewardRecyclerView.setAdapter(adapter);

    }
}