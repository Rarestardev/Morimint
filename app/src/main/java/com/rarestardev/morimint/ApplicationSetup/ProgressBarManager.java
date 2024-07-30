package com.rarestardev.morimint.ApplicationSetup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.rarestardev.morimint.R;

public class ProgressBarManager {
    private int minter;
    private long totalBalance;
    private int progressStatus;
    int maxProgress;
    Context context;

    private static final long[] LEVEL_COIN = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};
    private static final int[] LEVEL_TAP = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 18};


    public ProgressBarManager(Context context) {
        this.context = context;
        this.minter = 1;
    }

    public int getMinter() {
        return minter;
    }

    public void getCurrentCoin(long coin, ProgressBar progressBar) {
        totalBalance = coin;
        initProgressBarValues(progressBar);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void setImageWithCurrentLevel(int level, ImageView imageView) {
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

    public void ProgressBar(int level, ProgressBar progressBar) {
        switch (level) {
            case 2:
                progressStatus = (int) (totalBalance - LEVEL_COIN[0]);
                maxProgress = (int) (LEVEL_COIN[1] - LEVEL_COIN[0]);
                break;
            case 3:
                progressStatus = (int) (totalBalance - LEVEL_COIN[1]);
                maxProgress = (int) (LEVEL_COIN[2] - LEVEL_COIN[1]);
                break;
            case 4:
                progressStatus = (int) (totalBalance - LEVEL_COIN[2]);
                maxProgress = (int) (LEVEL_COIN[3] - LEVEL_COIN[2]);
                break;
            case 5:
                progressStatus = (int) (totalBalance - LEVEL_COIN[3]);
                maxProgress = (int) (LEVEL_COIN[4] - LEVEL_COIN[3]);
                break;
            case 6:
                progressStatus = (int) (totalBalance - LEVEL_COIN[4]);
                maxProgress = (int) (LEVEL_COIN[5] - LEVEL_COIN[4]);
                break;
            case 7:
                progressStatus = (int) (totalBalance - LEVEL_COIN[5]);
                maxProgress = (int) (LEVEL_COIN[6] - LEVEL_COIN[5]);
                break;
            case 8:
                progressStatus = (int) (totalBalance - LEVEL_COIN[6]);
                maxProgress = (int) (LEVEL_COIN[7] - LEVEL_COIN[6]);
                break;
            case 9:
                progressStatus = (int) (totalBalance - LEVEL_COIN[7]);
                maxProgress = (int) (LEVEL_COIN[8] - LEVEL_COIN[7]);
                break;
            case 10:
                progressStatus = (int) (totalBalance - LEVEL_COIN[8]);
                maxProgress = (int) (LEVEL_COIN[9] - LEVEL_COIN[8]);
                break;
            case 11:
                progressStatus = (int) (totalBalance - LEVEL_COIN[9]);
                maxProgress = (int) (LEVEL_COIN[10] - LEVEL_COIN[9]);
                break;
            case 12:
                progressStatus = (int) (totalBalance - LEVEL_COIN[10]);
                maxProgress = (int) (LEVEL_COIN[11] - LEVEL_COIN[10]);
                break;
            case 13:
                progressStatus = (int) (totalBalance - LEVEL_COIN[11]);
                maxProgress = (int) (LEVEL_COIN[12] - LEVEL_COIN[11]);
                break;
            case 14:
                progressStatus = (int) (totalBalance - LEVEL_COIN[12]);
                maxProgress = (int) (LEVEL_COIN[13] - LEVEL_COIN[12]);
                break;
            case 15:
                progressStatus = (int) (totalBalance - LEVEL_COIN[13]);
                maxProgress = (int) (LEVEL_COIN[14] - LEVEL_COIN[13]);
                if (progressStatus == LEVEL_COIN[14]) {
                    progressStatus = 100;
                }
                break;

            default:
                progressStatus = (int) totalBalance;
                maxProgress = (int) LEVEL_COIN[0];
                break;
        }

        initProgressBarValues(progressBar);
    }

    private void initProgressBarValues(ProgressBar progressBar) {
        new Thread(() -> {
            if (progressStatus < maxProgress) {
                progressStatus++;
                Log.i("Coin", progressStatus + "");
            }
            progressBar.setProgress(progressStatus, true);
            ChangeColorProgressState(progressStatus, progressBar, progressBar.getContext());
            try {
                Thread.sleep(100); // update progress
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void ChangeColorProgressState(int progress, ProgressBar progressBar, Context context) {
        progressBar.setProgress(progress);

        int color;
        if (progress < 40) {
            color = R.color.progressMinValue;
        } else if (progress < 60) {
            color = R.color.progressTwoValue;
        } else if (progress < 90) {
            color = R.color.progressThreeValue;
        } else {
            color = R.color.progressMaxValue;
        }

        LayerDrawable drawable = (LayerDrawable) progressBar.getProgressDrawable();
        Drawable progressDrawable = drawable.findDrawableByLayerId(android.R.id.progress);
        progressDrawable.setColorFilter(context.getColor(color), PorterDuff.Mode.SRC_IN);
    }
}
