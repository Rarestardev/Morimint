package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.rarestardev.morimint.Adapters.DailyCheckAdapter;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.OfflineModel.DailyCheckModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.ActivityDailyCheckBinding;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyCheckActivity extends AppCompatActivity {
    private ActivityDailyCheckBinding binding;
    List<DailyCheckModel> dailyCheckModel = new ArrayList<>();
    private static final String ADS_TAG = "StartApp";

    private static final String[] REWARDS = {"","1,000","5,000","10,000","15,000","20,000","25,000","40,000"};

    private static final long[] COIN = {0,1000,5000,10000,15000,20000,25000,40000};

    private static final String SHARED_PREF_NAME = "DailyCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isTabletMode()){
            binding = DataBindingUtil.setContentView(this,R.layout.activity_daily_check_tablet);
        }else {
            binding = DataBindingUtil.setContentView(this,R.layout.activity_daily_check);
        }
        setDailyChecks();


        StartAppSDK.init(this, UserConstants.startAppId, true);
        StartAppSDK.setTestAdsEnabled(UserConstants.startAppIsTested);
        Banner startAppBanner = binding.startappBanner;
        Log.d(ADS_TAG,startAppBanner + "");
        startAppBanner.loadAd();


    }

    private boolean isTabletMode(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;

        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        return diagonalInches >= 7.0; // Tablet 7 inches
    }


    private void setDailyChecks(){
        Calendar calendar = Calendar.getInstance();

        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

        for (int i = 1; i <= 7; i++) {
            boolean isClaimed = getRewardStatus(i);
            dailyCheckModel.add(new DailyCheckModel(i, isClaimed,REWARDS[i],COIN[i]));
        }

        DailyCheckAdapter dailyCheckAdapter = new DailyCheckAdapter(this,dailyCheckModel,currentDay);
        binding.rewardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.rewardRecyclerView.setHasFixedSize(true);
        binding.rewardRecyclerView.refreshDrawableState();
        binding.rewardRecyclerView.setAdapter(dailyCheckAdapter);
    }

    private boolean getRewardStatus(int day) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        return sharedPreferences.getBoolean(String.valueOf(day), false);
    }
}