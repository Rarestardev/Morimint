package com.rarestardev.morimint.ApplicationSetup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.R;

public class ProgressBarManager {
    private int minter;
    private long totalBalance;
    private int progressStatus;
    private int maxProgress;
    private int minProgress;
    Context context;

    private static final int[] LEVEL_TAP = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 18};

    private static final String SHARED_COIN_MINT_NAME = "Balance";
    private static final String SHARED_COIN_MINT_NAME_KEY = "Coin";


    public ProgressBarManager(Context context) {
        this.context = context;
        this.minter = 1;
    }

    public int getMinter() {
        return minter;
    }

    public void getCurrentCoin(ProgressBar progressBar) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_COIN_MINT_NAME, Context.MODE_PRIVATE);
        totalBalance = preferences.getLong(SHARED_COIN_MINT_NAME_KEY, 0);
        ProgressBar(progressBar);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void setImageWithCurrentLevel(ImageView imageView) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Energy Manager", Context.MODE_PRIVATE);
        int level = sharedPreferences.getInt("LEVEL", 0);
        switch (level) {
            case 2:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_two));
                minter = LEVEL_TAP[1];
                break;
            case 3:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_three));
                minter = LEVEL_TAP[2];
                break;
            case 4:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_four));
                minter = LEVEL_TAP[3];
                break;
            case 5:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_five));
                minter = LEVEL_TAP[4];
                break;
            case 6:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_six));
                minter = LEVEL_TAP[5];
                break;
            case 7:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_seven));
                minter = LEVEL_TAP[6];
                break;
            case 8:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_eight));
                minter = LEVEL_TAP[7];
                break;
            case 9:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_nine));
                minter = LEVEL_TAP[8];
                break;
            case 10:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_ten));
                minter = LEVEL_TAP[9];
                break;
            case 11:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_eleven));
                minter = LEVEL_TAP[10];
                break;
            case 12:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_twelve));
                minter = LEVEL_TAP[11];
                break;
            case 13:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_thirteen));
                minter = LEVEL_TAP[12];
                break;
            case 14:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_fourteen));
                minter = LEVEL_TAP[13];
                break;
            case 15:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_fifteen));
                minter = LEVEL_TAP[14];
                break;
            default:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_one));
                minter = LEVEL_TAP[0];
                break;

        }
    }

    public void ProgressBar(ProgressBar progressBar) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Energy Manager", Context.MODE_PRIVATE);
        int level = sharedPreferences.getInt("LEVEL", 0);
        switch (level) {
            case 1:
                progressStatus = (int) totalBalance;
                minProgress = 0;
                maxProgress = (int) UserConstants.LEVEL_COIN[1];
                break;
            case 2:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[1];
                maxProgress = (int) UserConstants.LEVEL_COIN[2];
                break;
            case 3:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[2];
                maxProgress = (int) UserConstants.LEVEL_COIN[3];
                break;
            case 4:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[3];
                maxProgress = (int) UserConstants.LEVEL_COIN[4];
                break;
            case 5:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[4];
                maxProgress = (int) UserConstants.LEVEL_COIN[5];
                break;
            case 6:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[5];
                maxProgress = (int) UserConstants.LEVEL_COIN[6];
                break;
            case 7:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[6];
                maxProgress = (int) UserConstants.LEVEL_COIN[7];
                break;
            case 8:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[7];
                maxProgress = (int) UserConstants.LEVEL_COIN[8];
                break;
            case 9:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[8];
                maxProgress = (int) UserConstants.LEVEL_COIN[9];
                break;
            case 10:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[9];
                maxProgress = (int) UserConstants.LEVEL_COIN[10];
                break;
            case 11:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[10];
                maxProgress = (int) UserConstants.LEVEL_COIN[11];
                break;
            case 12:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[11];
                maxProgress = (int) UserConstants.LEVEL_COIN[12];
                break;
            case 13:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[12];
                maxProgress = (int) UserConstants.LEVEL_COIN[13];
                break;
            case 14:
                progressStatus = (int) totalBalance;
                minProgress = (int) UserConstants.LEVEL_COIN[13];
                maxProgress = (int) UserConstants.LEVEL_COIN[14];
                break;
            case 15:
                minProgress = 0;
                progressStatus = 100;
                maxProgress = 100;
                break;
        }

        initProgressBarValues(progressBar, maxProgress, minProgress);
    }

    private void initProgressBarValues(ProgressBar progressBar, int max, int min) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            progressBar.setMin(min);
        }
        progressBar.setMax(max);
        new Thread(() -> {
            if (progressStatus < max) {
                progressStatus++;
            }
            progressBar.setProgress(progressStatus, true);
            try {
                Thread.sleep(100); // update progress
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
