
package com.rarestardev.morimint.ApplicationSetup;

import android.content.Context;
import android.os.CountDownTimer;

import com.rarestardev.morimint.Repository.CoinManagerRepository;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LevelManager {

    private static final long[] LEVEL_COIN = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};

    private int currentLevel;
    private static final int updateLevel = 1;
    int click = 0;
    private Context context;
    private boolean isUpdating = false;

    public LevelManager(Context context) {
        this.context = context;
    }

    public void getLevel(int level) {
        currentLevel = level;
    }

    public void HandleLevelWithCoin(long coin) {
        if (coin >= LEVEL_COIN[1]) {
            if (currentLevel == 1) {
                click++;
                updateNewLevel(click);
            }
        }
        if (coin > LEVEL_COIN[2]) {
            if (currentLevel == 2) {
                click++;
                updateNewLevel(click);
            }
        }

        if (coin > LEVEL_COIN[3]) {
            if (currentLevel == 3) {
                click++;
                updateNewLevel(click);
            }
        }
    }

    private void updateNewLevel(int clicked) {
        if (clicked == 1){
            final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    dialog.setTitle("Please Wait!");
                    dialog.setCancelable(false);
                    dialog.setContentText("Update level...");
                    dialog.show();
                }

                @Override
                public void onFinish() {
                    dialog.dismiss();
                    final SweetAlertDialog alertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    alertDialog.setTitle("Good Job!");
                    alertDialog.setContentText("Great! , Your level has been upgraded.");
                    alertDialog.setCancelable(false);
                    alertDialog.setConfirmButton("Thanks", sweetAlertDialog -> {
                        CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
                        coinManagerRepository.UpdateLevel(updateLevel, context);
                        sweetAlertDialog.dismiss();
                        isUpdating = false;
                    }).show();
                }
            };
            countDownTimer.start();
        }
    }
}
