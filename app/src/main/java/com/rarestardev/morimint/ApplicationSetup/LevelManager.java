package com.rarestardev.morimint.ApplicationSetup;

import android.content.Context;

import com.rarestardev.morimint.Repository.CoinManagerRepository;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LevelManager {

    private static final long[] LEVEL_COIN = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};

    private long balance;
    private int Level;
    private int currentLevel;

    Context context;

    public LevelManager(Context context) {
        this.context = context;
    }

    public void getCoinInMint(long coin) {
        balance = coin;
    }

    public void getTotalBalance(int level) {
        currentLevel = level;
    }

    public void HandleLevelWithCoin(long coin) {
        if (coin < LEVEL_COIN[0]) {
            if (currentLevel == 0) {
                updateNewLevel();
            }
        } else if (coin > LEVEL_COIN[0] && coin <= LEVEL_COIN[1]) {
            // level 2
            if (currentLevel == 1) {
                updateNewLevel();
            }
        } else if (coin > LEVEL_COIN[1] && coin <= LEVEL_COIN[2]) {
            if (currentLevel == 2) {
                updateNewLevel();
            }
        }
    }

    private void updateNewLevel() {
        CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
        coinManagerRepository.UpdateLevel(1, context);
    }
}
