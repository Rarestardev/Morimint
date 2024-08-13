package com.rarestardev.morimint.ApplicationSetup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.rarestardev.morimint.Activities.MainActivity;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.Repository.CoinManagerRepository;
import com.rarestardev.morimint.Utilities.DialogType;
import com.rarestardev.morimint.Utilities.StatusDialog;

public class CoinMintManager {

    private long balance;
    private static final String SHARED_COIN_MINT_NAME = "Balance";
    private static final String SHARED_COIN_MINT_NAME_KEY = "Coin";

    int level = 1;
    int currentLevel;
    private boolean isUpdatingLevel = false;

    Context context;

    public CoinMintManager(Context context) {
        this.context = context;
    }

    // Main Activity onResume getCoin on server data
    public void setBalance(long coin) {
        this.balance = coin;
    }

    public void IncreaseBalance(int click, int mint) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_COIN_MINT_NAME, Context.MODE_PRIVATE);
        long current_coin = preferences.getLong(SHARED_COIN_MINT_NAME_KEY, balance);
        int value = mint * click / click;

        long total = current_coin + value;
        SavedTotalCoin(total);

        balance = total;
        LevelCalculate();
    }

    public void IncreaseBalanceWithTurbo(int click, int mint) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_COIN_MINT_NAME, Context.MODE_PRIVATE);
        long current_coin = preferences.getLong(SHARED_COIN_MINT_NAME_KEY, balance);
        int Value = mint * click * 3 / click;

        long total = current_coin + Value;

        SavedTotalCoinTurbo(total);

        balance = total;
        LevelCalculate();
    }

    public void SendNewValue(long coin) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_COIN_MINT_NAME, Context.MODE_PRIVATE);
        long current_coin = preferences.getLong(SHARED_COIN_MINT_NAME_KEY, 0);
        if (current_coin != 0) {
            if (current_coin != coin) {
                long total = current_coin - coin;
                balance += total;
                CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
                coinManagerRepository.UpdateCoin(total, context);
                ((MainActivity) context).HandleResponseData();
            }
            if (coin > current_coin) {
                SavedTotalCoin(coin);
            }
        }
    }

    private void LevelCalculate() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Energy Manager", Context.MODE_PRIVATE);
        currentLevel = sharedPreferences.getInt("LEVEL", 0);
        SharedPreferences preferences = context.getSharedPreferences(SHARED_COIN_MINT_NAME, Context.MODE_PRIVATE);
        long current_coin = preferences.getLong(SHARED_COIN_MINT_NAME_KEY, 0);
        if (current_coin > UserConstants.LEVEL_COIN[1]) {
            if (currentLevel == 1) {
                level = 2;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[2]) {
            if (currentLevel == 2) {
                level = 3;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[3]) {
            if (currentLevel == 3) {
                level = 4;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[4]) {
            if (currentLevel == 4) {
                level = 5;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[5]) {
            if (currentLevel == 5) {
                level = 6;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[6]) {
            if (currentLevel == 6) {
                level = 7;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[7]) {
            if (currentLevel == 7) {
                level = 8;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[8]) {
            if (currentLevel == 8) {
                level = 9;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[9]) {
            if (currentLevel == 9) {
                level = 10;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[10]) {
            if (currentLevel == 10) {
                level = 11;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[11]) {
            if (currentLevel == 11) {
                level = 12;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[12]) {
            if (currentLevel == 12) {
                level = 13;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[13]) {
            if (currentLevel == 13) {
                level = 14;
                updateLevel();
            }
        }
        if (current_coin > UserConstants.LEVEL_COIN[14]) {
            if (currentLevel == 14) {
                level = 15;
                updateLevel();
            }
        }
    }

    private void updateLevel() {
        if (isUpdatingLevel) return;
        isUpdatingLevel = true;
        final StatusDialog dialog = new StatusDialog(context, DialogType.LOADING);
        dialog.setMessageDialog("Please wait...");
        dialog.setTitleDialog("Update");
        dialog.setCancelable(false);
        dialog.show();
        new Handler().postDelayed(() -> {
            updateLevelInServer();
            dialog.dismiss();
            isUpdatingLevel = false;
        }, 4000);
    }

    private void updateLevelInServer() {
        CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
        coinManagerRepository.UpdateLevel(level, context);
    }

    // send new value coin and update database
    private void SavedTotalCoin(long coin) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_COIN_MINT_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences.edit();
        editor1.putLong(SHARED_COIN_MINT_NAME_KEY, coin);
        editor1.apply();
    }

    private void SavedTotalCoinTurbo(long coin) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_COIN_MINT_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(SHARED_COIN_MINT_NAME_KEY, coin);
        editor.apply();
    }

    public long getBalance() {
        return balance;
    }

}
