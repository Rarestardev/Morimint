package com.rarestardev.morimint.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.ApplicationDataViewModel;
import com.rarestardev.morimint.databinding.ActivityGiftCodeBinding;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppSDK;

public class GiftCodeActivity extends AppCompatActivity {
    private ActivityGiftCodeBinding binding;
    private static final String ADS_TAG = "StartApp";

    ApplicationDataViewModel applicationDataViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTabletMode()){
            binding = DataBindingUtil.setContentView(this,R.layout.activity_gift_code_tablet);
        }else {
            binding = DataBindingUtil.setContentView(this,R.layout.activity_gift_code);
        }


        StartAppSDK.init(this, UserConstants.startAppId, true);
        StartAppSDK.setTestAdsEnabled(UserConstants.startAppIsTested);
        Banner startAppBanner = binding.startappBanner;
        Log.d(ADS_TAG,startAppBanner + "");
        startAppBanner.loadAd();


        applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);


        binding.editTextCodeSite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.btnCheckCode.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    binding.btnCheckCode.setVisibility(View.VISIBLE);
                    binding.btnCheckCode.setOnClickListener(v -> {
                        applicationDataViewModel.SiteGiftCode(GiftCodeActivity.this,s.toString().trim());
                        binding.editTextCodeSite.setText("");
                        binding.btnCheckCode.setVisibility(View.GONE);
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        binding.editTextCodeApp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.btnCheckCodeApp.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    binding.btnCheckCodeApp.setVisibility(View.VISIBLE);
                    binding.btnCheckCodeApp.setOnClickListener(v -> {
                        applicationDataViewModel.GiftCode(GiftCodeActivity.this,s.toString().trim());
                        binding.editTextCodeApp.setText("");
                        binding.btnCheckCodeApp.setVisibility(View.GONE);
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        binding.editTextMiniApp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.btnCheckCodeMiniApp.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    binding.btnCheckCodeMiniApp.setVisibility(View.VISIBLE);
                    binding.btnCheckCodeMiniApp.setOnClickListener(v -> {
                        applicationDataViewModel.MiniAppCode(GiftCodeActivity.this,s.toString().trim());
                        binding.btnCheckCodeMiniApp.setVisibility(View.GONE);
                        binding.editTextMiniApp.setText("");
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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