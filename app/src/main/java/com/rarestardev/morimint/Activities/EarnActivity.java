package com.rarestardev.morimint.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.Repository.CoinManagerRepository;
import com.rarestardev.morimint.Utilities.DialogType;
import com.rarestardev.morimint.Utilities.StatusDialog;
import com.rarestardev.morimint.databinding.ActivityEarnBinding;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

public class EarnActivity extends AppCompatActivity {
    ActivityEarnBinding binding;

    private static final String ADS_TAG = "StartApp";
    private static final String PREFS_NAME = "DailyUpdater";

    private int chance;

    private StartAppAd startAppAd;

    private StatusDialog statusDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTabletMode()){
            binding = DataBindingUtil.setContentView(this, R.layout.activity_earn_tablet);
        }else {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_earn);
        }

        statusDialog = new StatusDialog(this, DialogType.LOADING);

        StartAppSDK.init(this, UserConstants.startAppId, true);
        StartAppSDK.setTestAdsEnabled(UserConstants.startAppIsTested);
        Banner startAppBanner = binding.startappBanner;
        Log.d(ADS_TAG, startAppBanner + "");
        startAppBanner.loadAd();
        startAppAd = new StartAppAd(this);

        updateChanceAd();

        binding.rewardLayout.setOnClickListener(v ->
                startActivity(new Intent(EarnActivity.this, DailyRewardActivity.class)));

        binding.earnCode.setOnClickListener(v ->
                startActivity(new Intent(EarnActivity.this, GiftCodeActivity.class)));

        MoreCoin();

    }

    private boolean isTabletMode(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;

        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        return diagonalInches >= 7.0; // Tablet 7 inches
    }

    @SuppressLint("SetTextI18n")
    private void updateChanceAd(){
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        chance = preferences.getInt("reward",0);

        binding.chanceAdShow.setText("( " + chance + " / " + "5" + " )");
    }


    @SuppressLint("SetTextI18n")
    private void MoreCoin() {
        binding.makeMoney.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (chance == 0){
                    Toast.makeText(EarnActivity.this, "The ad will be activated the next day", Toast.LENGTH_SHORT).show();
                }else {
                    statusDialog.setTitleDialog("Loading");
                    statusDialog.setMessageDialog("Please wait");
                    statusDialog.setCancelable(false);
                    statusDialog.show();
                    HandleAds();
                }
            }
        });
    }


    private void HandleAds() {
        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
            @Override
            public void onReceiveAd(@NonNull Ad ad) {
                startAppAd.showAd();
                statusDialog.dismiss();
                GiftCoin();
            }

            @Override
            public void onFailedToReceiveAd(@Nullable Ad ad) {
                Toast.makeText(EarnActivity.this, "Try Again Later", Toast.LENGTH_SHORT).show();
                Log.d(ADS_TAG,"Failed");
                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                if (preferences.getInt("reward",0) != 5){
                    editor.putInt("reward", +1);
                    editor.apply();
                }
                statusDialog.dismiss();
            }
        });
    }

    private void GiftCoin(){
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        startAppAd.setVideoListener(() -> {
            final StatusDialog dialog = new StatusDialog(EarnActivity.this,DialogType.SUCCESS);
            dialog.setTitleDialog("Good job");
            dialog.setMessageDialog("You received 10,000 Coins");
            dialog.setCancelable(false);
            dialog.setButtonText("Claim");
            dialog.setButtonListener(v -> {
                chance -= 1;
                editor.putInt("reward", chance);
                editor.apply();
                updateChanceAd();
                if (chance < 0) {
                    chance = 0;
                    editor.putInt("reward", chance);
                    editor.apply();
                    updateChanceAd();
                }
                CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
                coinManagerRepository.UpdateCoin(10000,EarnActivity.this);
                dialog.dismiss();
            });
        });
    }
}