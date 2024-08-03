package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.ApplicationDataViewModel;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppSDK;

public class TaskActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AppCompatTextView tvNoData;
    ApplicationDataViewModel applicationDataViewModel;
    private static final String ADS_TAG = "StartApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        recyclerView = findViewById(R.id.recyclerView);
        tvNoData = findViewById(R.id.tvNoData);
        applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);
        applicationDataViewModel.getTasks(TaskActivity.this,recyclerView,tvNoData);


        StartAppSDK.init(this, UserConstants.startAppId, true);
        StartAppSDK.setTestAdsEnabled(UserConstants.startAppIsTested);
        Banner startAppBanner = findViewById(R.id.startapp_banner);
        Log.d(ADS_TAG,startAppBanner + "");
        startAppBanner.loadAd();

    }
}