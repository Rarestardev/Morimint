package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.rarestardev.morimint.Adapters.DailyCheckAdapter;
import com.rarestardev.morimint.Model.DailyCheckModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.ActivityDailyBonusBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyCheckActivity extends AppCompatActivity {
    private ActivityDailyBonusBinding binding;
    List<DailyCheckModel> dailyCheckModel = new ArrayList<>();

    private static final String[] REWARDS = {"","1K","2K","5K","8K","10K","12K","15K","20K"
            ,"30K","40K","50K","100K","120K","140K","180K","200K","250K","280K","300K"
            ,"320K","350K","380K","400K","420K","450K","500K","550K","600K","700K","900K","1M"};

    private static final long[] COIN = {0,1000,2000,5000,8000,10000,12000,15000,20000
            ,30000,40000,50000,100000,120000,140000,180000,200000,250000,280000,300000
            ,320000,350000,380000,400000,420000,450000,500000,550000,600000,700000,900000,1000000};

    private static final String SHARED_PREF_NAME = "DailyCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_daily_bonus);
        setDailyChecks();
    }


    private void setDailyChecks(){
        Calendar calendar = Calendar.getInstance();

        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= 31; i++) {
            boolean isClaimed = getRewardStatus(i);
            dailyCheckModel.add(new DailyCheckModel(i, isClaimed,REWARDS[i],COIN[i]));
        }

        DailyCheckAdapter dailyCheckAdapter = new DailyCheckAdapter(this,dailyCheckModel,currentDay);
        binding.rewardRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        binding.rewardRecyclerView.setHasFixedSize(true);
        binding.rewardRecyclerView.refreshDrawableState();
        binding.rewardRecyclerView.setAdapter(dailyCheckAdapter);
    }

    private boolean getRewardStatus(int day) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        return sharedPreferences.getBoolean(String.valueOf(day), false);
    }
}