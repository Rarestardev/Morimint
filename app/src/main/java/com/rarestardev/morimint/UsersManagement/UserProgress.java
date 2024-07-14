package com.rarestardev.morimint.UsersManagement;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.rarestardev.morimint.Constants.MintValues;

public class UserProgress {
    private int coin;
    private TextView textView;
    private ProgressBar progressBar;
    private int adsCount = 0;
    private int jackpotCount = 0;
    private int taskCount = 0;
    private int referralCount = 0;

    private static final int[] LEVEL_COINS = {
            50000, 100000, 200000, 300000, 500000, 600000, 800000, 1000000,
            2500000, 4000000, 6000000, 8000000, 10000000, 15000000
    };
    private static final int[] LEVEL_ADS = {5, 10, 20, 30, 40, 50, 60, 70, 80, 100, 120, 140, 160, 180};
    private static final int[] LEVEL_TASK = {3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 30};
    private static final int[] LEVEL_JACKPOT = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 150};
    private static final int[] LEVEL_REFERRAL = {2, 3, 4, 5, 7, 10};
    private static final int[] LEVEL_XP = {30, 50, 70, 85, 100};

    public UserProgress(int coin, TextView textView, ProgressBar progressBar) {
        this.coin = coin;
        this.textView = textView;
        this.progressBar = progressBar;
    }

    public void levelUserManagement() {
        textView.setText("1");
        for (int i = 0; i < LEVEL_COINS.length; i++) {
            if (coin > LEVEL_COINS[i] && (i + 1 >= LEVEL_COINS.length || coin <= LEVEL_COINS[i + 1])) {
                progressSetValue(LEVEL_XP[i]);
                if (adsCount >= LEVEL_ADS[i] && taskCount >= LEVEL_TASK[i] && jackpotCount >= LEVEL_JACKPOT[i] && referralCount >= LEVEL_REFERRAL[Math.min(i, LEVEL_REFERRAL.length - 1)]) {
                    setLevelValues(i + 2);
                }
                break;
            }
        }
    }

    private void progressSetValue(int value) {
        progressBar.setProgress(value);
    }

    private void setLevelValues(int level) {
        textView.setText(String.valueOf(level));
        MintValues.MaxEnergy = level * 1000;
        MintValues.mint = level;
    }

    public void adsCounter() {
        adsCount++;
        levelUserManagement();
    }

    public void jackpotCounter() {
        jackpotCount++;
        levelUserManagement();
    }

    public void taskCounter() {
        taskCount++;
        levelUserManagement();
    }

    public int getAdsCount() {
        return adsCount;
    }

    public int getJackpotCount() {
        return jackpotCount;
    }

    public int getTaskCount() {
        return taskCount;
    }
}
