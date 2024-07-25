package com.rarestardev.morimint.ApplicationSetup;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.rarestardev.morimint.R;

public class ProgramManager {

    ProgressBar progressBar;
    Context context;
    TextView tvLevel;
    AppCompatImageView Coin,Turbo;


    int energy;
    int mint = 1;
    int minEnergy;
    int progressStatus;
    long totalBalance;
    private static final String LOG_TAG = "ProgressManager";

    /* private static final long[] LEVEL_COIN = {200000,700000,1500000,5000000,8000000,15000000,25000000,35000000,50000000,70000000,90000000,
    120000000,150000000,200000000,300000000};*/

    private static final long[] LEVEL_COIN = {100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500};

    public ProgramManager(ProgressBar progressBar, Context context, TextView tvLevel, AppCompatImageView Coin, AppCompatImageView Turbo) {
        this.progressBar = progressBar;
        this.context = context;
        this.tvLevel = tvLevel;
        this.Coin = Coin;
        this.Turbo = Turbo;
    }

    public void initTotalCoin(long coin){
        totalBalance = coin;
        Log.i(LOG_TAG,"getCoin :" + totalBalance);
        initLevelValues();

       // changeProgressBarColor();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void initLevelValues(){
        if (totalBalance < LEVEL_COIN[0]){
            tvLevel.setText(String.valueOf(1));
            progressBar.setMax((int) LEVEL_COIN[0]);
            initProgressBarValues(LEVEL_COIN[0]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_one));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_one));

        }else if (totalBalance > LEVEL_COIN[0] && totalBalance <= LEVEL_COIN[1]){
            tvLevel.setText(String.valueOf(2));
            energy = 2000;
            progressBar.setMax((int) LEVEL_COIN[1]);
            initProgressBarValues(LEVEL_COIN[1]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_two));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_two));

        }else if (totalBalance > LEVEL_COIN[1] && totalBalance <= LEVEL_COIN[2]){
            tvLevel.setText(String.valueOf(3));
            energy = 3000;
            mint = 3;
            progressBar.setMax((int) LEVEL_COIN[2]);
            initProgressBarValues(LEVEL_COIN[2]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_three));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_three));

        }else if (totalBalance > LEVEL_COIN[2] && totalBalance <= LEVEL_COIN[3]){
            tvLevel.setText(String.valueOf(4));
            energy = 4000;
            mint = 4;
            progressBar.setMax((int) LEVEL_COIN[3]);
            initProgressBarValues(LEVEL_COIN[3]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_four));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_four));

        }else if (totalBalance > LEVEL_COIN[3] && totalBalance <= LEVEL_COIN[4]){
            tvLevel.setText(String.valueOf(5));
            energy = 5000;
            mint = 5;
            progressBar.setMax((int) LEVEL_COIN[4]);
            initProgressBarValues(LEVEL_COIN[4]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_five));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_five));

        }else if (totalBalance > LEVEL_COIN[4] && totalBalance <= LEVEL_COIN[5]){
            tvLevel.setText(String.valueOf(6));
            progressBar.setMax((int) LEVEL_COIN[5]);
            initProgressBarValues(LEVEL_COIN[5]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_six));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_six));

        }else if (totalBalance > LEVEL_COIN[5] && totalBalance <= LEVEL_COIN[6]){
            tvLevel.setText(String.valueOf(7));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[6]);
            initProgressBarValues(LEVEL_COIN[6]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_seven));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_seven));

        }else if (totalBalance > LEVEL_COIN[6] && totalBalance <= LEVEL_COIN[7]){
            tvLevel.setText(String.valueOf(8));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[7]);
            initProgressBarValues(LEVEL_COIN[7]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_eight));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_eight));

        }else if (totalBalance > LEVEL_COIN[7] && totalBalance <= LEVEL_COIN[8]){
            tvLevel.setText(String.valueOf(9));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[8]);
            initProgressBarValues(LEVEL_COIN[8]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_nine));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_nine));

        }else if (totalBalance > LEVEL_COIN[8] && totalBalance <= LEVEL_COIN[9]){
            tvLevel.setText(String.valueOf(10));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[9]);
            initProgressBarValues(LEVEL_COIN[9]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_ten));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_ten));

        }else if (totalBalance > LEVEL_COIN[9] && totalBalance <= LEVEL_COIN[10]){
            tvLevel.setText(String.valueOf(11));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[10]);
            initProgressBarValues(LEVEL_COIN[10]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_eleven));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_eleven));

        }else if (totalBalance > LEVEL_COIN[10] && totalBalance <= LEVEL_COIN[11]){
            tvLevel.setText(String.valueOf(12));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[11]);
            initProgressBarValues(LEVEL_COIN[11]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_twelve));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_twelve));

        }else if (totalBalance > LEVEL_COIN[11] && totalBalance <= LEVEL_COIN[12]){
            tvLevel.setText(String.valueOf(13));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[12]);
            initProgressBarValues(LEVEL_COIN[12]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_thirteen));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_thirteen));

        }else if (totalBalance > LEVEL_COIN[12] && totalBalance <= LEVEL_COIN[13]){
            tvLevel.setText(String.valueOf(14));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[13]);
            initProgressBarValues(LEVEL_COIN[13]);
            Coin.setImageDrawable(context.getDrawable(R.drawable.level_fourteen));
            Turbo.setImageDrawable(context.getDrawable(R.drawable.level_fourteen));

        }else if (totalBalance > LEVEL_COIN[13] && totalBalance <= LEVEL_COIN[14]){
            tvLevel.setText(String.valueOf(15));
            progressStatus = 0;
            progressBar.setMax((int) LEVEL_COIN[14]);
            initProgressBarValues(LEVEL_COIN[14]);
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






    public void initProgressBarValues(long currentCoin){
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



    public int getEnergy() {
        return energy;
    }

    public int getMint() {
        return mint;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setMint(int mint) {
        this.mint = mint;
    }

    public int getMinEnergy() {
        return minEnergy;
    }

    public void setMinEnergy(int minEnergy) {
        this.minEnergy = minEnergy;
    }






    private void changeProgressBarColor() {
        int progress = progressBar.getProgress();
        int colorId;
        if (progress < 10) {
            colorId = R.color.white;
        } else if (progress < 20) {
            colorId = R.color.black;
        } else if (progress < 30) {
            colorId = R.color.JackpotBonusAnim1;
        } else if (progress < 40) {
            colorId = R.color.BaseColor;
        } else if (progress < 50) {
            colorId = R.color.Gray;
        } else if (progress < 60) {
            colorId = R.color.Fields;
        } else if (progress < 70) {
            colorId = R.color.JackpotBonusAnim2;
        } else if (progress < 80) {
            colorId = R.color.JackpotBonusAnim1;
        } else if (progress < 90) {
            colorId = R.color.Views;
        } else {
            colorId = R.color.JackpotBonusAnim3;
        }
        progressBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(context, colorId), PorterDuff.Mode.SRC_ATOP);
    }
}
