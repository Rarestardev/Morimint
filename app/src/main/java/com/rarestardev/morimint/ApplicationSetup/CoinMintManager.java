package com.rarestardev.morimint.ApplicationSetup;

import android.content.Context;
import android.content.SharedPreferences;

import com.rarestardev.morimint.Activities.MainActivity;
import com.rarestardev.morimint.Repository.CoinManagerRepository;

public class CoinMintManager {

    private long balance;

    Context context;

    public CoinMintManager(Context context) {
        this.context = context;
        this.balance = 0;
    }

    // Main Activity onResume getCoin on server data
    public void setBalance(long coin) {
        this.balance = coin;
    }

    public void IncreaseBalance(int click, int mint) {
        SharedPreferences preferences = context.getSharedPreferences("Balance", Context.MODE_PRIVATE);
        long current_coin = preferences.getLong("Coin", 0);
        int value = mint * click / click;

        long total = current_coin + value;
        SavedTotalCoin(total);

        balance = total;
    }

    public void IncreaseBalanceWithTurbo(int click, int mint) {
        SharedPreferences preferences = context.getSharedPreferences("Balance", Context.MODE_PRIVATE);
        long current_coin = preferences.getLong("Coin", 0);
        int Value = mint * click * 3 / click;

        long total = current_coin + Value;

        SavedTotalCoinTurbo(total);

        balance = total;
    }

    public void SendNewValue(long coin) {
        SharedPreferences preferences = context.getSharedPreferences("Balance", Context.MODE_PRIVATE);
        long current_coin = preferences.getLong("Coin", 0);
        if (current_coin != 0) {
            if (current_coin != coin) {
                long total = current_coin - coin;
                balance += total;
                CoinManagerRepository coinManagerRepository = new CoinManagerRepository();
                coinManagerRepository.UpdateCoin(total, context);
                ((MainActivity) context).HandleResponseData();
            }
        }
    }

    private void LevelCalculate() {
        //Log.i("CoinValue", getBalance() + "");
    }

    // send new value coin and update database
    private void SavedTotalCoin(long coin) {
        SharedPreferences preferences = context.getSharedPreferences("Balance", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences.edit();
        editor1.putLong("Coin", coin);
        editor1.apply();
        LevelCalculate();
    }

    private void SavedTotalCoinTurbo(long coin) {
        SharedPreferences preferences = context.getSharedPreferences("Balance", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences.edit();
        editor1.putLong("Coin", coin);
        editor1.apply();
        LevelCalculate();
    }

    public long getBalance() {
        return balance;
    }

}
