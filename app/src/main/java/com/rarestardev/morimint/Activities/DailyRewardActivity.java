package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;

import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.ApplicationDataViewModel;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppSDK;

public class DailyRewardActivity extends AppCompatActivity {
    RecyclerView dailyRewardRecyclerView;
    AppCompatTextView tvNoData;
    RelativeLayout dailyBonus;
    ApplicationDataViewModel applicationDataViewModel;
    private static final String ADS_TAG = "StartApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isTabletMode()){
            setContentView(R.layout.activity_daily_reward_tablet);
        }else {
            setContentView(R.layout.activity_daily_reward);
        }

        dailyRewardRecyclerView = findViewById(R.id.dailyRewardRecyclerView);
        dailyBonus = findViewById(R.id.dailyBonus);
        tvNoData = findViewById(R.id.tvNoData);

        applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);
        applicationDataViewModel.GetDataDailyReward(dailyRewardRecyclerView, this,tvNoData);

        StartAppSDK.init(this, UserConstants.startAppId, true);
        StartAppSDK.setTestAdsEnabled(UserConstants.startAppIsTested);
        Banner startAppBanner = findViewById(R.id.startapp_banner);
        Log.d(ADS_TAG,startAppBanner + "");
        startAppBanner.loadAd();


        dailyBonus.setOnClickListener(v -> startActivity(new Intent(DailyRewardActivity.this, DailyCheckActivity.class)));
    }

    private boolean isTabletMode(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;

        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        return diagonalInches >= 7.0; // Tablet 7 inches
    }
}