package com.rarestardev.morimint.ApplicationSetup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.rarestardev.morimint.Adapters.LevelManagerAdapter;
import com.rarestardev.morimint.OfflineModel.LevelManagerModel;
import com.rarestardev.morimint.R;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManager {
    private int maxValue;
    private int minter;
    private long totalBalance;
    private int level;
    private int progressStatus;
    Context context;
    List<LevelManagerModel> managerModels;

    private static final long[] LEVEL_COIN = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};
    private static final int[] LEVEL = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private static final int[] LEVEL_ENERGY = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 14000, 16000, 18000};
    private static final int[] LEVEL_TAP = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 18};
    boolean[] LEVEL_PASSED = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

    private static final int[] LEVEL_ITEM = {R.drawable.level_one, R.drawable.level_two, R.drawable.level_three, R.drawable.level_four,
            R.drawable.level_five, R.drawable.level_six, R.drawable.level_seven, R.drawable.level_eight,
            R.drawable.level_nine, R.drawable.level_ten, R.drawable.level_eleven, R.drawable.level_twelve, R.drawable.level_thirteen,
            R.drawable.level_fourteen, R.drawable.level_fifteen};

    public ApplicationManager(Context context) {
        this.context = context;
        this.maxValue = 1000;
        this.minter = 1;
    }

    public int getMinter() {
        return minter;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void setImageWithCurrentLevel(int level, ImageView imageView) {
        switch (level) {
            case 2:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_two));
                break;
            case 3:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_three));
                break;
            case 4:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_four));
                break;
            case 5:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_five));
                break;
            case 6:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_six));
                break;
            case 7:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_seven));
                break;
            case 8:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_eight));
                break;
            case 9:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_nine));
                break;
            case 10:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_ten));
                break;
            case 11:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_eleven));
                break;
            case 12:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_twelve));
                break;
            case 13:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_thirteen));
                break;
            case 14:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_fourteen));
                break;
            case 15:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_fifteen));
                break;
            default:
                imageView.setImageDrawable(context.getDrawable(R.drawable.level_one));
                break;

        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void initLevelValues(long coin, ProgressBar progressBar) {
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
        ProgressBar(level, progressBar, coin);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void initLevelDialog(Context context, long coin, ProgressBar progressBar, AppCompatImageView Coin,
                                AppCompatTextView textView, RecyclerView recyclerView, TextView tap) {
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
        tap.setText("Tap : +" + minter);
        ProgressBar(level, progressBar, coin);
        setLevelListManager(recyclerView, context, level);
    }

    private void setLevelListManager(RecyclerView recyclerView, Context context, int level) {
        for (int i = 0; i < level; i++) {
            LEVEL_PASSED[i] = true;
        }

        managerModels = new ArrayList<>();
        for (int i = 0; i < LEVEL.length; i++) {
            managerModels.add(new LevelManagerModel(LEVEL[i], LEVEL_ITEM[i], LEVEL_PASSED[i], LEVEL_ENERGY[i], LEVEL_TAP[i], LEVEL_COIN[i]));
        }

        LevelManagerAdapter adapter = new LevelManagerAdapter(context, managerModels, level);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.refreshDrawableState();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setLevelImagesDialog(int levelValue, AppCompatImageView character, Context context) {
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
        int maxProgress;
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


    private void ChangeColorProgressState(int progress, ProgressBar progressBar, Context context) {
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
