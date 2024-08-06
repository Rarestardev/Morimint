package com.rarestardev.morimint.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rarestardev.morimint.Adapters.LevelManagerAdapter;
import com.rarestardev.morimint.ApplicationSetup.ProgressBarManager;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.OfflineModel.LevelManagerModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.ActivityLevelBinding;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class LevelActivity extends AppCompatActivity {

    private ActivityLevelBinding binding;

    boolean[] LEVEL_PASSED = {true, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private static final int[] LEVEL = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static final int[] LEVEL_ENERGY = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 14000, 16000, 18000};
    private static final int[] LEVEL_TAP = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 18};

    private static final int[] LEVEL_ITEM = {R.drawable.level_one, R.drawable.level_two,
            R.drawable.level_three, R.drawable.level_four,
            R.drawable.level_five, R.drawable.level_six,
            R.drawable.level_seven, R.drawable.level_eight,
            R.drawable.level_nine, R.drawable.level_ten,
            R.drawable.level_eleven, R.drawable.level_twelve,
            R.drawable.level_thirteen, R.drawable.level_fourteen,
            R.drawable.level_fifteen};

    private static final String SHARED_COIN_MINT_NAME = "Balance";
    private static final String SHARED_COIN_MINT_NAME_KEY = "Coin";

    long totalBalance;
    int currentLevel;
    int progressStatus;
    int maxProgress;
    int minProgress;

    List<LevelManagerModel> levelManagerModels = new ArrayList<>();

    ProgressBarManager progressBarManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isTabletMode()){
            binding = DataBindingUtil.setContentView(this,R.layout.activity_level_tablet);
        }else {
            binding = DataBindingUtil.setContentView(this,R.layout.activity_level);
        }


        progressBarManager = new ProgressBarManager(this);

        SharedPreferences sharedPreferences = getSharedPreferences("Energy Manager", Context.MODE_PRIVATE);
        currentLevel = sharedPreferences.getInt("LEVEL", 0);


        SharedPreferences preferences = getSharedPreferences(SHARED_COIN_MINT_NAME, Context.MODE_PRIVATE);
        totalBalance = preferences.getLong(SHARED_COIN_MINT_NAME_KEY, 0);

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat numberFormat = new DecimalFormat("###,###,###,###", decimalFormatSymbols);
        numberFormat.setGroupingSize(3);
        numberFormat.setMaximumFractionDigits(2);
        binding.tvBalanceCoin.setText(numberFormat.format(totalBalance));


        binding.iconCloseDialog.setOnClickListener(v -> this.finish());
        binding.tvLevel.setText("Level : " + currentLevel);
        setImageWithLevel();
        levelManager();
        ProgressBar();
    }

    private boolean isTabletMode(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;

        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        return diagonalInches >= 7.0; // Tablet 7 inches
    }

    private void ProgressBar() {
        SharedPreferences sharedPreferences = getSharedPreferences("Energy Manager", Context.MODE_PRIVATE);
        int level = sharedPreferences.getInt("LEVEL", 0);
        switch (level) {
            case 1:
                progressStatus = (int) totalBalance;
                minProgress = 0;
                maxProgress = (int) UserConstants.LEVEL_COIN[1];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 2:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[1];
                maxProgress = (int) UserConstants.LEVEL_COIN[2];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 3:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[2];
                maxProgress = (int) UserConstants.LEVEL_COIN[3];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 4:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[3];
                maxProgress = (int) UserConstants.LEVEL_COIN[4];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 5:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[4];
                maxProgress = (int) UserConstants.LEVEL_COIN[5];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 6:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[5];
                maxProgress = (int) UserConstants.LEVEL_COIN[6];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 7:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[6];
                maxProgress = (int) UserConstants.LEVEL_COIN[7];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 8:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[7];
                maxProgress = (int) UserConstants.LEVEL_COIN[8];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 9:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[8];
                maxProgress = (int) UserConstants.LEVEL_COIN[9];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 10:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[9];
                maxProgress = (int) UserConstants.LEVEL_COIN[10];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 11:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[10];
                maxProgress = (int) UserConstants.LEVEL_COIN[11];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 12:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[11];
                maxProgress = (int) UserConstants.LEVEL_COIN[12];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 13:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[12];
                maxProgress = (int) UserConstants.LEVEL_COIN[13];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 14:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[13];
                maxProgress = (int) UserConstants.LEVEL_COIN[14];
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
            case 15:
                minProgress = 0;
                progressStatus = 100;
                maxProgress = 100;
                initProgressBarValues(maxProgress,minProgress,progressStatus);
                break;
        }
    }

    private void initProgressBarValues(int max,int min,int progress) {
        binding.setProgress(min);

        binding.levelProgressBar.setMax(max);
        binding.levelProgressBar.setProgress(progress);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void setImageWithLevel() {
        switch (currentLevel) {
            case 1:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[0]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[0]);
                break;
            case 2:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[1]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[1]);
                break;
            case 3:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[2]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[2]);
                break;
            case 4:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[3]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[3]);
                break;
            case 5:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[4]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[4]);
                break;
            case 6:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[5]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[5]);
                break;
            case 7:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[6]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[6]);
                break;
            case 8:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[7]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[7]);
                break;
            case 9:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[8]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[8]);
                break;
            case 10:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[9]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[9]);
                break;
            case 11:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[10]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[10]);
                break;
            case 12:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[11]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[11]);
                break;
            case 13:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[12]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[12]);
                break;
            case 14:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[13]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[13]);
                break;
            case 15:
                binding.imageViewCharacter.setImageDrawable(getDrawable(LEVEL_ITEM[14]));
                binding.tvTap.setText("Tap : " + LEVEL_TAP[14]);
                break;
        }
    }

    private void levelManager() {
        for (int i = 0; i < LEVEL.length; i++) {
            if (i <= currentLevel - 1) {
                LEVEL_PASSED[i] = true;
            }
            levelManagerModels.add(new LevelManagerModel(
                    LEVEL[i],
                    LEVEL_ITEM[i],
                    LEVEL_PASSED[i],
                    LEVEL_ENERGY[i],
                    LEVEL_TAP[i],
                    UserConstants.LEVEL_COIN[i]
            ));
        }

        LevelManagerAdapter adapter = new LevelManagerAdapter(LevelActivity.this, levelManagerModels, currentLevel);
        binding.levelsRecyclerView.setHasFixedSize(true);
        binding.levelsRecyclerView.setLayoutManager(new LinearLayoutManager(LevelActivity.this));
        binding.levelsRecyclerView.setAdapter(adapter);
        binding.levelsRecyclerView.refreshDrawableState();
    }
}