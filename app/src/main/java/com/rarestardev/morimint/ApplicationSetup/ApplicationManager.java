package com.rarestardev.morimint.ApplicationSetup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.rarestardev.morimint.R;

public class ChargeEnergyCounter {
    int value;
    int maxValue;
    int minValue;
    int step;
    int minter;
    long totalBalance;
    int progressStatus;

    private static final long[] LEVEL_COIN = {100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500};

    public ChargeEnergyCounter() {
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
        if (value > maxValue){
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
    public void initLevelValues(Context context,long coin , TextView tvLevel, ProgressBar progressBar, AppCompatImageView Coin, AppCompatImageView Turbo){
        totalBalance = coin;
        if (totalBalance < LEVEL_COIN[0]){
            tvLevel.setText(String.valueOf(1));
            progressBar.setMax((int) LEVEL_COIN[0]);
            initProgressBarValues(LEVEL_COIN[0],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_one));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_one));

        }else if (totalBalance > LEVEL_COIN[0] && totalBalance <= LEVEL_COIN[1]){
            tvLevel.setText(String.valueOf(2));
            progressBar.setMax((int) LEVEL_COIN[1]);
            initProgressBarValues(LEVEL_COIN[1],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_two));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_two));
            maxValue = 2000;

        }else if (totalBalance > LEVEL_COIN[1] && totalBalance <= LEVEL_COIN[2]){
            tvLevel.setText(String.valueOf(3));
            progressBar.setMax((int) LEVEL_COIN[2]);
            initProgressBarValues(LEVEL_COIN[2],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_three));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_three));

            maxValue = 3000;

        }else if (totalBalance > LEVEL_COIN[2] && totalBalance <= LEVEL_COIN[3]){
            tvLevel.setText(String.valueOf(4));

            progressBar.setMax((int) LEVEL_COIN[3]);
            initProgressBarValues(LEVEL_COIN[3],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_four));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_four));

        }else if (totalBalance > LEVEL_COIN[3] && totalBalance <= LEVEL_COIN[4]){
            tvLevel.setText(String.valueOf(5));
            progressBar.setMax((int) LEVEL_COIN[4]);
            initProgressBarValues(LEVEL_COIN[4],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_five));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_five));

        }else if (totalBalance > LEVEL_COIN[4] && totalBalance <= LEVEL_COIN[5]){
            tvLevel.setText(String.valueOf(6));
            progressBar.setMax((int) LEVEL_COIN[5]);
            initProgressBarValues(LEVEL_COIN[5],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_six));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_six));

        }else if (totalBalance > LEVEL_COIN[5] && totalBalance <= LEVEL_COIN[6]){
            tvLevel.setText(String.valueOf(7));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[6]);
            initProgressBarValues(LEVEL_COIN[6],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_seven));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_seven));

        }else if (totalBalance > LEVEL_COIN[6] && totalBalance <= LEVEL_COIN[7]){
            tvLevel.setText(String.valueOf(8));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[7]);
            initProgressBarValues(LEVEL_COIN[7],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_eight));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_eight));

        }else if (totalBalance > LEVEL_COIN[7] && totalBalance <= LEVEL_COIN[8]){
            tvLevel.setText(String.valueOf(9));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[8]);
            initProgressBarValues(LEVEL_COIN[8],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_nine));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_nine));

        }else if (totalBalance > LEVEL_COIN[8] && totalBalance <= LEVEL_COIN[9]){
            tvLevel.setText(String.valueOf(10));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[9]);
            initProgressBarValues(LEVEL_COIN[9],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_ten));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_ten));

        }else if (totalBalance > LEVEL_COIN[9] && totalBalance <= LEVEL_COIN[10]){
            tvLevel.setText(String.valueOf(11));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[10]);
            initProgressBarValues(LEVEL_COIN[10],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_eleven));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_eleven));

        }else if (totalBalance > LEVEL_COIN[10] && totalBalance <= LEVEL_COIN[11]){
            tvLevel.setText(String.valueOf(12));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[11]);
            initProgressBarValues(LEVEL_COIN[11],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_twelve));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_twelve));

        }else if (totalBalance > LEVEL_COIN[11] && totalBalance <= LEVEL_COIN[12]){
            tvLevel.setText(String.valueOf(13));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[12]);
            initProgressBarValues(LEVEL_COIN[12],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_thirteen));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_thirteen));

        }else if (totalBalance > LEVEL_COIN[12] && totalBalance <= LEVEL_COIN[13]){
            tvLevel.setText(String.valueOf(14));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[13]);
            initProgressBarValues(LEVEL_COIN[13],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_fourteen));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_fourteen));

        }else if (totalBalance > LEVEL_COIN[13] && totalBalance <= LEVEL_COIN[14]){
            tvLevel.setText(String.valueOf(15));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[14]);
            initProgressBarValues(LEVEL_COIN[14],progressBar);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_fifteen));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_fifteen));

        }else if (totalBalance > LEVEL_COIN[14]){
            tvLevel.setText(String.valueOf(15));
            progressBar.setMax((int) LEVEL_COIN[14]);
            progressBar.setProgress(100);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_fifteen));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_fifteen));
        }
    }

    private void initProgressBarValues(long currentCoin,ProgressBar progressBar){
        if (totalBalance < currentCoin){
            progressStatus = (int) totalBalance;
        }
        new Thread(() -> {
            if (progressStatus < currentCoin){
                progressStatus ++;
            } else if (progressStatus == currentCoin){
                progressStatus = 0;
            }
            progressBar.setProgress(progressStatus,true);

            try {
                Thread.sleep(100); // update progress
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
