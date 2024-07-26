package com.rarestardev.morimint.ApplicationSetup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.rarestardev.morimint.R;

public class ApplicationManager {
    private int value;
    private int maxValue;
    private int minValue;
    private int step;
    private int minter;
    private long totalBalance;
    private int level;
    private int maxProgress;
    private int progressStatus;

    private static final long[] LEVEL_COIN = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};

    public ApplicationManager() {
        this.value = 1000;
        this.maxValue = 1000;
        this.minValue = 0;
        this.step = 3;
        this.minter = 1;
    }

    public void increment() {
        if (value < maxValue) {
            value += step;
        }
        if (value > maxValue) {
            value = maxValue;
        }
    }

    public void decrement(int mint) {
        if (value >= 1) {
            value -= mint;
            if (value < minValue) {
                value = minValue;
            }
        }
    }

    public boolean mintStop(int mint) {
        return value < mint;
    }

    public void increase(int currentTime, int last_energy) {
        int added_energy = currentTime * step;

        value = Math.min(last_energy + added_energy, maxValue);
        if (value > maxValue) {
            value = maxValue;
        }
    }

    public int getValue() {
        return value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinter() {
        return minter;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void initLevelValues(Context context, long coin, ProgressBar progressBar, AppCompatImageView Coin, AppCompatImageView Turbo, TextView textView,
                                RoundedImageView mainProfile, AppCompatImageView drawerProfile) {
        totalBalance = coin;
        if (totalBalance < LEVEL_COIN[0]) {
            level = 1;
        } else if (totalBalance > LEVEL_COIN[0] && totalBalance <= LEVEL_COIN[1]) {
            level = 2;
            maxValue = 2000;
            minter = 2;
        } else if (totalBalance > LEVEL_COIN[1] && totalBalance <= LEVEL_COIN[2]) {
            level = 3;
            maxValue = 3000;
            minter = 3;
        } else if (totalBalance > LEVEL_COIN[2] && totalBalance <= LEVEL_COIN[3]) {
            level = 4;
            maxValue = 4000;
            minter = 4;
        } else if (totalBalance > LEVEL_COIN[3] && totalBalance <= LEVEL_COIN[4]) {
            level = 5;
            maxValue = 5000;
            minter = 5;
        } else if (totalBalance > LEVEL_COIN[4] && totalBalance <= LEVEL_COIN[5]) {
            level = 6;
            maxValue = 6000;
            minter = 6;
        } else if (totalBalance > LEVEL_COIN[5] && totalBalance <= LEVEL_COIN[6]) {
            level = 7;
            maxValue = 7000;
            minter = 7;
        } else if (totalBalance > LEVEL_COIN[6] && totalBalance <= LEVEL_COIN[7]) {
            level = 8;
            maxValue = 8000;
            minter = 8;
        } else if (totalBalance > LEVEL_COIN[7] && totalBalance <= LEVEL_COIN[8]) {
            level = 9;
            maxValue = 9000;
            minter = 9;
        } else if (totalBalance > LEVEL_COIN[8] && totalBalance <= LEVEL_COIN[9]) {
            level = 10;
            maxValue = 10000;
            minter = 10;
        } else if (totalBalance > LEVEL_COIN[9] && totalBalance <= LEVEL_COIN[10]) {
            level = 11;
            maxValue = 11000;
            minter = 11;
        } else if (totalBalance > LEVEL_COIN[10] && totalBalance <= LEVEL_COIN[11]) {
            level = 12;
            maxValue = 12000;
            minter = 12;
        } else if (totalBalance > LEVEL_COIN[11] && totalBalance <= LEVEL_COIN[12]) {
            level = 13;
            maxValue = 14000;
            minter = 14;
        } else if (totalBalance > LEVEL_COIN[12] && totalBalance <= LEVEL_COIN[13]) {
            level = 14;
            maxValue = 16000;
            minter = 16;
        } else if (totalBalance > LEVEL_COIN[13] && totalBalance <= LEVEL_COIN[14]) {
            level = 15;
            maxValue = 18000;
            minter = 18;
        } else if (totalBalance > LEVEL_COIN[14]) {
            level = 15;
            progressBar.setProgress(100);
            maxValue = 18000;
            minter = 18;
        }

        setLevelImages(level, Coin, Turbo, context,mainProfile,drawerProfile);
        textView.setText(String.valueOf(level));
        ProgressBar(level, progressBar, coin);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setLevelImages(int levelValue, AppCompatImageView Coin, AppCompatImageView Turbo, Context context
            ,RoundedImageView mainProfile,AppCompatImageView drawerProfile) {
        switch (levelValue) {
            case 2:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_two));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_two));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_two));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_two));
                break;
            case 3:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_three));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_three));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_three));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_three));
                break;
            case 4:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_four));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_four));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_four));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_four));
                break;
            case 5:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_five));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_five));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_five));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_five));
                break;
            case 6:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_six));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_six));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_six));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_six));
                break;
            case 7:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_seven));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_seven));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_seven));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_seven));
                break;
            case 8:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_eight));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_eight));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_eight));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_eight));
                break;
            case 9:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_nine));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_nine));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_nine));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_nine));
                break;
            case 10:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_ten));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_ten));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_ten));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_ten));
                break;
            case 11:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_eleven));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_eleven));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_eleven));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_eleven));
                break;
            case 12:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_twelve));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_twelve));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_twelve));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_twelve));
                break;
            case 13:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_thirteen));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_thirteen));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_thirteen));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_thirteen));
                break;
            case 14:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_fourteen));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_fourteen));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_fourteen));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_fourteen));
                break;
            case 15:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_fifteen));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_fifteen));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_fifteen));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_fifteen));
                break;

            default:
                Coin.setImageDrawable(context.getDrawable(R.drawable.level_one));
                Turbo.setImageDrawable(context.getDrawable(R.drawable.level_one));
                mainProfile.setImageDrawable(context.getDrawable(R.drawable.level_one));
                drawerProfile.setImageDrawable(context.getDrawable(R.drawable.level_one));
                break;

        }
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void initLevelDialog(Context context, long coin, ProgressBar progressBar, AppCompatImageView Coin, AppCompatTextView textView) {
        totalBalance = coin;
        if (totalBalance < LEVEL_COIN[0]) {
            level = 1;
        } else if (totalBalance > LEVEL_COIN[0] && totalBalance <= LEVEL_COIN[1]) {
            level = 2;
            maxValue = 2000;
            minter = 2;
        } else if (totalBalance > LEVEL_COIN[1] && totalBalance <= LEVEL_COIN[2]) {
            level = 3;
            maxValue = 3000;
            minter = 3;
        } else if (totalBalance > LEVEL_COIN[2] && totalBalance <= LEVEL_COIN[3]) {
            level = 4;
            maxValue = 4000;
            minter = 4;
        } else if (totalBalance > LEVEL_COIN[3] && totalBalance <= LEVEL_COIN[4]) {
            level = 5;
            maxValue = 5000;
            minter = 5;
        } else if (totalBalance > LEVEL_COIN[4] && totalBalance <= LEVEL_COIN[5]) {
            level = 6;
            maxValue = 6000;
            minter = 6;
        } else if (totalBalance > LEVEL_COIN[5] && totalBalance <= LEVEL_COIN[6]) {
            level = 7;
            maxValue = 7000;
            minter = 7;
        } else if (totalBalance > LEVEL_COIN[6] && totalBalance <= LEVEL_COIN[7]) {
            level = 8;
            maxValue = 8000;
            minter = 8;
        } else if (totalBalance > LEVEL_COIN[7] && totalBalance <= LEVEL_COIN[8]) {
            level = 9;
            maxValue = 9000;
            minter = 9;
        } else if (totalBalance > LEVEL_COIN[8] && totalBalance <= LEVEL_COIN[9]) {
            level = 10;
            maxValue = 10000;
            minter = 10;
        } else if (totalBalance > LEVEL_COIN[9] && totalBalance <= LEVEL_COIN[10]) {
            level = 11;
            maxValue = 11000;
            minter = 11;
        } else if (totalBalance > LEVEL_COIN[10] && totalBalance <= LEVEL_COIN[11]) {
            level = 12;
            maxValue = 12000;
            minter = 12;
        } else if (totalBalance > LEVEL_COIN[11] && totalBalance <= LEVEL_COIN[12]) {
            level = 13;
            maxValue = 14000;
            minter = 14;
        } else if (totalBalance > LEVEL_COIN[12] && totalBalance <= LEVEL_COIN[13]) {
            level = 14;
            maxValue = 16000;
            minter = 16;
        } else if (totalBalance > LEVEL_COIN[13] && totalBalance <= LEVEL_COIN[14]) {
            level = 15;
            maxValue = 18000;
            minter = 18;
        } else if (totalBalance > LEVEL_COIN[14]) {
            level = 15;
            progressBar.setProgress(100);
            maxValue = 18000;
            minter = 18;
        }

        setLevelImagesDialog(level, Coin, context);
        textView.setText("Level : " + level);
        ProgressBar(level, progressBar, coin);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setLevelImagesDialog(int levelValue, AppCompatImageView character,Context context) {
        switch (levelValue) {
            case 2:
                character.setImageDrawable(context.getDrawable(R.drawable.level_two));
                break;
            case 3:
                character.setImageDrawable(context.getDrawable(R.drawable.level_three));
                break;
            case 4:
                character.setImageDrawable(context.getDrawable(R.drawable.level_four));
                break;
            case 5:
                character.setImageDrawable(context.getDrawable(R.drawable.level_five));
                break;
            case 6:
                character.setImageDrawable(context.getDrawable(R.drawable.level_six));
                break;
            case 7:
                character.setImageDrawable(context.getDrawable(R.drawable.level_seven));
                break;
            case 8:
                character.setImageDrawable(context.getDrawable(R.drawable.level_eight));
                break;
            case 9:
                character.setImageDrawable(context.getDrawable(R.drawable.level_nine));
                break;
            case 10:
                character.setImageDrawable(context.getDrawable(R.drawable.level_ten));
                break;
            case 11:
                character.setImageDrawable(context.getDrawable(R.drawable.level_eleven));
                break;
            case 12:
                character.setImageDrawable(context.getDrawable(R.drawable.level_twelve));
                break;
            case 13:
                character.setImageDrawable(context.getDrawable(R.drawable.level_thirteen));
                break;
            case 14:
                character.setImageDrawable(context.getDrawable(R.drawable.level_fourteen));
                break;
            case 15:
                character.setImageDrawable(context.getDrawable(R.drawable.level_fifteen));
                break;

            default:
                character.setImageDrawable(context.getDrawable(R.drawable.level_one));
                break;

        }
    }

    private void ProgressBar(int level, ProgressBar progressBar, long coin) {
        switch (level) {
            case 2:
                progressStatus = (int) (coin - LEVEL_COIN[0]);
                maxProgress = (int) (LEVEL_COIN[1] - LEVEL_COIN[0]);
                break;
            case 3:
                progressStatus = (int) (coin - LEVEL_COIN[1]);
                maxProgress = (int) (LEVEL_COIN[2] - LEVEL_COIN[1]);
                break;
            case 4:
                progressStatus = (int) (coin - LEVEL_COIN[2]);
                maxProgress = (int) (LEVEL_COIN[3] - LEVEL_COIN[2]);
                break;
            case 5:
                progressStatus = (int) (coin - LEVEL_COIN[3]);
                maxProgress = (int) (LEVEL_COIN[4] - LEVEL_COIN[3]);
                break;
            case 6:
                progressStatus = (int) (coin - LEVEL_COIN[4]);
                maxProgress = (int) (LEVEL_COIN[5] - LEVEL_COIN[4]);
                break;
            case 7:
                progressStatus = (int) (coin - LEVEL_COIN[5]);
                maxProgress = (int) (LEVEL_COIN[6] - LEVEL_COIN[5]);
                break;
            case 8:
                progressStatus = (int) (coin - LEVEL_COIN[6]);
                maxProgress = (int) (LEVEL_COIN[7] - LEVEL_COIN[6]);
                break;
            case 9:
                progressStatus = (int) (coin - LEVEL_COIN[7]);
                maxProgress = (int) (LEVEL_COIN[8] - LEVEL_COIN[7]);
                break;
            case 10:
                progressStatus = (int) (coin - LEVEL_COIN[8]);
                maxProgress = (int) (LEVEL_COIN[9] - LEVEL_COIN[8]);
                break;
            case 11:
                progressStatus = (int) (coin - LEVEL_COIN[9]);
                maxProgress = (int) (LEVEL_COIN[10] - LEVEL_COIN[9]);
                break;
            case 12:
                progressStatus = (int) (coin - LEVEL_COIN[10]);
                maxProgress = (int) (LEVEL_COIN[11] - LEVEL_COIN[10]);
                break;
            case 13:
                progressStatus = (int) (coin - LEVEL_COIN[11]);
                maxProgress = (int) (LEVEL_COIN[12] - LEVEL_COIN[11]);
                break;
            case 14:
                progressStatus = (int) (coin - LEVEL_COIN[12]);
                maxProgress = (int) (LEVEL_COIN[13] - LEVEL_COIN[12]);
                break;
            case 15:
                progressStatus = (int) (coin - LEVEL_COIN[13]);
                maxProgress = (int) (LEVEL_COIN[14] - LEVEL_COIN[13]);
                if (progressStatus == LEVEL_COIN[14]) {
                    progressStatus = 100;
                }
                break;

            default:
                progressStatus = (int) coin;
                maxProgress = (int) LEVEL_COIN[0];
                break;

        }
        initProgressBarValues(progressBar, maxProgress);
    }

    private void initProgressBarValues(ProgressBar progressBar, int max) {
        new Thread(() -> {
            if (progressStatus < max) {
                progressStatus++;
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


    private void ChangeColorProgressState(int progress, ProgressBar progressBar,Context context) {
        progressBar.setProgress(progress);

        int color;
        if (progress < 20) {
            color = R.color.progressMinValue;
        } else if (progress < 40) {
            color = R.color.progressOneValue;
        } else if (progress < 60) {
            color = R.color.progressTwoValue;
        } else if (progress < 80) {
            color = R.color.progressThreeValue;
        } else {
            color = R.color.progressMaxValue;
        }

        LayerDrawable drawable = (LayerDrawable) progressBar.getProgressDrawable();
        Drawable progressDrawable = drawable.findDrawableByLayerId(android.R.id.progress);
        progressDrawable.setColorFilter(context.getColor(color), PorterDuff.Mode.SRC_IN);
    }
}
