package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.rarestardev.morimint.R;
import com.rarestardev.morimint.ViewModel.ApplicationDataViewModel;
import com.rarestardev.morimint.databinding.ActivityEarnBinding;

public class EarnActivity extends AppCompatActivity {
    ActivityEarnBinding binding;
    ApplicationDataViewModel applicationDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_earn);


        binding.dailyReward.setOnClickListener(v ->
                startActivity(new Intent(EarnActivity.this,DailyRewardActivity.class)));

        applicationDataViewModel = new ViewModelProvider(this).get(ApplicationDataViewModel.class);


        binding.editTextCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.btnCheckCode.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    binding.btnCheckCode.setVisibility(View.VISIBLE);
                    binding.btnCheckCode.setOnClickListener(v ->
                            applicationDataViewModel.SiteGiftCode(EarnActivity.this,s.toString()));
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
                    binding.btnCheckCodeApp.setOnClickListener(v ->
                            applicationDataViewModel.GiftCode(EarnActivity.this,s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

}